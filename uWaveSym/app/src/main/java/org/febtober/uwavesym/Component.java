package org.febtober.uwavesym;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

public class Component implements Parcelable {
    public static final int PATCH = 1;
    public static final int DIPOLE = 2;
    public static final int MONOPOLE = 3;
    public static final int LOOP = 4;
    public static final int BALUN = 5;
    public static final int QUARTER_TRANSFORMER = 6;
    public static final int T_LINE = 7;
    public static final int RESISTOR = 8;
    public static final int INDUCTOR = 9;
    public static final int CAPACITOR = 10;
    public static final int SUBSTRATE = 11;
    public static final int TERMINATION = 12;

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

    private boolean param1Valid = false;
    private boolean param2Valid = false;

    private static Context context;

    public Component(int componentId) {
        connection1 = null;
        connection2 = null;
        Resources res = context.getResources();

        switch (componentId) {
            case PATCH:
                name = res.getString(R.string.patch);
                param1String = res.getString(R.string.length);
                param2String = res.getString(R.string.width);
                param1Min = 0;
                param2Min = 0;
                param1Max = 10;
                param2Max = 10;
                param1Unit = res.getString(R.string.unit_mm);
                param2Unit = res.getString(R.string.unit_mm);
                info = res.getString(R.string.info_patch);
                break;
            case DIPOLE:
                name = res.getString(R.string.dipole);
                param1String = res.getString(R.string.length);
                param2String = res.getString(R.string.width);
                param1Min = 0;
                param2Min = 0;
                param1Max = 10;
                param2Max = 10;
                param1Unit = res.getString(R.string.unit_mm);
                param2Unit = res.getString(R.string.unit_mm);
                info = res.getString(R.string.info_dipole);
                break;
            case MONOPOLE:
                name = res.getString(R.string.monopole);
                param1String = res.getString(R.string.length);
                param2String = res.getString(R.string.width);
                param1Min = 0;
                param2Min = 0;
                param1Max = 10;
                param2Max = 10;
                param1Unit = res.getString(R.string.unit_mm);
                param2Unit = res.getString(R.string.unit_mm);
                info = res.getString(R.string.info_monopole);
                break;
            case LOOP:
                name = res.getString(R.string.loop);
                param1String = res.getString(R.string.length);
                param2String = res.getString(R.string.width);
                param1Min = 0;
                param2Min = 0;
                param1Max = 10;
                param2Max = 10;
                param1Unit = res.getString(R.string.unit_mm);
                param2Unit = res.getString(R.string.unit_mm);
                info = res.getString(R.string.info_loop);
                break;
            case BALUN:
                name = res.getString(R.string.balun);
                param1String = res.getString(R.string.resistance);
                param2String = res.getString(R.string.capacitance);
                param1Min = 0;
                param2Min = 0;
                param1Max = 10;
                param2Max = 10;
                param1Unit = res.getString(R.string.unit_ohm);
                param2Unit = res.getString(R.string.unit_pf);
                info = res.getString(R.string.info_balun);
                break;
            case QUARTER_TRANSFORMER:
                name = res.getString(R.string.quarterTransformer);
                param1String = res.getString(R.string.frequency);
                param2String = res.getString(R.string.frequency);
                param1Min = 0;
                param2Min = 0;
                param1Max = 10;
                param2Max = 10;
                param1Unit = res.getString(R.string.unit_mhz);
                param2Unit = res.getString(R.string.unit_ghz);
                info = res.getString(R.string.info_quarterTransformer);
                break;
            case T_LINE:
                name = res.getString(R.string.tLine);
                param1String = res.getString(R.string.length);
                param2String = res.getString(R.string.width);
                param1Min = 0;
                param2Min = 0;
                param1Max = 10;
                param2Max = 10;
                param1Unit = res.getString(R.string.unit_mm);
                param2Unit = res.getString(R.string.unit_mm);
                info = res.getString(R.string.info_tLine);
                break;
            case RESISTOR:
                name = res.getString(R.string.resistor);
                param1String = res.getString(R.string.resistance);
                param2String = res.getString(R.string.resistance);
                param1Min = 0;
                param2Min = 0;
                param1Max = 10;
                param2Max = 10;
                param1Unit = res.getString(R.string.unit_mohm);
                param2Unit = res.getString(R.string.unit_megaohm);
                info = res.getString(R.string.info_resistor);
                break;
            case INDUCTOR:
                name = res.getString(R.string.inductor);
                param1String = res.getString(R.string.inductance);
                param2String = res.getString(R.string.inductance);
                param1Min = 0;
                param2Min = 0;
                param1Max = 10;
                param2Max = 10;
                param1Unit = res.getString(R.string.unit_mhenry);
                param2Unit = res.getString(R.string.unit_uhenry);
                info = res.getString(R.string.info_inductor);
                break;
            case CAPACITOR:
                name = res.getString(R.string.capacitor);
                param1String = res.getString(R.string.capacitance);
                param2String = res.getString(R.string.capacitance);
                param1Min = 0;
                param2Min = 0;
                param1Max = 10;
                param2Max = 10;
                param1Unit = res.getString(R.string.unit_pf);
                param2Unit = res.getString(R.string.unit_uf);
                info = res.getString(R.string.info_capacitor);
                break;
            case SUBSTRATE:
                name = res.getString(R.string.substrate);
                param1String = res.getString(R.string.length);
                param2String = res.getString(R.string.width);
                param1Min = 0;
                param2Min = 0;
                param1Max = 10;
                param2Max = 10;
                param1Unit = res.getString(R.string.unit_mm);
                param2Unit = res.getString(R.string.unit_mm);
                info = res.getString(R.string.info_substrate);
                break;
            case TERMINATION:
                name = res.getString(R.string.termination);
                param1String = res.getString(R.string.length);
                param2String = res.getString(R.string.width);
                param1Min = 0;
                param2Min = 0;
                param1Max = 10;
                param2Max = 10;
                param1Unit = res.getString(R.string.unit_mm);
                param2Unit = res.getString(R.string.unit_mm);
                info = res.getString(R.string.info_termination);
                break;
            default:
                break;
        }
    }

    public void setName(String a) {name = a;}
    public void setParam1(float a) {param1 = a; param1Valid = true;}
    public void setParam2(float a) {param2 = a; param2Valid = true;}
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

    public String getName() {return name;}
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
    public boolean getParam1Valid() {return param1Valid;}
    public boolean getParam2Valid() {return param2Valid;}

    public static void setContext(Context mcontext) {
        if (context == null)
            context = mcontext;
    }

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
        param1Valid = in.readInt() != 0;
        param2Valid = in.readInt() != 0;
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
        dest.writeInt(param1Valid ? 1 : 0);
        dest.writeInt(param2Valid ? 1 : 0);
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
