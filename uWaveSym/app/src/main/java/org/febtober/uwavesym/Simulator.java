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
                    this.resistor(currComp);
                    break;
                case Component.RESISTOR_SHUNT:
                    break;
                case Component.INDUCTOR:
                    this.inductor(currComp);
                    break;
                case Component.INDUCTOR_SHUNT:
                    break;
                case Component.CAPACITOR:
                    this.capacitor(currComp);
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

    private void resistor(Component comp) {
        double r = comp.getParam1();
        impedance_r += r;
    }

    private void capacitor(Component comp) {
        double c = comp.getParam1();
        double z = -1 / (w * c);
        impedance_i += z;
    }

    private void inductor(Component comp) {
        double l = comp.getParam1();
        double z = w * l;
        impedance_i += z;
    }
}
