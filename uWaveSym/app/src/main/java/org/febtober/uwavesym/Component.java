package org.febtober.uwavesym;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public static final int RESISTOR_SHUNT = 13;
    public static final int INDUCTOR_SHUNT = 14;
    public static final int CAPACITOR_SHUNT = 15;

    private int componentId = 0;
    private int imgId = 0;
    private int symId = 0;
    private int symViewId = 0;
    private static int currSymViewId = 1;
    private String name;
    private float param1 = 0;
    private float param2 = 0;
    private String param1String;
    private String param2String;
    private int param1Prefix = 0;   // index into param1Units list. is selected prefix.
    private int param2Prefix = 0;   // index into param2Units list. is selected prefix.
    private int param1MinPrefix = 0;
    private int param1MaxPrefix = 0;
    private int param2MinPrefix = 0;
    private int param2MaxPrefix = 0;
    private List<String> param1Units = new ArrayList<>(); // list of appropriate prefixes
    private List<String> param2Units = new ArrayList<>();
    private String info;
    private Component connection1 = null;
    private Component connection2 = null;
    private List<String> prefixes;

    private boolean param1Valid = false;
    private boolean param2Valid = false;
    private boolean param1Exists = true;
    private boolean param2Exists = true;

    private static Context context;

    public Component(int componentId) {
        this.componentId = componentId;
        Resources res = context.getResources();
        String[] prefixesArray = res.getStringArray(R.array.prefixes_abbrev);
        prefixes = new ArrayList<String>(Arrays.asList(prefixesArray));
        symViewId = currSymViewId++;

        switch (componentId) {
            case PATCH:
                imgId = R.drawable.img_blank;
                symId = R.drawable.sym_blank;
                name = res.getString(R.string.patch);
                param1String = res.getString(R.string.length);
                param2String = res.getString(R.string.width);
                param1MinPrefix = 2;
                param1MaxPrefix = 4;
                param2MinPrefix = 2;
                param2MaxPrefix = 4;
                param1Units = appendUnit(res.getString(R.string.unit_abbrev_meter));
                param2Units = appendUnit(res.getString(R.string.unit_abbrev_meter));
                info = res.getString(R.string.info_patch);
                break;
            case DIPOLE:
                imgId = R.drawable.img_blank;
                symId = R.drawable.sym_blank;
                name = res.getString(R.string.dipole);
                param1String = res.getString(R.string.length);
                param2String = res.getString(R.string.radius);
                param1MinPrefix = 2;
                param1MaxPrefix = 4;
                param2MinPrefix = 3;
                param2MaxPrefix = 3;
                param1Units = appendUnit(res.getString(R.string.unit_abbrev_meter));
                param2Units = appendUnit(res.getString(R.string.unit_abbrev_meter));
                info = res.getString(R.string.info_dipole);
                break;
            case MONOPOLE:
                imgId = R.drawable.img_monopole;
                symId = R.drawable.sym_blank;
                name = res.getString(R.string.monopole);
                param1String = res.getString(R.string.length);
                param2String = res.getString(R.string.radius);
                param1MinPrefix = 2;
                param1MaxPrefix = 4;
                param2MinPrefix = 3;
                param2MaxPrefix = 3;
                param1Units = appendUnit(res.getString(R.string.unit_abbrev_meter));
                param2Units = appendUnit(res.getString(R.string.unit_abbrev_meter));
                info = res.getString(R.string.info_monopole);
                break;
            case LOOP:
                imgId = R.drawable.img_loop;
                symId = R.drawable.sym_blank;
                name = res.getString(R.string.loop);
                param1String = res.getString(R.string.radius);
                param1MinPrefix = 2;
                param1MaxPrefix = 4;
                param1Units = appendUnit(res.getString(R.string.unit_abbrev_meter));
                param2Exists = false;
                info = res.getString(R.string.info_loop);
                break;
            case BALUN:
                imgId = R.drawable.img_blank;
                symId = R.drawable.sym_balun;
                name = res.getString(R.string.balun);
                param1Exists = param2Exists = false;
                info = res.getString(R.string.info_balun);
                break;
            case QUARTER_TRANSFORMER:
                imgId = R.drawable.img_blank;
                symId = R.drawable.sym_quarter_transformer;
                name = res.getString(R.string.quarterTransformer);
                param1String = res.getString(R.string.length);
                param2String = res.getString(R.string.width);
                param1MinPrefix = 2;
                param1MaxPrefix = 4;
                param2MinPrefix = 2;
                param2MaxPrefix = 4;
                param1Units = appendUnit(res.getString(R.string.unit_abbrev_meter));
                param2Units = appendUnit(res.getString(R.string.unit_abbrev_meter));
                info = res.getString(R.string.info_quarterTransformer);
                break;
            case T_LINE:
                imgId = R.drawable.img_blank;
                symId = R.drawable.sym_tline;
                name = res.getString(R.string.tLine);
                param1String = res.getString(R.string.length);
                param2String = res.getString(R.string.width);
                param1MinPrefix = 2;
                param1MaxPrefix = 4;
                param2MinPrefix = 2;
                param2MaxPrefix = 4;
                param1Units = appendUnit(res.getString(R.string.unit_abbrev_meter));
                param2Units = appendUnit(res.getString(R.string.unit_abbrev_meter));
                info = res.getString(R.string.info_tLine);
                break;
            case RESISTOR:
                imgId = R.drawable.img_resistor;
                symId = R.drawable.sym_resistor;
                name = res.getString(R.string.resistor);
                param1String = res.getString(R.string.resistance);
                param1MinPrefix = 4;
                param1MaxPrefix = 6;
                param2Exists = false;
                param1Units = appendUnit(res.getString(R.string.unit_abbrev_ohm));
                info = res.getString(R.string.info_resistor);
                break;
            case RESISTOR_SHUNT:
                imgId = R.drawable.img_resistor;
                symId = R.drawable.sym_resistor_shunt;
                name = res.getString(R.string.resistor_shunt);
                param1String = res.getString(R.string.resistance);
                param1MinPrefix = 4;
                param1MaxPrefix = 6;
                param1Units = appendUnit(res.getString(R.string.unit_abbrev_ohm));
                param2Exists = false;
                info = res.getString(R.string.info_resistor);
                break;
            case INDUCTOR:
                imgId = R.drawable.img_inductor;
                symId = R.drawable.sym_inductor;
                name = res.getString(R.string.inductor);
                param1String = res.getString(R.string.inductance);
                param1MinPrefix = 0;
                param1MaxPrefix = 2;
                param1Units = appendUnit(res.getString(R.string.unit_abbrev_henry));
                param2Exists = false;
                info = res.getString(R.string.info_inductor);
                break;
            case INDUCTOR_SHUNT:
                imgId = R.drawable.img_inductor;
                symId = R.drawable.sym_inductor_shunt;
                name = res.getString(R.string.inductor_shunt);
                param1String = res.getString(R.string.inductance);
                param1MinPrefix = 0;
                param1MaxPrefix = 2;
                param1Units = appendUnit(res.getString(R.string.unit_abbrev_henry));
                param2Exists = false;
                info = res.getString(R.string.info_inductor);
                break;
            case CAPACITOR:
                imgId = R.drawable.img_capacitor;
                symId = R.drawable.sym_capacitor;
                name = res.getString(R.string.capacitor);
                param1String = res.getString(R.string.capacitance);
                param1MinPrefix = 0;
                param1MaxPrefix = 2;
                param1Units = appendUnit(res.getString(R.string.unit_abbrev_farad));
                param2Exists = false;
                info = res.getString(R.string.info_capacitor);
                break;
            case CAPACITOR_SHUNT:
                imgId = R.drawable.img_capacitor;
                symId = R.drawable.sym_capacitor_shunt;
                name = res.getString(R.string.capacitor_shunt);
                param1String = res.getString(R.string.capacitance);
                param1MinPrefix = 0;
                param1MaxPrefix = 2;
                param1Units = appendUnit(res.getString(R.string.unit_abbrev_farad));
                param2Exists = false;
                info = res.getString(R.string.info_capacitor);
                break;
            case SUBSTRATE:
                imgId = R.drawable.img_substrate;
                symId = R.drawable.sym_blank;
                name = res.getString(R.string.substrate);
                param1String = res.getString(R.string.permittivity);
                param2String = res.getString(R.string.height);
                param1MinPrefix = 4;
                param1MaxPrefix = 4;
                param2MinPrefix = 4;
                param2MaxPrefix = 4;
                param1Units = appendUnit(res.getString(R.string.blank));
                param2Units = appendUnit(res.getString(R.string.blank));
                info = res.getString(R.string.info_substrate);
                break;
            case TERMINATION:
                imgId = R.drawable.img_blank;
                symId = R.drawable.sym_termination;
                name = res.getString(R.string.termination);
                param1String = res.getString(R.string.resistance);
                param1MinPrefix = 2;
                param1MaxPrefix = 4;
                param1Units = appendUnit(res.getString(R.string.unit_abbrev_ohm));
                param1 = 50;
                param1Valid = true;
                param1Prefix = 2;
                param2Exists = false;
                info = res.getString(R.string.info_termination);
                break;
            default:
                break;
        }
        if (param1Exists)
            param1Units = param1Units.subList(param1MinPrefix, param1MaxPrefix+1);
        if (param2Exists)
            param2Units = param2Units.subList(param2MinPrefix, param2MaxPrefix+1);
    }

    private List<String> appendUnit(String unit) {
        List<String> units = new ArrayList<>();
        for (String prefix: prefixes) {
            String u = prefix.concat(unit);
            units.add(u);
        }
        return units;
    }

    public void setComponentId(int a) {componentId = a;}
    public void setImgId(int a) {imgId = a;}
    public void setSymId(int a) {symId = a;}
    public void setSymViewId(int a) {symViewId = a;}
    public void setName(String a) {name = a;}
    public void setParam1(float a) {param1 = a; param1Valid = true;}
    public void setParam2(float a) {param2 = a; param2Valid = true;}
    public void setParam1String(String a) {param1String = a;}
    public void setParam2String(String a) {param2String = a;}
    public void setParam1Prefix(int a) {param1Prefix = a;}
    public void setParam2Prefix(int a) {param2Prefix = a;}
    public void setParam1MinPrefix(int a) {
        param1MinPrefix = a;}
    public void setParam2MinPrefix(int a) {
        param2MinPrefix = a;}
    public void setParam1MaxPrefix(int a) {
        param1MaxPrefix = a;}
    public void setParam2MaxPrefix(int a) {
        param2MaxPrefix = a;}
    public void setParam1Units(List<String> a) {param1Units = a;}
    public void setParam2Units(List<String> a) {param2Units = a;}
    public void setInfo(String a) {info = a;}
    public void setConnection1(Component a) {connection1 = a;}
    public void setConnection2(Component a) {connection2 = a;}

    public int getComponentId() {return componentId;}
    public int getImgId() {return imgId;}
    public int getSymId() {return symId;}
    public int getSymViewId() {return symViewId;}
    public String getName() {return name;}
    public float getParam1() {return param1;}
    public float getParam2() {return param2;}
    public String getParam1String() {return param1String;}
    public String getParam2String() {return param2String;}
    public int getParam1Prefix() {return param1Prefix;}
    public int getParam2Prefix() {return param2Prefix;}
    public int getParam1MinPrefix() {return param1MinPrefix;}
    public int getParam2MinPrefix() {return param2MinPrefix;}
    public int getParam1MaxPrefix() {return param1MaxPrefix;}
    public int getParam2MaxPrefix() {return param2MaxPrefix;}
    public List<String> getParam1Units() {return param1Units;}
    public List<String> getParam2Units() {return param2Units;}
    public String getInfo() {return info;}
    public Component getConnection1() {return connection1;}
    public Component getConnection2() {return connection2;}
    public boolean getParam1Valid() {return param1Valid;}
    public boolean getParam2Valid() {return param2Valid;}
    public boolean getParam1Exists() {return param1Exists;}
    public boolean getParam2Exists() {return param2Exists;}

    public static void setContext(Context mcontext) {
        if (context == null)
            context = mcontext;
    }

    // Parcelling part. Pulled from parcelabler.com
    protected Component(Parcel in) {
        componentId = in.readInt();
        imgId = in.readInt();
        symId = in.readInt();
        symViewId = in.readInt();
        name = in.readString();
        param1 = in.readFloat();
        param2 = in.readFloat();
        param1String = in.readString();
        param2String = in.readString();
        param1Prefix = in.readInt();
        param2Prefix = in.readInt();
        param1MinPrefix = in.readInt();
        param2MinPrefix = in.readInt();
        param1MaxPrefix = in.readInt();
        param2MaxPrefix = in.readInt();
        in.readStringList(param1Units);
        in.readStringList(param2Units);
        info = in.readString();
        connection1 = (Component) in.readValue(Component.class.getClassLoader());
        connection2 = (Component) in.readValue(Component.class.getClassLoader());
        param1Valid = in.readInt() != 0;
        param2Valid = in.readInt() != 0;
        param1Exists = in.readInt() != 0;
        param2Exists = in.readInt() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(componentId);
        dest.writeInt(imgId);
        dest.writeInt(symId);
        dest.writeInt(symViewId);
        dest.writeString(name);
        dest.writeFloat(param1);
        dest.writeFloat(param2);
        dest.writeString(param1String);
        dest.writeString(param2String);
        dest.writeInt(param1Prefix);
        dest.writeInt(param2Prefix);
        dest.writeInt(param1MinPrefix);
        dest.writeInt(param2MinPrefix);
        dest.writeInt(param1MaxPrefix);
        dest.writeInt(param2MaxPrefix);
        dest.writeStringList(param1Units);
        dest.writeStringList(param2Units);
        dest.writeString(info);
        dest.writeValue(connection1);
        dest.writeValue(connection2);
        dest.writeInt(param1Valid ? 1 : 0);
        dest.writeInt(param2Valid ? 1 : 0);
        dest.writeInt(param1Exists ? 1 : 0);
        dest.writeInt(param2Exists ? 1 : 0);
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
