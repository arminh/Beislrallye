package at.tugraz.beislrallye;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnWebConnectionTaskCompletedListener, AdapterView.OnItemClickListener {

    private static final String LOG_TAG = "MainActivity";
    private ListView locationTypeLV;
    private EditText locationCount;
    private AutoCompleteTextView startPoint;
    private GoogleApiClient mGoogleApiClient;
    private LatLng currentPos = null;


    private List<String> types;

    private ArrayList<String> selectedTypes;
    private int numOfLocations;
    private LatLng startPointAddress = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        startPoint = (AutoCompleteTextView) findViewById(R.id.start_point);
        startPoint.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.list_item));
        startPoint.setOnItemClickListener(this);
        locationCount = (EditText) findViewById(R.id.location_count);
        locationTypeLV = (ListView) findViewById(R.id.location_type_lv);

        ArrayAdapter<String> adapter;

        types = new ArrayList<>();
        types.add("Bar");
        types.add("Cafe");
        types.add("Nightclub");
        types.add("Restaurant");

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        final LocationManager locationManager = (LocationManager) getSystemService((Context.LOCATION_SERVICE));
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentPos = new LatLng(location.getLatitude(), location.getLongitude());
                locationManager.removeUpdates(this);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 500.0f, listener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000L, 500.0f, listener);

        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_multiple_choice,
                types);
        locationTypeLV.setAdapter(adapter);
        locationTypeLV.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        findViewById(R.id.compute_ralley_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOnComputeClick();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        reset();
    }

    private void reset() {
        findViewById(R.id.compute_ralley_button).setEnabled(true);
        ((EditText) findViewById(R.id.location_count)).setText("");
    }

    public void onItemClick(AdapterView adapterView, View view, int position, long id) {
        AutocompleteData data = ((GooglePlacesAutocompleteAdapter)adapterView.getAdapter()).getAutoCompleteData(position);
        Toast.makeText(this, data.getDescription(), Toast.LENGTH_SHORT).show();
        
    }


    public ListView getListView() {
        return locationTypeLV;
    }
    @Override
    protected void onStart() {
        super.onStart();
        checkEnableGPS();
        mGoogleApiClient.connect();
    }

    private void checkEnableGPS() {
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.location_not_active));
            builder.setMessage(getResources().getString(R.string.enable_location));
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            Dialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }
    }

    private void handleOnComputeClick() {
        if (locationCount.getText().toString().matches("")) {
            Toast.makeText(this, getResources().getString(R.string.error_amount), Toast.LENGTH_SHORT).show();
        } else {
            numOfLocations = Integer.parseInt(locationCount.getText().toString());
            selectedTypes = new ArrayList<>();

            SparseBooleanArray checked = locationTypeLV.getCheckedItemPositions();
            if(checked.size() <= 0) {
                Toast.makeText(this, getResources().getString(R.string.error_type), Toast.LENGTH_SHORT).show();
            } else {
                for (int i = 0; i < checked.size(); i++) {
                    int key = checked.keyAt(i);
                    boolean value = checked.get(key);
                    if (value) {
                        int indexOfKey = checked.indexOfKey(i);
                        if(indexOfKey >= 0) {
                            Log.d("MainActivity", "Selected = " + types.get(checked.indexOfKey(i)));
                            selectedTypes.add(types.get(checked.indexOfKey(i)).toLowerCase());
                        }
                    }
                }
                ((Button)findViewById(R.id.compute_ralley_button)).setEnabled(false);
                prepareRallye();
            }
        }
    }

    private void prepareRallye() {
        if (startPointAddress == null) {
            if (currentPos == null) {
                LocationControl loc = new LocationControl();
                loc.execute();
            } else {
                getNearestPlaces(currentPos);
            }
        } else {
            getNearestPlaces(startPointAddress);
        }
    }

    private class LocationControl extends AsyncTask<Context, Void, Void> {

        private final ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        protected void onPreExecute() {
            this.dialog.setMessage("Determining your location...");
            this.dialog.show();
        }

        protected Void doInBackground(Context... params) {
            Long t = Calendar.getInstance().getTimeInMillis();
            while (currentPos == null && Calendar.getInstance().getTimeInMillis() - t < 30000) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            ;
            return null;
        }

        protected void onPostExecute(final Void unused) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            getNearestPlaces(currentPos);
        }
    }

    private void getNearestPlaces(LatLng startingPoint) {
        if (startPointAddress == null)
            startPointAddress = new LatLng(47.0693154, 15.4422045);
        String url = URLCreator.createNearbyURL(startPointAddress, selectedTypes);
        WebConnectionTask connect = new WebConnectionTask(url, this);
        connect.execute();
    }

    @Override
    public <T> void onTaskCompleted(String result) {
        try {
            JSONArray places = new JSONArray();

            final JSONObject json = new JSONObject(result);
            JSONArray results = json.getJSONArray("results");
            if (results.length() >= numOfLocations) {
                for (int i = 0; i < numOfLocations; i++) {
                    places.put(results.getJSONObject(i));
                }
            }
            String placesString = places.toString();

            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra("places", placesString);
            startActivity(intent);

        } catch (JSONException e) {
            Log.e("MapsActivity", "Error in onTaskCompleted" + e.toString());
            ((Button)findViewById(R.id.compute_ralley_button)).setEnabled(true);
        }


    }

    @Override
    protected void onStop() {
        super.onStop();

        mGoogleApiClient.disconnect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(LOG_TAG, "Connected");
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(LOG_TAG, "GoogleApiClient connection failed: " + result.toString());
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this, 0).show();
            return;
        }
        try {
            result.startResolutionForResult(this, 3);
        } catch (IntentSender.SendIntentException e) {
            Log.e(LOG_TAG, "Exception while starting resolution activity", e);
        }
    }

    public static ArrayList autocomplete(String input) {
        ArrayList<AutocompleteData> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            String urlStr = URLCreator.createAutocompleteURL(Constants.API_KEY, "at", URLEncoder.encode(input, "utf8"));
            Log.d(LOG_TAG, "Autocomplete URL = " + urlStr);
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");
            resultList = new ArrayList(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                resultList.add(new AutocompleteData(predsJsonArray.getJSONObject(i).getString("description"), predsJsonArray.getJSONObject(i).getString("place_id")));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }

        return resultList;
    }

    class GooglePlacesAutocompleteAdapter extends ArrayAdapter implements Filterable {
        private ArrayList<AutocompleteData> resultList;
        private ArrayList<String> stringResults;

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return stringResults.get(index);
        }

        public AutocompleteData getAutoCompleteData(int index) {return resultList.get(index); }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        resultList = autocomplete(constraint.toString());

                        if(resultList != null) {
                            stringResults = new ArrayList<>(resultList.size());
                            for(AutocompleteData data : resultList)
                                stringResults.add(data.getDescription());
                            filterResults.values = stringResults;
                            filterResults.count = resultList.size();
                        }
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }
}
