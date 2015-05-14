package org.febtober.uwavesym;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class WorkspaceActivity extends Activity {
    String[] componentCategories;
    String[] antennas;
    String[] twoPortComponents;
    String[] onePortComponents;
    DrawerLayout componentsDrawer;
    GridView v_workspaceGrid;
    ActionBarDrawerToggle drawerToggle;
    ExpandableListView componentsListView;
    ExpandableListAdapter expListAdapter;
    HashMap<String, List<String>> componentsData;
    Button simulateButton;
    Button resultsButton;
    Button saveButton;

    List<Component> workspaceComponents = new ArrayList<>();
    List<ImageView> workspaceViews = new ArrayList<>();

    private BaseAdapter workspaceAdapter;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workspace);
        context = getApplicationContext();

        v_workspaceGrid = (GridView) findViewById(R.id.view_workspaceGrid);
        simulateButton = (Button) findViewById(R.id.button_simulate);
        resultsButton = (Button) findViewById(R.id.button_results);
        saveButton = (Button) findViewById(R.id.button_save);
        resultsButton.setEnabled(false);
        saveButton.setEnabled(false);
        setButtonOnClickListeners();
        setUpComponentsDrawer();
        setComponentOnClickListeners();
        Component.setContext(context);

        workspaceAdapter = new ComponentAdapter(context, workspaceComponents);
        v_workspaceGrid.setAdapter(workspaceAdapter);
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
                int compId = componentIdFromPositions(groupPosition, childPosition);
                if (compId == 0) {
                    return false;
                }

                Component comp = new Component(compId);
                workspaceComponents.add(comp);
                workspaceAdapter.notifyDataSetChanged();
                componentsDrawer.closeDrawer(componentsListView);
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
                    Component comp = new Component(componentIdFromPositions(groupPosition, childPosition));
                    componentsDrawer.closeDrawer(componentsListView);
                    int compId = componentIdFromPositions(groupPosition, childPosition);
                    if (compId == 0) {
                        return false;
                    }
                    Intent intent = new Intent(getApplicationContext(), ComponentEditor.class);
                    intent.putExtra("component", comp);
                    startActivityForResult(intent, ComponentEditor.EDIT_COMPONENT);
                    return true;
                }
                return false;
            }
        });
    }

    private int componentIdFromPositions(int groupPosition, int childPosition) {
        int compId = 0 ;
        switch (groupPosition) {
            case 0: // Antennas
                switch (childPosition) {
                    case 0: // Patch
                        compId = Component.PATCH;
                        break;
                    case 1: // Dipole
                        compId = Component.DIPOLE;
                        break;
                    case 2: // Monopole
                        compId = Component.MONOPOLE;
                        break;
                    case 3: // Loop
                        compId = Component.LOOP;
                        break;
                    default: // Error code
                        break;
                }
                break;
            case 1: // Two-port components
                switch (childPosition) {
                    case 0: // Balun
                        compId = Component.BALUN;
                        break;
                    case 1: // pi/4 transformer
                        compId = Component.QUARTER_TRANSFORMER;
                        break;
                    case 2: // T-line
                        compId = Component.T_LINE;
                        break;
                    case 3: // Resistor
                        compId = Component.RESISTOR;
                        break;
                    case 4: // Inductor
                        compId = Component.INDUCTOR;
                        break;
                    case 5: // Capacitor
                        compId = Component.CAPACITOR;
                        break;
                    case 6: // Termination
                        compId = Component.TERMINATION;
                        break;
                    case 7: // Substrate
                        compId = Component.SUBSTRATE;
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
        return compId;
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
                Component modifiedComp = intent.getParcelableExtra("component");
                int compType = modifiedComp.getComponentId();

                Component wsComponent = null;
                Iterator<Component> it = workspaceComponents.iterator();
                while (it.hasNext()) {
                    Component tmpComp = it.next();
                    if (tmpComp.getComponentId() == modifiedComp.getComponentId()) {
                        wsComponent = tmpComp;
                        break;
                    }
                }

                if (wsComponent == null) {
                    // Component was not found in List
                    return;
                }

                if (modifiedComp.getParam1Valid()) {
                    wsComponent.setParam1(modifiedComp.getParam1());
                }
                if (modifiedComp.getParam2Valid()) {
                    wsComponent.setParam2(modifiedComp.getParam2());
                }

            } else if (resultCode == RESULT_CANCELED) {
                // cancel button pressed
            }
        }
        else {
            // finish() called from some other activity
        }
    }

    private void setComponentOnClickListeners() {
        v_workspaceGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Component comp = workspaceComponents.get(position);
                Intent intent = new Intent(context, ComponentEditor.class);
                intent.putExtra("component", comp);
                startActivityForResult(intent, ComponentEditor.EDIT_COMPONENT);
            }
        });
    }
}
