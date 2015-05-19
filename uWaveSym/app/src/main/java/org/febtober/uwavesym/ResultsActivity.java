package org.febtober.uwavesym;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class ResultsActivity extends Activity {
    Button button_impedance;
    Button button_magS11;
    Button button_vswr;
    Button button_angS11;
    Button button_s11Db;
    Button button_gainDb;
    Button button_radPattern;

    private double[] impedance_r;
    private double[] impedance_i;
    private double[] D;
    private double[] DDB;
    private double[] currDistribution;
    double[] mag_S11;
    double[] VSWR;
    double[] ang_S11;
    double[] S11_dB;
    double[] Gain_dB;
    double[] frequencySweep;

    int radPatternResId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        button_impedance = (Button) findViewById(R.id.button_impedance);
        button_magS11 = (Button) findViewById(R.id.button_magS11);
        button_vswr = (Button) findViewById(R.id.button_vswr);
        button_angS11 = (Button) findViewById(R.id.button_angS11);
        button_s11Db = (Button) findViewById(R.id.button_s11Db);
        button_gainDb = (Button) findViewById(R.id.button_gainDb);
        button_radPattern = (Button) findViewById(R.id.button_radiationPattern);

        Bundle bundle = getIntent().getExtras();
        radPatternResId = bundle.getInt("radPatternResId", 0);
        impedance_r     = bundle.getDoubleArray("impedance_r");
        impedance_i     = bundle.getDoubleArray("impedance_i");
        D               = bundle.getDoubleArray("D");
        DDB             = bundle.getDoubleArray("DDB");
        currDistribution= bundle.getDoubleArray("currDistribution");
        mag_S11         = bundle.getDoubleArray("mag_S11");
        VSWR            = bundle.getDoubleArray("VSWR");
        ang_S11         = bundle.getDoubleArray("ang_S11");
        S11_dB          = bundle.getDoubleArray("S11_dB");
        Gain_dB         = bundle.getDoubleArray("Gain_dB");
        frequencySweep  = bundle.getDoubleArray("frequencySweep");

        button_impedance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ResultsPlotActivity.class);
                intent.putExtra("y1", impedance_r);
                intent.putExtra("y2", impedance_i);
                intent.putExtra("x", frequencySweep);
                startActivity(intent);
            }
        });
        button_magS11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ResultsPlotActivity.class);
                intent.putExtra("y1", mag_S11);
                intent.putExtra("x", frequencySweep);
                startActivity(intent);
            }
        });
        button_vswr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ResultsPlotActivity.class);
                intent.putExtra("y1", VSWR);
                intent.putExtra("x", frequencySweep);
                startActivity(intent);
            }
        });
        button_angS11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ResultsPlotActivity.class);
                intent.putExtra("y1", ang_S11);
                intent.putExtra("x", frequencySweep);
                startActivity(intent);
            }
        });
        button_s11Db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ResultsPlotActivity.class);
                intent.putExtra("y1", S11_dB);
                intent.putExtra("x", frequencySweep);
                startActivity(intent);
            }
        });
        button_gainDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ResultsPlotActivity.class);
                intent.putExtra("y1", Gain_dB);
                intent.putExtra("y2", DDB);
                intent.putExtra("x", frequencySweep);
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
