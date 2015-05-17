package org.febtober.uwavesym;

import android.os.AsyncTask;

import java.util.Arrays;
import java.util.List;

public class Simulator extends AsyncTask<Object, Integer, Circuit> {
    private Circuit circuit;
    private double impedance_r = 0;
    private double impedance_i = 0;
    private double frequency;
    private double freq_radian;
    private double w;
    private double c = 2.99792458e8;    // Speed of light
    double Zfo = 120*Math.PI;           // Free space impedance
    double t = 1.4 * 2.54e-5;           // Conductor thickness for 1 oz copper
    double e = Math.exp(1);
    double permitivity_free_space = 4e-7 * Math.PI;
    private double substrateH;
    private double substrateP;
    WorkspaceActivity parentActivity;

    public Simulator(WorkspaceActivity pActivity) {
        parentActivity = pActivity;
    }

    // params contains [Context, Circuit, WorkspaceActivity]
    @Override
    protected Circuit doInBackground(Object... params) {
        circuit = (Circuit) params[0];
        frequency = circuit.getFrequency();
        freq_radian = 2 * Math.PI * frequency;
        w = 2 * Math.PI * frequency;
        substrateH = circuit.getSubstrateH();
        substrateP = circuit.getSubstrateP();

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
                    break;
                case Component.MONOPOLE:
                    break;
                case Component.LOOP:
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
                    this.termination(param1);
                    break;
                default:
                    break;
            }
        }
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

    public List<Double> getResults() {
        return Arrays.asList(impedance_r, impedance_i);
    }

    private void resistor(double param1) {
        double r = param1;
        impedance_r += r;
    }
    
    private void resistorShunt(double param1) {
        // inputs
        double R = param1;                    // Resistance in ohms
        double re_Zin = impedance_r;                // Real Zout from previous component in ohms
        double im_Zin = impedance_i;                 // Imaginary Zout from previous component in ohms

        // Calculation
        double Yr = 1/R;                   // Given the inputs this is 0.01

        double re_Yin = 1/(Math.sqrt(Math.pow(re_Zin,2)+Math.pow(im_Zin,2)))*Math.cos(-Math.atan(im_Zin/re_Zin));
        double im_Yin = 1/(Math.sqrt(Math.pow(re_Zin,2)+Math.pow(im_Zin,2)))*Math.sin(-Math.atan(im_Zin/re_Zin));
        double A = re_Yin + Yr;
        double B = im_Yin;

        // Output
        double re_Zout = 1/(Math.sqrt(Math.pow(A,2)+Math.pow(B,2)))*Math.cos(-Math.atan(B/A));    // For given inputs 33
        double im_Zout = 1/(Math.sqrt(Math.pow(A,2)+Math.pow(B,2)))*Math.sin(-Math.atan(B/A));    // For given inputs j0

        impedance_r += re_Zout;
        impedance_i += im_Zout;
    }

    private void capacitor(double param1) {
        double c = param1;
        double z = -1 / (w * c);
        impedance_i += z;
    }
    
    private void capacitorShunt(double param1) {
        double freq = frequency;        // frequency in Hertz
        double C = param1;              // Capacitance in farads
        double re_Zin = impedance_r;    // Real Zout from previous component in ohms
        double im_Zin = impedance_i;    // Imaginary Zout from previous component
                
//        w = 2*Math.PI*freq;              // frequency in radians (automatic)
        double Yc = w*C;                   // Given the inputs this is j6.283 mS

        double re_Yin = 1/(Math.sqrt(Math.pow(re_Zin,2)+Math.pow(im_Zin,2)))*Math.cos(-Math.atan(im_Zin/re_Zin));
        double im_Yin = 1/(Math.sqrt(Math.pow(re_Zin,2)+Math.pow(im_Zin,2)))*Math.sin(-Math.atan(im_Zin/re_Zin));
        double A = re_Yin;
        double B = im_Yin + Yc;

        // Output
        double re_Zout = 1/(Math.sqrt(Math.pow(A,2)+Math.pow(B,2)))*Math.cos(-Math.atan(B/A));    // For given inputs 45
        double im_Zout = 1/(Math.sqrt(Math.pow(A,2)+Math.pow(B,2)))*Math.sin(-Math.atan(B/A));    // For given inputs -j14

        impedance_r += re_Zout;
        impedance_i += im_Zout;
    }

    private void inductor(double param1) {
        double l = param1;
        double z = w * l;
        impedance_i += z;
    }
    
    private void inductorShunt(double param1) {
        double freq = frequency;        // frequency in Hertz
        double L = param1;              // Inductance in Henrys
        double re_Zin = impedance_r;    // Real Zout from previous component in ohms
        double im_Zin = impedance_i;    // Imaginary Zout from previous component

        // Calculation
        w = 2*Math.PI*freq;             // frequency in radians (automatic)
        double Yl = -1/(w*L);           // Given the inputs this is j6.2832

        double re_Yin = 1/(Math.sqrt(Math.pow(re_Zin,2)+Math.pow(im_Zin,2)))*Math.cos(-Math.atan(im_Zin/re_Zin));
        double im_Yin = 1/(Math.sqrt(Math.pow(re_Zin,2)+Math.pow(im_Zin,2)))*Math.sin(-Math.atan(im_Zin/re_Zin));
        double A = re_Yin;
        double B = im_Yin + Yl;

        // Output
        double re_Zout = 1/(Math.sqrt(Math.pow(A,2)+Math.pow(B,2)))*Math.cos(-Math.atan(B/A));    // For given inputs 50
        double im_Zout = 1/(Math.sqrt(Math.pow(A,2)+Math.pow(B,2)))*Math.sin(-Math.atan(B/A));    // For given inputs j0.4

        impedance_r += re_Zout;
        impedance_i += im_Zout;
    }

    // param1 = length, param2 = width
    private void quarterTransformer(double param1, double param2) {
        // inputs
        double H = 20 * 2.54e-5;        // Substrate height in meters
        double Er = 4;                  // Permittivity of substrate
        double freq = frequency;        // frequency in Hertz
        double l = param1;              // Length of xmfr in meters
        double W = param2;              // Width in meters
        double re_Zin = impedance_r;    // real Zout from previous component in ohms
        double im_Zin = impedance_i;    // Imaginary Zout from previous component in ohms

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

        impedance_r += re_Zout;
        impedance_i += im_Zout;
    }
    
    private void termination(double param1) {
        // inputs
        double re_Zin = impedance_r;    // real Zout from previous component in ohms
        double im_Zin = impedance_i;    // imaginary Zout from previous component in ohms
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
        double mag_S11 = Math.sqrt(Math.pow(re_S11,2)+Math.pow(im_S11,2));
        double VSWR = 1+mag_S11/(1-mag_S11);
        double ang_S11 = top_ang-bot_ang;
        double S11_dB = 20*Math.log10(mag_S11);         // Return Loss
    }
}
