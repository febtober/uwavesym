package org.febtober.uwavesym;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

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
    Button simulateButton;
    Button resultsButton;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workspace);

        simulateButton = (Button) findViewById(R.id.button_simulate);
        resultsButton = (Button) findViewById(R.id.button_results);
        saveButton = (Button) findViewById(R.id.button_save);
        resultsButton.setEnabled(false);
        saveButton.setEnabled(false);
        setButtonOnClickListeners();

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

        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;

        componentsListView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                switch (groupPosition) {
                    case 0: // Antennas
                        switch (childPosition) {
                            case 0: // Patch
                                Toast.makeText(context, "Patch short", duration).show();
                                break;
                            case 1: // Dipole
                                Toast.makeText(context, "Dipole short", duration).show();
                                break;
                            case 2: // Monopole
                                Toast.makeText(context, "Monopole short", duration).show();
                                break;
                            case 3: // Loop
                                Toast.makeText(context, "Loop short", duration).show();
                                break;
                            default: // Error code
                                Toast.makeText(context, "Antenna error short", duration).show();
                                break;
                        }
                        break;
                    case 1: // Two-port components
                        switch (childPosition) {
                            case 0: // Balun
                                Toast.makeText(context, "Balun short", duration).show();
                                break;
                            case 1: // pi/4 transformer
                                Toast.makeText(context, "\u03BB/4 transformer short", duration).show();
                                break;
                            case 2: // T-line
                                Toast.makeText(context, "T-line short", duration).show();
                                break;
                            case 3: // Resistor
                                Toast.makeText(context, "Resistor short", duration).show();
                                break;
                            case 4: // Inductor
                                Toast.makeText(context, "Inductor short", duration).show();
                                break;
                            case 5: // Capacitor
                                Toast.makeText(context, "Capacitor short", duration).show();
                                break;
                            case 6: // Termination
                                Toast.makeText(context, "Termination short", duration).show();
                                break;
                            case 7: // Substrate
                                Toast.makeText(context, "Substrate short", duration).show();
                                break;
                            default: // Error code
                                Toast.makeText(context, "Two-port error short", duration).show();
                                break;
                        }
                        break;
                    case 2: // One-port components
                        switch (childPosition) {
                            case 0: // empty
                                Toast.makeText(context, "empty short", duration).show();
                                break;
                            default:
                                Toast.makeText(context, "One-port error short", duration).show();
                                break;
                        }
                        break;
                    default: // Error code
                        Toast.makeText(context, "Error short", duration).show();
                        break;
                }
                return true;
            }
        });


        componentsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (ExpandableListView.getPackedPositionType(id) ==
                        ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    int groupPosition = ExpandableListView.getPackedPositionGroup(id);
                    int childPosition = ExpandableListView.getPackedPositionChild(id);

                    switch (groupPosition) {
                        case 0: // Antennas
                            switch (childPosition) {
                                case 0: // Patch
                                    Toast.makeText(context, "Patch long", duration).show();
                                    break;
                                case 1: // Dipole
                                    Toast.makeText(context, "Dipole long", duration).show();
                                    break;
                                case 2: // Monopole
                                    Toast.makeText(context, "Monopole long", duration).show();
                                    break;
                                case 3: // Loop
                                    Toast.makeText(context, "Loop long", duration).show();
                                    break;
                                default: // Error code
                                    Toast.makeText(context, "Antennas error long", duration).show();
                                    break;
                            }
                            break;
                        case 1: // Two-port components
                            switch (childPosition) {
                                case 0: // Balun
                                    Toast.makeText(context, "Balun long", duration).show();
                                    break;
                                case 1: // pi/4 transformer
                                    Toast.makeText(context, "\u03BB/4 transformer long", duration).show();
                                    break;
                                case 2: // T-line
                                    Toast.makeText(context, "T-line long", duration).show();
                                    break;
                                case 3: // Resistor
                                    Toast.makeText(context, "Resistor long", duration).show();
                                    break;
                                case 4: // Inductor
                                    Toast.makeText(context, "Inductor long", duration).show();
                                    break;
                                case 5: // Capacitor
                                    Toast.makeText(context, "Capacitor long", duration).show();
                                    break;
                                case 6: // Termination
                                    Toast.makeText(context, "Termination long", duration).show();
                                    break;
                                case 7: // Substrate
                                    Toast.makeText(context, "Substrate long", duration).show();
                                    break;
                                default: // Error code
                                    Toast.makeText(context, "Two-port error long", duration).show();
                                    break;
                            }
                            break;
                        case 2: // One-port components
                            switch (childPosition) {
                                case 0: // empty
                                    Toast.makeText(context, "empty long", duration).show();
                                    break;
                                default:
                                    Toast.makeText(context, "One-port error long", duration).show();
                                    break;
                            }
                            break;
                        default: // Error code
                            Toast.makeText(context, "Error long", duration).show();
                            break;
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void setButtonOnClickListeners() {
        simulateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        resultsButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
