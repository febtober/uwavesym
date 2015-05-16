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
import android.view.ViewManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        ArrayAdapter<String> param1UnitsAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, comp.getParam1Units());
        param1UnitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_param1Units.setAdapter(param1UnitsAdapter);
        sp_param1Units.setSelection(comp.getParam1Prefix());

        if (comp.getParam2Exists()) {
            etv_param2.setHint(comp.getParam2String());
            if (comp.getParam2Valid()) {
                etv_param2.setText(String.valueOf(comp.getParam2()));
            }
            ArrayAdapter<String> param2UnitsAdapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, comp.getParam2Units());
            param2UnitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_param2Units.setAdapter(param2UnitsAdapter);
            sp_param2Units.setSelection(comp.getParam2Prefix());
        }
        else {
            LinearLayout editorButtons = (LinearLayout) findViewById(R.id.layout_editorBottomButtons);
            ((ViewManager) etv_param2.getParent()).removeView(etv_param2);
            ((ViewManager) sp_param2Units.getParent()).removeView(sp_param2Units);
        }

        tv_info.setText(Html.fromHtml(comp.getInfo()));

        Drawable drw_image = res.getDrawable(comp.getImgId());
        Drawable drw_equation;
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

                comp.setParam1Prefix(sp_param1Units.getSelectedItemPosition());
                if (comp.getParam2Exists())
                    comp.setParam2Prefix(sp_param2Units.getSelectedItemPosition());

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
