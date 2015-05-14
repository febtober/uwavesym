package org.febtober.uwavesym;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Circuit {
    private List<Component> components = new ArrayList<>();

    private float frequency;
    private float dielectric;

    public Circuit(float freq, float diel) {
        frequency = freq;
        dielectric = diel;
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

    public float getFrequency() {
        return frequency;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    public float getDielectric() {
        return dielectric;
    }

    public void setDielectric(float dielectric) {
        this.dielectric = dielectric;
    }

    public int size() {
        return components.size();
    }
}
