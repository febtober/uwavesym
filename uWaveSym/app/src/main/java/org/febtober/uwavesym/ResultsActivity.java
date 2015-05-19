package org.febtober.uwavesym;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class ResultsActivity extends Activity {
    Button button_magS11;
    Button button_vswr;
    Button button_angS11;
    Button button_s11Db;
    Button button_gainDb;
    Button button_radPattern;

    int radPatternResId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        button_magS11 = (Button) findViewById(R.id.button_magS11);
        button_vswr = (Button) findViewById(R.id.button_vswr);
        button_angS11 = (Button) findViewById(R.id.button_angS11);
        button_s11Db = (Button) findViewById(R.id.button_s11Db);
        button_gainDb = (Button) findViewById(R.id.button_gainDb);
        button_radPattern = (Button) findViewById(R.id.button_radiationPattern);

        radPatternResId = getIntent().getIntExtra("radPatternResId", 0);

        button_magS11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ResultsPlotActivity.class);
                double[] x = new double[]{1,2,3,4,5,6,7,8,9,10};
                double[] y = new double[]{1,2,3,4,5,6,7,8,9,10};
                intent.putExtra("x_values", x);
                intent.putExtra("y_values", y);
                startActivity(intent);
            }
        });
        button_vswr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ResultsPlotActivity.class);
//                intent.putExtra();
                startActivity(intent);
            }
        });
        button_angS11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ResultsPlotActivity.class);
//                intent.putExtra();
                startActivity(intent);
            }
        });
        button_s11Db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ResultsPlotActivity.class);
//                intent.putExtra();
                startActivity(intent);
            }
        });
        button_gainDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ResultsPlotActivity.class);
//                intent.putExtra();
                startActivity(intent);
            }
        });
        button_radPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("radPatternResId", radPatternResId);
                ImageViewFragment ivf = new ImageViewFragment();
                ivf.setArguments(bundle);
                ivf.show(getFragmentManager(), "radPattern");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_results, menu);
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
