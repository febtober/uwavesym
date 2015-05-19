package org.febtober.uwavesym;

import android.os.AsyncTask;

public class Simulator extends AsyncTask<Object, Integer, Circuit> {
    // Constants
    private double w;
    private double c = 3e8;        // Speed of light
    private double Zfo = 120*Math.PI;       // Free space impedance
    private double t = 1.4 * 2.54e-5;       // Conductor thickness for 1 oz copper
    private double e = Math.exp(1);
    private double permitivity_free_space = 4e-7 * Math.PI;
    private double E = 120.0*Math.PI;       // free space impedance
    double THETA = Math.PI/180.0;           // 1 degree increment in radians
    double UMAX = 0.0;
    double PRAD = 0.0;
    private int pts = 40;                   // number of freq points in simulation
    private int linspace_pts = 500;

    private double frequency;
    private double currFrequency;
    private double freq_radian;
    private double substrateH;
    private double substrateP;
    private int x = 0;

    private Circuit circuit;
    private double[] impedance_r = new double[pts+2];;
    private double[] impedance_i = new double[pts+2];;
    private double[] D = new double[pts+2];
    private double[] DDB = new double[pts+2];
    private double[] currDistribution = new double[linspace_pts+2];
    double[] mag_S11 = new double[pts+2];
    double[] VSWR = new double[pts+2];
    double[] ang_S11 = new double[pts+2];
    double[] S11_dB = new double[pts+2];
    double[] Gain_dB = new double[pts+2];

    private WorkspaceActivity parentActivity;

    public Simulator(WorkspaceActivity pActivity) {
        parentActivity = pActivity;
    }

    // params contains [Circuit]
    @Override
    protected Circuit doInBackground(Object... params) {
	    substrateH = circuit.getSubstrateH();
        substrateP = circuit.getSubstrateP();
        circuit = (Circuit) params[0];
        frequency = circuit.getFrequency();
		
		double freq_low = frequency - frequency*0.25;            // define low frequency
        double freq_high = frequency + frequency*0.25;			// define high frequency
        double freq_step = (freq_high-freq_low)/pts;	// define frequency step

        x = 1;

        double currFrequency = freq_low;
        for (; currFrequency <= freq_high; x++, currFrequency += freq_step) {				// START FREQUENCY SWEEPING LOOP HURRR
			// pts+2 for 1-indexing array (matlab compliance)
			freq_radian = 2 * Math.PI * currFrequency;		// same as w
			w = 2 * Math.PI * currFrequency;				// same as freq_radian

			int numComps = circuit.size();

            int i = 0;
            for (; i < numComps; i++) {
                Component currComp = circuit.getComponent(i);
                double param1 = 0;
                double param2 = 0;
                if (currComp.getParam1Exists()) {
                    int param1Min = currComp.getParam1MinPrefix();
                    int param1Prefix = currComp.getParam1Prefix();
                    // normalize prefix index around 0. i.e. micro is -2, kilo is 1, etc.
                    double newParam1Prefix = param1Prefix - (4 - param1Min);
                    // new param = old param * 10e(newParam1Prefix*3)
                    param1 = Math.pow(10, newParam1Prefix*3) * currComp.getParam1();
                }
                if (currComp.getParam2Exists()) {
                    int param2Min = currComp.getParam2MinPrefix();
                    int param2Prefix = currComp.getParam2Prefix();
                    // normalize prefix index around 0. i.e. micro is -2, kilo is 1, etc.
                    double newParam2Prefix = param2Prefix - (4 - param2Min);
                    // new param = old param * 10e(newParam1Prefix*3)
                    param2 = Math.pow(10, newParam2Prefix*3) * currComp.getParam2();
                }

                publishProgress(i);

                switch (currComp.getComponentId()) {
                    case Component.PATCH:
                        break;
                    case Component.DIPOLE:
                        this.dipole(param1, param2);
                        break;
                    case Component.MONOPOLE:
                        // same function as dipole but with length doubled
                        this.dipole(param1*2, param2);
                        break;
                    case Component.LOOP:
                        this.loop(param1);
                        break;
                    case Component.BALUN:
                        break;
                    case Component.QUARTER_TRANSFORMER:
                        this.quarterTransformer(param1, param2);
                        break;
                    case Component.T_LINE:
                        this.quarterTransformer(param1, param2);
                        break;
                    case Component.RESISTOR:
                        this.resistor(param1);
                        break;
                    case Component.RESISTOR_SHUNT:
                        this.resistorShunt(param1);
                        break;
                    case Component.INDUCTOR:
                        this.inductor(param1);
                        break;
                    case Component.INDUCTOR_SHUNT:
                        this.inductorShunt(param1);
                        break;
                    case Component.CAPACITOR:
                        this.capacitor(param1);
                        break;
                    case Component.CAPACITOR_SHUNT:
                        this.capacitorShunt(param1);
                        break;
                    case Component.SUBSTRATE:
                        break;
                    case Component.TERMINATION:
                        this.termination(param1, i == 0);
                        break;
                    default:
                        break;
                }
            }
        }			// END FREQUENCY SWEEPING HURR
        

        return circuit;
    }

