package at.tugraz.beislrallye;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private ListView locationTypeLV;
    private EditText locationCount;
    private EditText startPoint;
    private GoogleApiClient mGoogleApiClient;
    private List<String> types;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        startPoint = (EditText) findViewById(R.id.start_point);
        locationCount = (EditText) findViewById(R.id.location_count);
        locationTypeLV = (ListView) findViewById(R.id.location_type_lv);

        ArrayAdapter<String> adapter;

        types = new ArrayList<>();
        types.add("Bar");
        types.add("Cafe");
        types.add("Nightclub");
        types.add("Restraurant");

        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_multiple_choice,
                types);
        locationTypeLV.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        locationTypeLV.setAdapter(adapter);

        findViewById(R.id.compute_ralley_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOnComputeClick();
            }
        });
    }

    private void handleOnComputeClick() {
        if(startPoint.getText().toString() == "" || locationCount.getText().toString() == "") {
            Toast.makeText(this, "Ausgangspunkt und Anzahl müssen ausgefüllt sein", Toast.LENGTH_SHORT).show();
        } else {
            String startPointAddress = startPoint.getText().toString();
            int numOfLocations = Integer.parseInt(locationCount.getText().toString());
            ArrayList<String> selectedTypes = new ArrayList<>();

            SparseBooleanArray checked = locationTypeLV.getCheckedItemPositions();
            for (int i = 0; i < checked.size(); i++) {
                int key = checked.keyAt(i);
                boolean value = checked.get(key);
                if (value) {
                    Log.d("MainActivity", "Selected = " + types.get(checked.indexOfKey(i)));
                    selectedTypes.add(types.get(checked.indexOfKey(i)));
                }
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
