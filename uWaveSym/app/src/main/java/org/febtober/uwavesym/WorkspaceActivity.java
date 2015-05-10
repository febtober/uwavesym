package org.febtober.uwavesym;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class WorkspaceActivity extends Activity {
    String[] componentCategories;
    String[] antennas;
    String[] twoPortComponents;
    String[] onePortComponents;
    DrawerLayout componentsDrawer;
    ActionBarDrawerToggle drawerToggle;
    ExpandableListView componentsListView;
    ExpandableListAdapter expListAdapter;
    HashMap<String, List<String>> componentsData;
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
        Component.setContext(getApplicationContext());
    }

    private void setUpComponentsDrawer() {
        Resources res = getResources();
        componentCategories = res.getStringArray(R.array.drawer_componentCategories);
        antennas = res.getStringArray(R.array.drawer_antennas);
        twoPortComponents = res.getStringArray(R.array.drawer_twoPortComponents);
        onePortComponents = res.getStringArray(R.array.drawer_onePortComponents);
        componentsDrawer = (DrawerLayout) findViewById(R.id.workspace_drawerLayout);
        componentsListView = (ExpandableListView) findViewById(R.id.workspace_leftDrawer);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(
                this,
                componentsDrawer,
                R.string.drawer_close,
                R.string.drawer_open
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(R.string.app_name);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View view) {
                getActionBar().setTitle(R.string.components);
                invalidateOptionsMenu();
            }
        };

        componentsDrawer.setDrawerListener(drawerToggle);

        List<String> catsList = new ArrayList<>(Arrays.asList(componentCategories));
        List<String> antennasList = new ArrayList<>(Arrays.asList(antennas));
        List<String> twoPortCompsList = new ArrayList<>(Arrays.asList(twoPortComponents));
        List<String> onePortCompsList = new ArrayList<>(Arrays.asList(onePortComponents));
        componentsData = new HashMap<>();
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
                    long packedPos = ((ExpandableListView) parent).getExpandableListPosition(position);
                    int groupPosition = ExpandableListView.getPackedPositionGroup(packedPos);
                    int childPosition = ExpandableListView.getPackedPositionChild(packedPos);
                    Resources res = getResources();
                    Component comp = null;

                    switch (groupPosition) {
                        case 0: // Antennas
                            switch (childPosition) {
                                case 0: // Patch
                                    comp = new Component(Component.PATCH);
                                    break;
                                case 1: // Dipole
                                    comp = new Component(Component.DIPOLE);
                                    break;
                                case 2: // Monopole
                                    comp = new Component(Component.MONOPOLE);
                                    break;
                                case 3: // Loop
                                    comp = new Component(Component.LOOP);
                                    break;
                                default: // Error code
                                    break;
                            }
                            break;
                        case 1: // Two-port components
                            switch (childPosition) {
                                case 0: // Balun
                                    comp = new Component(Component.BALUN);
                                    break;
                                case 1: // pi/4 transformer
                                    comp = new Component(Component.QUARTER_TRANSFORMER);
                                    break;
                                case 2: // T-line
                                    comp = new Component(Component.T_LINE);
                                    break;
                                case 3: // Resistor
                                    comp = new Component(Component.RESISTOR);
                                    break;
                                case 4: // Inductor
                                    comp = new Component(Component.INDUCTOR);
                                    break;
                                case 5: // Capacitor
                                    comp = new Component(Component.CAPACITOR);
                                    break;
                                case 6: // Termination
                                    comp = new Component(Component.TERMINATION);
                                    break;
                                case 7: // Substrate
                                    comp = new Component(Component.SUBSTRATE);
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
                    componentsDrawer.closeDrawer(componentsListView);
                    if (comp != null) {
                        Intent intent = new Intent(getApplicationContext(), ComponentEditor.class);
                        intent.putExtra("component", comp);
                        startActivityForResult(intent, ComponentEditor.EDIT_COMPONENT);
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
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == ComponentEditor.EDIT_COMPONENT) {
            if (resultCode == RESULT_OK) {
                // update button pressed
                Component comp = intent.getParcelableExtra("component");
            } else if (resultCode == RESULT_CANCELED) {
                // cancel button pressed
            }
        }
        else {
            // finish() called from some other activity
        }
    }
}