    @Override
    protected void onProgressUpdate(Integer... statuses) {
        parentActivity.updateSimulationProgress(statuses[0]);
    }

    @Override
    protected void onPostExecute(Circuit c) {
        parentActivity.simulationComplete();
        super.onPostExecute(c);
    }

    public double[][] getResults() {
        return new double[][] {impedance_r, impedance_i, {frequency}};
    }

    private void resistor(double param1) {
		double r = param1;
        impedance_r[x] += r;	
    }
    
    private void resistorShunt(double param1) {
        // inputs
        double R = param1;                    // Resistance in ohms
        double re_Zin = impedance_r[x];                // Real Zout from previous component in ohms
        double im_Zin = impedance_i[x];                 // Imaginary Zout from previous component in ohms

        // Calculation
        double Yr = 1/R;                   
		double re_Yin;
		double im_Yin;
        re_Yin = 1/(Math.sqrt(Math.pow(re_Zin,2)+Math.pow(im_Zin,2)))*Math.cos(-Math.atan(im_Zin/re_Zin));
        im_Yin = 1/(Math.sqrt(Math.pow(re_Zin,2)+Math.pow(im_Zin,2)))*Math.sin(-Math.atan(im_Zin/re_Zin));
        double A = re_Yin + Yr;
        double B = im_Yin;

        // Output
        double re_Zout = 1/(Math.sqrt(Math.pow(A,2)+Math.pow(B,2)))*Math.cos(-Math.atan(B/A));    // For given inputs 33
        double im_Zout = 1/(Math.sqrt(Math.pow(A,2)+Math.pow(B,2)))*Math.sin(-Math.atan(B/A));    // For given inputs j0

        impedance_r[x] = re_Zout;	// my math does the increment
        impedance_i[x] = im_Zout;	// my math does the increment
    }

    private void capacitor(double param1) {
        double c = param1;
        double z = -1 / (w * c);
        impedance_i[x] += z;		// need to increment
    }
    
    private void capacitorShunt(double param1) {
        //double freq = frequency;        // should be defined in 
        double C = param1;              // Capacitance in farads
        double re_Zin = impedance_r[x];    // Real Zout from previous component in ohms
        double im_Zin = impedance_i[x];    // Imaginary Zout from previous component
                
//        w = 2*Math.PI*freq;              // frequency in radians (automatic)
        double Yc = w*C;                   // Given the inputs this is j6.283 mS

        double re_Yin = 1/(Math.sqrt(Math.pow(re_Zin,2)+Math.pow(im_Zin,2)))*Math.cos(-Math.atan(im_Zin/re_Zin));
        double im_Yin = 1/(Math.sqrt(Math.pow(re_Zin,2)+Math.pow(im_Zin,2)))*Math.sin(-Math.atan(im_Zin/re_Zin));
        double A = re_Yin;
        double B = im_Yin + Yc;

        // Output
        double re_Zout = 1/(Math.sqrt(Math.pow(A,2)+Math.pow(B,2)))*Math.cos(-Math.atan(B/A));    // For given inputs 45
        double im_Zout = 1/(Math.sqrt(Math.pow(A,2)+Math.pow(B,2)))*Math.sin(-Math.atan(B/A));    // For given inputs -j14

        impedance_r[x] = re_Zout;	// my math does the increment
        impedance_i[x] = im_Zout;	// my math does the increment
    }

