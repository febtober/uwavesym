package org.febtober.uwavesym;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.res.Resources;
import android.graphics.Point;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class WorkspaceActivity extends ActionBarActivity {
    String[] componentCategories;
    String[] antennas;
    String[] twoPortComponents;
    String[] onePortComponents;
    DrawerLayout componentsDrawer;
    ExpandableListView componentsListView;
    LinearLayout antennasList;
    LinearLayout twoPortCompsList;
    LinearLayout onePortCompsList;
    BaseExpandableListAdapter expListAdapter;
    HashMap<String, List<String> > componentsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workspace);

        setUpComponentsDrawer();
    }

    private void setUpComponentsDrawer() {
        Resources res = getResources();
        componentCategories = res.getStringArray(R.array.drawer_componentCategories);
        antennas = res.getStringArray(R.array.drawer_antennas);
        twoPortComponents = res.getStringArray(R.array.drawer_twoPortComponents);
        onePortComponents = res.getStringArray(R.array.drawer_onePortComponents);
        componentsDrawer = (DrawerLayout) findViewById(R.id.workspace_drawerLayout);
        componentsListView = (ExpandableListView) findViewById(R.id.workspace_leftDrawer);

        List<String> catsList = new ArrayList<String>(Arrays.asList(componentCategories));
        List<String> antennasList = new ArrayList<String>(Arrays.asList(antennas));
        List<String> twoPortCompsList = new ArrayList<String>(Arrays.asList(twoPortComponents));
        List<String> onePortCompsList = new ArrayList<String>(Arrays.asList(onePortComponents));
        componentsData = new HashMap<String, List<String> >();
        componentsData.put(catsList.get(0), antennasList);
        componentsData.put(catsList.get(1), twoPortCompsList);
        componentsData.put(catsList.get(2), onePortCompsList);
        expListAdapter = new ExpandableListAdapter(this, catsList, componentsData);
        componentsListView.setAdapter(expListAdapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_workspace, menu);
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
