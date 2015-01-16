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

        componentsListView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                switch (groupPosition) {
                    case 0: // Antennas
                        switch (childPosition) {
                            case 0: // Patch
                                break;
                            case 1: // Dipole
                                break;
                            case 2: // Monopole
                                break;
                            case 3: // Loop
                                break;
                            default: // Error code
                                break;
                        }
                        break;
                    case 1: // Two-port components
                        switch (childPosition) {
                            case 0: // Balun
                                break;
                            case 1: // pi/4 transformer
                                break;
                            case 2: // T-line
                                break;
                            case 3: // Resistor
                                break;
                            case 4: // Inductor
                                break;
                            case 5: // Capacitor
                                break;
                            case 6: // Termination
                                break;
                            case 7: // Substrate
                                break;
                            default: // Error code
                                break;
                        }
                        break;
                    case 2: // One-port components
                        switch (childPosition) {
                            case 0: // empty
                                break;
                            default:
                                break;
                        }
                        break;
                    default: // Error code
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
                                    break;
                                case 1: // Dipole
                                    break;
                                case 2: // Monopole
                                    break;
                                case 3: // Loop
                                    break;
                                default: // Error code
                                    break;
                            }
                            break;
                        case 1: // Two-port components
                            switch (childPosition) {
                                case 0: // Balun
                                    break;
                                case 1: // pi/4 transformer
                                    break;
                                case 2: // T-line
                                    break;
                                case 3: // Resistor
                                    break;
                                case 4: // Inductor
                                    break;
                                case 5: // Capacitor
                                    break;
                                case 6: // Termination
                                    break;
                                case 7: // Substrate
                                    break;
                                default: // Error code
                                    break;
                            }
                            break;
                        case 2: // One-port components
                            switch (childPosition) {
                                case 0: // empty
                                    break;
                                default:
                                    break;
                            }
                            break;
                        default: // Error code
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