    private void inductor(double param1) {
        double l = param1;
        double z = w * l;
        impedance_i[x] += z;
    }
    
    private void inductorShunt(double param1) {
        //double freq = frequency;        // should be already defined in freq_sweep loop
        double L = param1;              // Inductance in Henrys
        double re_Zin = impedance_r[x];    // Real Zout from previous component in ohms
        double im_Zin = impedance_i[x];    // Imaginary Zout from previous component

        // Calculation
        //w = 2*Math.PI*freq;             // shouldn't need. defined in while loop on line 64
        double Yl = -1/(w*L);           // Given the inputs this is j6.2832

        double re_Yin = 1/(Math.sqrt(Math.pow(re_Zin,2)+Math.pow(im_Zin,2)))*Math.cos(-Math.atan(im_Zin/re_Zin));
        double im_Yin = 1/(Math.sqrt(Math.pow(re_Zin,2)+Math.pow(im_Zin,2)))*Math.sin(-Math.atan(im_Zin/re_Zin));
        double A = re_Yin;
        double B = im_Yin + Yl;

        // Output
        double re_Zout = 1/(Math.sqrt(Math.pow(A,2)+Math.pow(B,2)))*Math.cos(-Math.atan(B/A));    // For given inputs 50
        double im_Zout = 1/(Math.sqrt(Math.pow(A,2)+Math.pow(B,2)))*Math.sin(-Math.atan(B/A));    // For given inputs j0.4

        impedance_r[x] = re_Zout;	// my math does the increment
        impedance_i[x] = im_Zout;	// my math does the increment
    }

    // param1 = length, param2 = width
    private void quarterTransformer(double param1, double param2) {
        // inputs
        double H = substrateH;        // Substrate height in meters. should be defined in freq_sweep loop
        double Er = substrateP;                  // Permittivity of substrate. should be defined in freq_sweep loop
        double freq = frequency;        // frequency in Hertz. should be defined in freq_sweep loop
        double l = param1;              // Length of xmfr in meters
        double W = param2;              // Width in meters
        double re_Zin = impedance_r[x];    // real Zout from previous component in ohms
        double im_Zin = impedance_i[x];    // Imaginary Zout from previous component in ohms

        // Calculations
//        w = 2*pi*freq;              // frequency in radians

        // ---- Line impedance Calculation-------------
        double u = W/H;
        double W_eff = W + (t/Math.PI) * (Math.log((2*H)/t) + 1);
        double a = 1+(1/49)*Math.log((Math.pow(u,4) +Math.pow((u/52),2))/(Math.pow(u,4)+0.432)) + (1/18.7)*Math.log(1+Math.pow((u/18.1),3));
        double b = 0.564*(Math.pow((Er-0.9)/(Er+3),0.053));
        double fu = 6+(2*Math.PI-6)*Math.exp(-(Math.pow((30.666*(H/W_eff)),0.7528)));
        double E_eff = (Er+1)/2 + (Er-1)/2*Math.pow((1+10*(H/W)),-(a*b));    // Effective permittivity
        double deltaW1 = (t/Math.PI)*Math.log(1+((4*e)/(t*Math.pow((1/Math.tanh(Math.pow(6.517*u,1/2))),2))));
        double W1 = W + deltaW1;
        double deltaWr = 1/2*deltaW1*(1+(1/Math.cosh(Math.pow(Er-1,1/2))));
        double Wr = W + deltaWr;
        double ZL1_W1 = Zfo/(2*Math.PI)*Math.log(fu*(H/W1)+Math.pow(1+Math.pow(2*H/W1,2),1/2));
        double ZL1_Wr = Zfo/(2*Math.PI)*Math.log(fu*(H/Wr)+Math.pow(1+Math.pow(2*H/Wr,2),1/2));
        double E_eff_with_t = E_eff*Math.pow((ZL1_W1/ZL1_Wr),2);

        double ZL_W_h = Zfo/(2*Math.PI)*Math.log(fu*(H/W_eff)+Math.pow(1+Math.pow(2*H/W_eff,2),1/2));
        double Zo = (ZL_W_h)/(Math.pow(E_eff_with_t,1/2));       // Impedance of xmfr in ohms
        // ---------------------------------------------

        double lambda = c/(freq*Math.sqrt(E_eff));              // Wavelength
        double bl = (2*Math.PI*l)/lambda;                       // Electrical length

        double top_real = re_Zin;
        double top_imag = im_Zin + Zo*Math.tan(bl);
        double top_mag  = Math.sqrt(Math.pow(top_real,2) + Math.pow(top_imag,2));
        double top_ang  = Math.atan(top_imag/top_real);
        double bot_real = Zo - im_Zin;
        double bot_imag = re_Zin*Math.tan(bl);
        double bot_mag  = Math.sqrt(Math.pow(bot_real,2) + Math.pow(bot_imag,2));
        double bot_ang  = Math.atan(bot_imag/bot_real);

        // Output
        double re_Zout = Zo*(top_mag/bot_mag*Math.cos(top_ang-bot_ang));
        double im_Zout = Zo*(top_mag/bot_mag*Math.sin(top_ang-bot_ang));

        impedance_r[x] = re_Zout;	// my math does the increment
        impedance_i[x] = im_Zout;	// my math does the increment
    }
    
