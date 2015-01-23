package org.febtober.uwavesym;

public class Component {
    private String name;
    private float param1;
    private float param2;
    private String param1String;
    private String param2String;
    private float param1Min;
    private float param2Min;
    private float param1Max;
    private float param2Max;
    private String param1Unit;
    private String param2Unit;
    private String info;

    public Component() { }

    public void setName(String a) {name = a;}
    public void setParam1(float a) {param1 = a;}
    public void setParam2(float a) {param2 = a;}
    public void setParam1String(String a) {param1String = a;}
    public void setParam2String(String a) {param2String = a;}
    public void setParam1Min(float a) {param1Min = a;}
    public void setParam2Min(float a) {param2Min = a;}
    public void setParam1Max(float a) {param1Max = a;}
    public void setParam2Max(float a) {param2Max = a;}
    public void setParam1Unit(String a) {param1Unit = a;}
    public void setParam2Unit(String a) {param2Unit = a;}
    public void setInfo(String a) {info = a;}

    public float getParam1() {return param1;}
    public float getParam2() {return param2;}
    public String getParam1String() {return param1String;}
    public String getParam2String() {return param2String;}
    public float getParam1Min() {return param1Min;}
    public float getParam2Min() {return param2Min;}
    public float getParam1Max() {return param1Max;}
    public float getParam2Max() {return param2Max;}
    public String getParam1Unit() {return param1Unit;}
    public String getParam2Unit() {return param2Unit;}
    public String getInfo() {return info;}
}
