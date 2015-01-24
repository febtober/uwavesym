package org.febtober.uwavesym;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class ComponentEditor extends ActionBarActivity {
    Button button_update;
    Button button_cancel;
    EditText etv_param1;
    TextView tv_param1Unit;
    EditText etv_param2;
    TextView tv_param2Unit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component_editor);

        Bundle componentData = getIntent().getExtras();
        Component componentIn = (Component) componentData.getParcelable("component");

        button_update = (Button) findViewById(R.id.button_update);
        button_cancel = (Button) findViewById(R.id.button_cancel);

        button_update.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        button_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        etv_param1 = (EditText) findViewById(R.id.param1);
        tv_param1Unit = (TextView) findViewById(R.id.param1Unit);
        etv_param2 = (EditText) findViewById(R.id.param2);
        tv_param2Unit = (TextView) findViewById(R.id.param2Unit);

        tv_param1Unit.setText(componentIn.getParam1Unit());
        tv_param2Unit.setText(componentIn.getParam2Unit());
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