    private void termination(double param1, boolean isFirstComponent) {
        // inputs
        double re_Zin = impedance_r[x];    // real Zout from previous component in ohms
        double im_Zin = impedance_i[x];    // imaginary Zout from previous component in ohms
        double re_Zo = param1;          // real Impedance of termination (50 ohms)
        double im_Zo = 0;               // imaginary Impedance of termination

        // Calculations
        if (re_Zin == re_Zo)          // NaN if they are equal
            re_Zo = re_Zo+0.0001;

        double top_mag = Math.sqrt(Math.pow(re_Zin-re_Zo,2)+Math.pow(im_Zin-im_Zo,2));
        double top_ang = Math.atan((im_Zin-im_Zo)/(re_Zin-re_Zo));
        double bot_mag = Math.sqrt(Math.pow(re_Zin+re_Zo,2)+Math.pow(im_Zin+im_Zo,2));
        double bot_ang = Math.atan((im_Zin+im_Zo)/(re_Zin+re_Zo));

        double re_S11 = (top_mag/bot_mag)*Math.cos(top_ang-bot_ang);
        double im_S11 = (top_mag/bot_mag)*Math.sin(top_ang-bot_ang);

        // Outputs
        mag_S11[x] = Math.sqrt(Math.pow(re_S11,2)+Math.pow(im_S11,2));
        VSWR[x] = 1+mag_S11[x]/(1-mag_S11[x]);
        ang_S11[x] = top_ang-bot_ang;
        S11_dB[x] = 20*Math.log10(mag_S11[x]);      // Return Loss
        Gain_dB[x] = 10*Math.log10((1-Math.pow(mag_S11[x], 2)) * D[x]);

        // Does not affect impedance if not first component
        if (isFirstComponent) {
            impedance_r[x] = re_Zo;
            impedance_i[x] = im_Zo;
        }
    }

