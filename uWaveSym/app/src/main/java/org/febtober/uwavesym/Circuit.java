package org.febtober.uwavesym;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Circuit {
    private List<Component> components = new ArrayList<>();

    private double frequency;
    private double substrateH;
    private double substrateP;

    public Circuit(double freq, double subH, double subP) {
        frequency = freq;
        substrateH = subH;
        substrateP = subP;
    }

    public void addComponent(Component comp) {
        components.add(comp);
    }

    public Component getComponent(int position) {
        return components.get(position);
    }

    public void updateComponent(Component modifiedComp) {
        Component wsComponent = null;
        Iterator<Component> it = components.iterator();
        while (it.hasNext()) {
            Component tmpComp = it.next();
            if (tmpComp.getComponentId() == modifiedComp.getComponentId()) {
                wsComponent = tmpComp;
                break;
            }
        }

        if (wsComponent == null) {
            // Component was not found in List
            return;
        }

        if (modifiedComp.getParam1Valid())
            wsComponent.setParam1(modifiedComp.getParam1());
        if (modifiedComp.getParam2Valid())
            wsComponent.setParam2(modifiedComp.getParam2());
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public double getSubstrateH() {
        return substrateH;
    }

    public void setSubstrateH(double subH) {
        this.substrateH = subH;
    }

    public double getSubstrateP() {
        return substrateP;
    }

    public void setSubstrateP(double substrateP) {
        this.substrateP = substrateP;
    }

    public int size() {
        return components.size();
    }
}
