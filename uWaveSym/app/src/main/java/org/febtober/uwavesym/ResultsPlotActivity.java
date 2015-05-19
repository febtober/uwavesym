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
import java.util.Arrays;
import java.util.List;


public class ResultsPlotActivity extends Activity {
    private XYPlot v_plot;
    SimpleXYSeries xy1Series;
    SimpleXYSeries xy2Series;
    LineAndPointFormatter lpf1;
    LineAndPointFormatter lpf2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_plot);

        v_plot = (XYPlot) findViewById(R.id.xyplot_results);
        Bundle extras = getIntent().getExtras();
        double[] x = extras.getDoubleArray("x");
        double[] y1 = extras.getDoubleArray("y1");
        double[] y2 = extras.getDoubleArray("y2"); // will be null if y2 does not exist

        // remove item at index 0 (is always 0 for compliance with original matlab code
        List<Number> xVals = doubleArrayToNumberList(Arrays.copyOfRange(x, 1, x.length));
        List<Number> y1Vals = doubleArrayToNumberList(Arrays.copyOfRange(y1, 1, y1.length));
        List<Number> y2Vals;
        if (y2 != null) {
            y2Vals = doubleArrayToNumberList(Arrays.copyOfRange(y2, 1, y2.length));
            xy2Series = new SimpleXYSeries(xVals, y2Vals, "plot2");
        }

        xy1Series = new SimpleXYSeries(xVals, y1Vals, "plot1");

        lpf1 = new LineAndPointFormatter(
                Color.RED,
                Color.BLUE,
                null,
                null
        );
        lpf2 = new LineAndPointFormatter(
                Color.GREEN,
                Color.YELLOW,
                null,
                null
        );

        v_plot.addSeries(xy1Series, lpf1);
        if (y2 != null)
            v_plot.addSeries(xy2Series, lpf2);
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