    // param1 = length, param2 = radius
    private void dipole(double param1, double param2) {
        double RIN = 0;
        double RR = 0;
        double Xin = 0;
        double A = 0;

        // Input the length of the dipole---
        // freq = input('\nFrequency of operation in Hertz = ');
        //double freq = frequency;				// should already be defined on line 60
        double lambda_fc = c/frequency;
        // l = input('\nLength of dipole in meters = ','s');
        // l = str2num(l);
        double l = param1;
        double L_fc = l/lambda_fc;
        double lambda = c / currFrequency;

        // R = input('Radius of dipole in meters = ');
        double R = param2;
        double r = R/lambda;
//        fprintf(1,'I have the inputs set as 1 GHz, l = 0.15, r = 0.001');

        // Main program----------------------
            double L = l / lambda;
            r = R / lambda;
            // Calculate input resistance given length
            A = L * Math.PI;                   // repititive calcuation outside loop
            double I = 1;                      // initialize
            while (I <= 180) {            // increment theta by 1 degree bw 0 & 180
                double XI = I * Math.PI / 180.0;
                double U = 0;
                if (XI != Math.PI) {
                    U = Math.pow(((Math.cos(A * Math.cos(XI)) - Math.cos(A)) / Math.sin(XI)), 2) * (E / (8.0 * Math.pow(Math.PI, 2)));
                    if (U > UMAX)
                        UMAX = U;
                }
                double UA = U * Math.sin(XI) * THETA * 2.0 * Math.PI;
                PRAD = PRAD + UA;
                I = I + 1;
            }
            D[x] = (4.0 * Math.PI * UMAX) / PRAD;//directivity
//            DDB[x] = 10.0 * Math.log10(D[x]);//directivity in dB(this is our output)
            RR = 2.0 * PRAD;
            PRAD = 0;
            if (A != Math.PI)
                RIN = RR / Math.pow(Math.sin(A), 2);//OUTPUT !Input resistance

            // calculate reactance given length and radius

            double k = 2 * Math.PI;                 // 2pi/lambda; lambda = 1

//            if exist('cosint') ~ = 2,  // I don't imagine sinint is a func you can use.
            double Xm = 30 * (2 * si(k * L) + Math.cos(k * L) * (2 * si(k * L) - si(2 * k * L)) -
                Math.sin(k * L) * (2 * ci(k * L) - ci(2 * k * L) - ci(2 * k * Math.pow(r, 2) / L)));
            Xin = Xm / Math.pow(Math.sin(k * L / 2), 2);     // OUTPUT! Input reactance

        double[] z=linspace(-L_fc/2,L_fc/2,500);
        k=2*Math.PI;
        int j = 0;
        for (; j < z.length; j++) {
            currDistribution[j] = Math.sin(k * (L_fc / 2 - Math.abs(z[j])));
        }

        impedance_r[x] = RIN;
        impedance_i[x] = Xin;
    }

    // helper function for dipole()
    private double si(double x) {
        double[] v = linspace(0, x / Math.PI, linspace_pts);
        double dv = v[2] - v[1];

//        double y = Math.PI * sum(sinc(v) * dv);
        int i = 1;
        double sum = 0;
        // loop is sum(sinc(x))
        for(; i < linspace_pts+1; i++) {
            // sinc(x) = sin(x) / x
            double sinc;
            if (v[i] == 0)
                sinc = 1;
            else
                sinc = Math.sin(Math.PI*v[i]) / (Math.PI*v[i]);
            sinc *= dv;
            sum += sinc;
        }
        double y = Math.PI * sum;
        return y;
    }

    // helper function for dipole()
    private double ci(double x) {
        double[] v = linspace(0, x / (2 * Math.PI), linspace_pts);
        double dv = v[2] - v[1];

//        y1 = 2 * pi * sum(sinc(v). * sin(pi * v) * dv);
        int i = 1;
        double sum = 0;
        // loop is sum(sinc(v) .* sin(pi * v) * dv)
        for(; i < linspace_pts+1; i++) {
            // sinc(x) = sin(x) / x
            double sinc;
            if (v[i] == 0)
                sinc = 1;
            else
                sinc = Math.sin(Math.PI*v[i]) / (Math.PI*v[i]);
            sinc *= Math.sin(Math.PI * v[i]);
            sinc *= dv;
            sum += sinc;
        }

        double y1 = 2 * Math.PI * sum;
        double y = .5772 + Math.log(x) - y1;
        return y;
    }

