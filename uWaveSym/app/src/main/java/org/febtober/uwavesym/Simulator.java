package org.febtober.uwavesym;

import android.os.AsyncTask;

import java.util.Arrays;
import java.util.List;

public class Simulator extends AsyncTask<Object, Integer, Circuit> {
    private Circuit circuit;
    private double impedance_r, impedance_i, frequency, freq_radian, w, substrateH, substrateP;
    WorkspaceActivity parentActivity;

    public Simulator(WorkspaceActivity pActivity) {
        parentActivity = pActivity;
    }

    // params contains [Context, Circuit, WorkspaceActivity]
    @Override
    protected Circuit doInBackground(Object... params) {
        circuit = (Circuit) params[0];
        impedance_r = impedance_i = 0;
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
                    break;
                case Component.T_LINE:
                    break;
                case Component.RESISTOR:
                    this.resistor(param1);
                    break;
                case Component.RESISTOR_SHUNT:
                    break;
                case Component.INDUCTOR:
                    this.inductor(param1);
                    break;
                case Component.INDUCTOR_SHUNT:
                    break;
                case Component.CAPACITOR:
                    this.capacitor(param1);
                    break;
                case Component.CAPACITOR_SHUNT:
                    break;
                case Component.SUBSTRATE:
                    break;
                case Component.TERMINATION:
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

    private void capacitor(double param1) {
        double c = param1;
        double z = -1 / (w * c);
        impedance_i += z;
    }

    private void inductor(double param1) {
        double l = param1;
        double z = w * l;
        impedance_i += z;
    }
}
