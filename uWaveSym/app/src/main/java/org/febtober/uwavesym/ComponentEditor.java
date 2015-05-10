package org.febtober.uwavesym;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


public class ComponentEditor extends Activity {
    Button button_update;
    Button button_cancel;
    TextView tv_componentName;
    EditText etv_param1;
    Spinner sp_param1Units;
    EditText etv_param2;
    Spinner sp_param2Units;
    TextView tv_info;
    ImageView iv_image;
    Component comp;

    public static final int EDIT_COMPONENT = 0x20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component_editor);
        Resources res = getResources();

        tv_componentName = (TextView) findViewById(R.id.text_componentName);
        etv_param1 = (EditText) findViewById(R.id.param1);
        sp_param1Units = (Spinner) findViewById(R.id.param1Units);
        etv_param2 = (EditText) findViewById(R.id.param2);
        sp_param2Units = (Spinner) findViewById(R.id.param2Units);
        tv_info = (TextView) findViewById(R.id.text_componentInfo);
        iv_image = (ImageView) findViewById(R.id.image_component);

        button_update = (Button) findViewById(R.id.button_update);
        button_cancel = (Button) findViewById(R.id.button_cancel);

        comp = (Component) getIntent().getExtras().getParcelable("component");

        tv_componentName.setText(comp.getName());
        etv_param1.setHint(comp.getParam1String());
        if (comp.getParam1Valid()) {etv_param1.setText(String.valueOf(comp.getParam1()));}
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, comp.getParam1Units());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_param1Units.setAdapter(spinnerAdapter);
        etv_param2.setHint(comp.getParam2String());
        if (comp.getParam2Valid()) {etv_param2.setText(String.valueOf(comp.getParam2()));}
        spinnerAdapter.clear();
        spinnerAdapter.addAll(comp.getParam2Units());
        sp_param2Units.setAdapter(spinnerAdapter);

        tv_info.setText(Html.fromHtml(comp.getInfo()));

        Drawable drw_image = null;
        Drawable drw_equation;
        switch(comp.getComponentId()) {
            case(Component.PATCH):
                break;
            case(Component.DIPOLE):
                break;
            case(Component.MONOPOLE):
                drw_image = res.getDrawable(R.drawable.img_monopole);
                break;
            case(Component.LOOP):
                drw_image = res.getDrawable(R.drawable.img_loop);
                break;
            case(Component.BALUN):
                break;
            case(Component.QUARTER_TRANSFORMER):
                break;
            case(Component.T_LINE):
                break;
            case(Component.RESISTOR):
                drw_image = res.getDrawable(R.drawable.img_resistor);
                break;
            case(Component.INDUCTOR):
                drw_image = res.getDrawable(R.drawable.img_inductor);
                break;
            case(Component.CAPACITOR):
                drw_image = res.getDrawable(R.drawable.img_capacitor);
                break;
            case(Component.SUBSTRATE):
                drw_image = res.getDrawable(R.drawable.img_substrate);
                break;
            case(Component.TERMINATION):
                break;
        }
        if (iv_image != null) {
            iv_image.setImageDrawable(drw_image);
        }

        button_update.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String param1String = etv_param1.getText().toString();
                if (param1String.length() > 0) {
                    comp.setParam1(Float.valueOf(param1String));
                }
                String param2String = etv_param2.getText().toString();
                if (param2String.length() > 0) {
                    comp.setParam2(Float.valueOf(param2String));
                }
                Intent returnIntent = new Intent();
                returnIntent.putExtra("component", comp);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
        button_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(RESULT_CANCELED, returnIntent);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_component_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
