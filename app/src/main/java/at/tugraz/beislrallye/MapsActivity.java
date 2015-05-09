package at.tugraz.beislrallye;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.ui.IconGenerator;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class MapsActivity extends ActionBarActivity implements OnWebConnectionTaskCompletedListener, OnListViewReorderListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final String LOG_TAG = "MapsActivity";
    private GoogleMap map;
    private HashMap<LatLng, Place> markerToPlaceMap = new HashMap<>();
    private ArrayList<Place> places = new ArrayList<>();
    private DynamicListView placesList;
    private MapHandler mapHandler;
    private LatLng currentLocation = null;
    private LatLng finalLocation = new LatLng(47.0672434,15.4425523);
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getSupportActionBar().hide();

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        Date now = new Date();
        RalleyStatisticsManager.getInstance().setStartTime(now);

        placesList = (DynamicListView) findViewById(R.id.places_lv);

        SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        map = fm.getMap();
        mapHandler = new MapHandler(map);

        String placesString = getIntent().getExtras().getString("places");
        if(placesString != null && placesString != "") {
            places = PlacesGenerator.generatePlaces(placesString);

            mapHandler.zoomTo(15);
            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    showBeislDetails(marker);
                    return false;
                }
            });
            map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    findViewById(R.id.beisl_detail).setVisibility(View.INVISIBLE);
                }
            });

            findViewById(R.id.retry_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleOnRetryClick();
                }
            });

            findViewById(R.id.load_map_overlay).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            findViewById(R.id.finalize_ralley_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleOnFinalizeClick();
                }
            });
            createLocationsRequest();
        }
    }

    private void handleOnFinalizeClick() {
        RalleyStatisticsManager.getInstance().setEndTime(new Date());
        Intent intent = new Intent(MapsActivity.this, SummaryActivity.class);
        this.startActivity(intent);
    }

    private void handleOnRetryClick() {
        findViewById(R.id.error_container).setVisibility(View.GONE);
        queryDirectionsApi(true);
    }

    private void createLocationsRequest() {
        Log.d(LOG_TAG, "createLocationsRequest");
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        Log.d(LOG_TAG, "createLocationsRequest - end");
    }

    private void showBeislDetails(Marker marker) {
        if(markerToPlaceMap.containsKey(marker.getPosition())) {
            Place place = markerToPlaceMap.get(marker.getPosition());
            Intent intent = new Intent(this, PlacesPreviewActivity.class);
            intent.putExtra("place", place);
            startActivity(intent);
        }
    }

    private void queryDirectionsApi(boolean optimize) {
        ArrayList<LatLng> locations = new ArrayList<>();
        if(currentLocation != null) {
            locations.add(currentLocation);
            for (Place place : places) {
                locations.add(new LatLng(place.getLat(), place.getLng()));
            }
            locations.add(finalLocation);
            String url = URLCreator.createDirectionURL(locations, optimize);
            WebConnectionTask connect = new WebConnectionTask(url, this);
            connect.execute();
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.want_quit))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MapsActivity.this.finish();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), null)
                .show();
    }

    @Override
    public <T> void onTaskCompleted(String result) {
        if(result == null) {
            findViewById(R.id.error_container).setVisibility(View.VISIBLE);
            return;
        }
        try {
            DirectionsApiJSONParser parser = new DirectionsApiJSONParser(result);
            String encodedString = parser.getPoints();
            List<LatLng> polygoneLines = PolyUtil.decode(encodedString);

            JSONArray wp = parser.getWaypointOrder();
            Log.d("MapsActivity", "Waypoints = " + wp.toString());
            if(wp != null) {
                int[] waypoints = new int[wp.length()];
                for (int i = 0; i < wp.length(); ++i) {
                    waypoints[i] = wp.optInt(i);
                }
                updatePlacesArray(waypoints);
                setPlacesMarker();
            }

            mapHandler.removeAllPolylines();
            for(int z = 0; z < polygoneLines.size()-1; z++){
                LatLng src= polygoneLines.get(z);
                LatLng dest= polygoneLines.get(z+1);
                mapHandler.addPolyline(src, dest, 2, Color.BLUE, true);
            }

            createListView(parser.getDistances(), parser.getDurations());
            findViewById(R.id.load_map_overlay).setVisibility(View.GONE);

        }
        catch (JSONException e) {
            Log.e("MapsActivity", "Error in onTaskCompleted" + e.toString());
        }
    }

    private void setPlacesMarker() {
        mapHandler.removeAllMarkers();

        if (currentLocation != null) {
            MarkerOptions markerCurrent = new MarkerOptions().position(currentLocation);
            markerCurrent.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            mapHandler.addCurrentLocationMarker(markerCurrent);
        }

        MarkerOptions markerFinal = new MarkerOptions().position(finalLocation);
        markerFinal.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mapHandler.addMarker(markerFinal);

        for(int i = 0; i < places.size(); i++) {
            Place place = places.get(i);
            IconGenerator tc = new IconGenerator(this);
            tc.setStyle(IconGenerator.STYLE_RED);
            Bitmap bmp = tc.makeIcon("" + (i + 1));
            MarkerOptions placeMarker = new MarkerOptions().position(new LatLng(place.getLat(), place.getLng()));
            placeMarker.icon(BitmapDescriptorFactory.fromBitmap(bmp));
            markerToPlaceMap.put(placeMarker.getPosition(), place);
            mapHandler.addMarker(placeMarker);
        }

        mapHandler.moveCameraToFirstMarker();
    }

    private void createListView(ArrayList<String> distances, ArrayList<String> durations) {
        placesList.setAdapter(new StableArrayAdapter(this, R.layout.places_listview_item, places, distances, durations));
        placesList.setOnListViewReorderListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    public <T> void onListViewReorder(List<T> content) {
        findViewById(R.id.load_map_overlay).setVisibility(View.VISIBLE);
        places = (ArrayList<Place>) content;
        queryDirectionsApi(false);
    }

    private void updatePlacesArray(int[] waypointsIndex) {
        List<Place> waypoints = places;
        List<Place> waypointsSorted = new ArrayList<Place>();
        if(waypointsIndex.length != waypoints.size()){
            Log.e(LOG_TAG, "Update Places: wrong indices " + waypointsIndex.length + " | " + waypoints.size());
        } else {
            for(int i = 0; i < waypointsIndex.length; i++) {
                waypointsSorted.add(waypoints.get(waypointsIndex[i]));
            }
            ArrayList<Place> sortedPlaces = new ArrayList<>();
            sortedPlaces.addAll(waypointsSorted);
            places = sortedPlaces;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_maps, menu);
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
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, this);
        Log.d("MapsActivity", "onConnected");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("MapsActivity", "onLocationChanged");
        if(location != null) {
            if(currentLocation == null) {
                currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                queryDirectionsApi(true);
            } else {
                currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
            }
            MarkerOptions markerCurrent = new MarkerOptions().position(currentLocation);
            markerCurrent.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            mapHandler.updateCurrentLocationMarker(currentLocation);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "onConnectionFailed", Toast.LENGTH_SHORT).show();
        Log.d("MapsActivity", "onConnectionFailed");
    }
}