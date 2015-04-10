package at.tugraz.beislrallye;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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
    private LatLng currentLocation = null;//new LatLng(47.0672434,15.4425523);
    private LatLng finalLocation = new LatLng(47.0672434,15.4425523);
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


        placesList = (DynamicListView) findViewById(R.id.places_lv);

        SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        map = fm.getMap();
        mapHandler = new MapHandler(map);

        places = DummyPlacesGenerator.generatePlaces();

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
        createLocationsRequest();
        //queryDirectionsApi(true);
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
        findViewById(R.id.beisl_detail).setVisibility(View.VISIBLE);
        Place place = markerToPlaceMap.get(marker.getPosition());
        if(place != null) {
            ((TextView) findViewById(R.id.title_text)).setText(place.getName());
            ((TextView) findViewById(R.id.address_text)).setText(place.getAddress());
        } else {
            Log.d(LOG_TAG, "Place == null " + marker.hashCode());
        }
    }

    private void queryDirectionsApi(boolean optimize) {
        ArrayList<LatLng> locations = new ArrayList<>();
        if(currentLocation != null) {
            locations.add(currentLocation);
            for (Place place : places) {
                locations.add(place.getLocation());
            }
            locations.add(finalLocation);
            String url = URLCreator.createDirectionURL(locations, optimize);
            WebConnectionTask connect = new WebConnectionTask(url, this);
            connect.execute();
        }
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
            List<LatLng> polygoneLines = PolyUtil.decode(encodedString);//decodePoly(encodedString);

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
        //for(Place place : places) {
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
            MarkerOptions placeMarker = new MarkerOptions().position(place.getLocation());
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
        places = (ArrayList<Place>) content;//((StableArrayAdapter)placesList.getAdapter()).getItems();
        queryDirectionsApi(false);
    }

    private void updatePlacesArray(int[] waypointsIndex) {
        List<Place> waypoints = places;//.subList(0, places.size()-1);
        List<Place> waypointsSorted = new ArrayList<Place>();
        if(waypointsIndex.length != waypoints.size()){
            Log.e(LOG_TAG, "Update Places: wrong indices " + waypointsIndex.length + " | " + waypoints.size());
        } else {
            for(int i = 0; i < waypointsIndex.length; i++) {
                waypointsSorted.add(waypoints.get(waypointsIndex[i]));
            }
            ArrayList<Place> sortedPlaces = new ArrayList<>();
            //sortedPlaces.add(places.get(0));
            sortedPlaces.addAll(waypointsSorted);
            //sortedPlaces.add(places.get(places.size()-1));
            places = sortedPlaces;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_maps, menu);
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

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("MapsActivity", "onConnected");
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, this);

        Toast.makeText(this, "onConnected", Toast.LENGTH_SHORT).show();
        Log.d("MapsActivity", "onConnected - end");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("MapsActivity", "onLocationChanged");
        if(location != null) {
            if(currentLocation == null) {
                Toast.makeText(this, "OnLocationChanged", Toast.LENGTH_SHORT).show();
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

/*package at.tugraz.beislrallye;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MapsActivity extends ActionBarActivity implements OnWebConnectionTaskCompletedListener, OnListViewReorderListener {
    private static final String LOG_TAG = "MapsActivity";
    private GoogleMap map;
    private HashMap<LatLng, Place> markerToPlaceMap = new HashMap<>();
    private ArrayList<Place> places = new ArrayList<>();
    private DynamicListView placesList;
    private MapHandler mapHandler;
    private LatLng currentLocation = new LatLng(47.0672434,15.4425523);
    private LatLng finalLocation = new LatLng(47.0672434,15.4425523);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        placesList = (DynamicListView) findViewById(R.id.places_lv);

        SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        map = fm.getMap();
        mapHandler = new MapHandler(map);

        places = DummyPlacesGenerator.generatePlaces();

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

        findViewById(R.id.load_map_overlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        queryDirectionsApi(true);
    }

    private void showBeislDetails(Marker marker) {
        findViewById(R.id.beisl_detail).setVisibility(View.VISIBLE);
        Place place = markerToPlaceMap.get(marker.getPosition());
        if(place != null) {
            ((TextView) findViewById(R.id.title_text)).setText(place.getName());
            ((TextView) findViewById(R.id.address_text)).setText(place.getAddress());
        } else {
            Log.d(LOG_TAG, "Place == null " + marker.hashCode());
        }
    }

    private void queryDirectionsApi(boolean optimize) {
        ArrayList<LatLng> locations = new ArrayList<>();
        locations.add(currentLocation);
        for(Place place : places) {
            locations.add(place.getLocation());
        }
        locations.add(finalLocation);
        String url = URLCreator.createDirectionURL(locations, optimize);
        WebConnectionTask connect = new WebConnectionTask(url, this);
        connect.execute();
    }

    @Override
    public <T> void onTaskCompleted(String result) {
        if(result == null)
            return;
        try {
            DirectionsApiJSONParser parser = new DirectionsApiJSONParser(result);
            String encodedString = parser.getPoints();
            List<LatLng> polygoneLines = PolyUtil.decode(encodedString);//decodePoly(encodedString);

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
        //for(Place place : places) {
        mapHandler.removeAllMarkers();

        MarkerOptions markerCurrent = new MarkerOptions().position(currentLocation);
        markerCurrent.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        mapHandler.addMarker(markerCurrent);

        MarkerOptions markerFinal = new MarkerOptions().position(finalLocation);
        markerFinal.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        mapHandler.addMarker(markerFinal);

        for(int i = 0; i < places.size(); i++) {
            Place place = places.get(i);
            IconGenerator tc = new IconGenerator(this);
            tc.setStyle(IconGenerator.STYLE_RED);
            Bitmap bmp = tc.makeIcon("" + (i + 1));
            MarkerOptions placeMarker = new MarkerOptions().position(place.getLocation());
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
    public <T> void onListViewReorder(List<T> content) {
        findViewById(R.id.load_map_overlay).setVisibility(View.VISIBLE);
        places = (ArrayList<Place>) content;//((StableArrayAdapter)placesList.getAdapter()).getItems();
        queryDirectionsApi(false);
    }

    private void updatePlacesArray(int[] waypointsIndex) {
        List<Place> waypoints = places;//.subList(0, places.size()-1);
        List<Place> waypointsSorted = new ArrayList<Place>();
        if(waypointsIndex.length != waypoints.size()){
            Log.e(LOG_TAG, "Update Places: wrong indices " + waypointsIndex.length + " | " + waypoints.size());
        } else {
            for(int i = 0; i < waypointsIndex.length; i++) {
                waypointsSorted.add(waypoints.get(waypointsIndex[i]));
            }
            ArrayList<Place> sortedPlaces = new ArrayList<>();
            //sortedPlaces.add(places.get(0));
            sortedPlaces.addAll(waypointsSorted);
            //sortedPlaces.add(places.get(places.size()-1));
            places = sortedPlaces;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_maps, menu);
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
} */
