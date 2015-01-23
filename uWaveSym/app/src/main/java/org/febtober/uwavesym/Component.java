package org.febtober.uwavesym;

public class Component {
    private String name;
    private float attr1;
    private float attr2;
    private String attr1String;
    private String attr2String;
    private float attr1Min;
    private float attr2Min;
    private float attr1Max;
    private float attr2Max;
    private String attr1Unit;
    private String attr2Unit;
    private String info;

    public Component() { }

    public void setName(String a) {name = a;}
    public void setAttr1(float a) {attr1 = a;}
    public void setAttr2(float a) {attr2 = a;}
    public void setAttr1String(String a) {attr1String = a;}
    public void setAttr2String(String a) {attr2String = a;}
    public void setAttr1Min(float a) {attr1Min = a;}
    public void setAttr2Min(float a) {attr2Min = a;}
    public void setAttr1Max(float a) {attr1Max = a;}
    public void setAttr2Max(float a) {attr2Max = a;}
    public void setAttr1Unit(String a) {attr1Unit = a;}
    public void setAttr2Unit(String a) {attr2Unit = a;}
    public void setInfo(String a) {info = a;}

    public float getAttr1() {return attr1;}
    public float getAttr2() {return attr2;}
    public String getAttr1String() {return attr1String;}
    public String getAttr2String() {return attr2String;}
    public float getAttr1Min() {return attr1Min;}
    public float getAttr2Min() {return attr2Min;}
    public float getAttr1Max() {return attr1Max;}
    public float getAttr2Max() {return attr2Max;}
    public String getAttr1Unit() {return attr1Unit;}
    public String getAttr2Unit() {return attr2Unit;}
    public String getInfo() {return info;}
}
