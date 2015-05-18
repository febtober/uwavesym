package org.febtober.uwavesym;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;

import java.util.ArrayList;
import java.util.List;


public class ResultsPlotActivity extends Activity {
    private XYPlot v_plot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_plot);

        v_plot = (XYPlot) findViewById(R.id.xyplot_results);
        Bundle extras = getIntent().getExtras();
        double[] x = extras.getDoubleArray("x_values");
        double[] y = extras.getDoubleArray("y_values");

        List<Number> xVals = doubleArrayToNumberList(x);
        List<Number> yVals = doubleArrayToNumberList(y);

        SimpleXYSeries xySeries = new SimpleXYSeries(xVals, yVals, "plot");

        LineAndPointFormatter formatter = new LineAndPointFormatter(
                Color.RED,
                Color.BLUE,
                Color.WHITE,
                null
        );

        v_plot.addSeries(xySeries, formatter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_results_plot, menu);
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

    private List<Number> doubleArrayToNumberList(double[] arg) {
        List<Number> n = new ArrayList<>();
        for (double d : arg) {
            n.add(new Double(d));
        }
        return n;
    }
}