    // helper function for dipole()
    private double[] linspace(double x1, double x2, int steps) {
        // matlab is 1-indexed. do same here to retain compliance
        double[] res = new double[steps+1];
        double step = (x2 - x1) / (steps - 1);
        res[1] = x1;
        int i = 2;
        for (; i < steps; i++) {
            res[i] = res[i-1] + step;
        }
        return res;
    }

    private void loop(double param1) {
        double im_Zin;
        double re_Zin;

        // Inputs
        // freq = input('\nFrequency of operation in Hertz = ');
        double lambda = c/currFrequency;
        // l = input('\nRadius of loop in meters = ','s');
        // l = str2num(l);
        double r = param1;
        double r_fc = r/lambda;

        // Loop antenna input impedance lookup table-----------

        double[][] impedance_table = {
                // Need to 1-index table for compliance with matlab code
                {0, 0},
                {89.32, -754.6},
                {106.1, -1011.2},
                {185.6, -1617.7},
                {588.8, -3477},
                {1465.2, 14782.6},
                {79.48, -576.3},
                {76.52, -451.4},
                {77.55, -356.1},
                {81.63, -278.4},
                {88.5, -211.6},
                {98.43, -151.5},
                {112.0, -95.32},
                {130.5, -41.05},
                {155.3, 13.3},
                {190.7, 67.58},
                {239.3, 125.4},
                {312.5, 181.6},
                {422.2, 227.9},
                {584.2, 236.5},
                {788.1, 142.3},
                {926.1, -116.0},
                {837.1, -419.6},
                {610.9, -559.7},
                {414.5, -554.2},
                {287.7, -493.0},
                {212.5, -422.5},
        };

        // Each row states real them imaginary impedance. Then each row going
        // down increments kb by 0.05. kb = circumference/lambda. Table
        // starts with kb = 0.45 and ends with kb = 1.7. Omega = 12.
        // -----------------------------------------------------

        // Loop antenna directivity lookup table---------------

        double[] Directivity_table = {
                0, // need to 1-index table for matlab compliance
                -0.4, 0.3, 0.8, 1.3, 1.68, 2, 2.35, 2.6, 2.82, 3.05, 3.25, 3.45, 3.65, 3.81,
                3.98, 4.125, 4.25, 4.38, 4.45, 4.53, 4.5, 4.38, 4.25, 4, 3.5, 2.7};
        // Each row states directivity in dB for the given kb.
        // 0.45 < kb < 1.7
        // -----------------------------------------------------

        // Calculations ----------------------
        //double freq_low = freq - freq*0.25;            // 50// bandwidth
        //double freq_high = freq + freq*0.25;
        //double freq_step = (freq_high-freq_low)/pts;

        //int x = 1;
        //double freq_now = freq_low;
        //while (freq_now <= freq_high) {

        double cir_lambda = (2 * Math.PI * r) / lambda;           // Circumference / lambda
        int adjust = (int) Math.floor(cir_lambda / 0.05);
        int row = adjust - 8;
        double weight2 = (cir_lambda - adjust * 0.05) / 0.05;    // weighted average b/w data points
        double weight1 = 1 - weight2;
        re_Zin = (weight1 * impedance_table[row][1] + weight2 * impedance_table[row + 1][1]);
        im_Zin = (weight1 * impedance_table[row][2] + weight2 * impedance_table[row + 1][2]);
        DDB[x] = (weight1 * Directivity_table[row] + weight2 * Directivity_table[row + 1]);
        //    freq_now = freq_now + freq_step;
        //    x = x + 1;
        //}
        impedance_r[x] = re_Zin;
        impedance_i[x] = im_Zin;
    }

    private void matrixIncrement(double[] lhs, double[] rhs) {
        // matlab is 1-indexed
        int i = 1;
        for (; i < lhs.length; i++) {
            lhs[i] += rhs[i];
        }
    }

    private void matrixIncrement(double[] lhs, double rhs) {
        // matlab is 1-indexed
        int i = 1;
        for (; i < lhs.length; i++) {
            lhs[i] += rhs;
        }
    }
}
