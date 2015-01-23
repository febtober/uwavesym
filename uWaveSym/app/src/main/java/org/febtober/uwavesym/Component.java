package org.febtober.uwavesym;

import android.os.Parcel;
import android.os.Parcelable;

public class Component implements Parcelable {
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
    private Component connection1;
    private Component connection2;

    public Component() {
        connection1 = null;
        connection2 = null;
    }

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
    public void setConnection1(Component a) {connection1 = a;}
    public void setConnection2(Component a) {connection2 = a;}

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
    public Component getConnection1() {return connection1;}
    public Component getConnection2() {return connection2;}

    // Parcelling part. Pulled from parcelabler.com
    protected Component(Parcel in) {
        name = in.readString();
        param1 = in.readFloat();
        param2 = in.readFloat();
        param1String = in.readString();
        param2String = in.readString();
        param1Min = in.readFloat();
        param2Min = in.readFloat();
        param1Max = in.readFloat();
        param2Max = in.readFloat();
        param1Unit = in.readString();
        param2Unit = in.readString();
        info = in.readString();
        connection1 = (Component) in.readValue(Component.class.getClassLoader());
        connection2 = (Component) in.readValue(Component.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(param1);
        dest.writeFloat(param2);
        dest.writeString(param1String);
        dest.writeString(param2String);
        dest.writeFloat(param1Min);
        dest.writeFloat(param2Min);
        dest.writeFloat(param1Max);
        dest.writeFloat(param2Max);
        dest.writeString(param1Unit);
        dest.writeString(param2Unit);
        dest.writeString(info);
        dest.writeValue(connection1);
        dest.writeValue(connection2);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Component> CREATOR = new Parcelable.Creator<Component>() {
        @Override
        public Component createFromParcel(Parcel in) {
            return new Component(in);
        }

        @Override
        public Component[] newArray(int size) {
            return new Component[size];
        }
    };
}
