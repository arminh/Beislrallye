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
import android.provider.Settings.Secure;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;


public class MainActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private LatLng currentPos = null;

    public static final String API_KEY = "AIzaSyCgW9vZAP_Xc-5gdRMxHfv-vnuECmNccpg";

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

        Button button = (Button)findViewById(R.id.showPlacesButton);

        button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                    }
                }
        );

        LocationManager locationManager = (LocationManager)getSystemService((Context.LOCATION_SERVICE));
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentPos = new LatLng(location.getLatitude(), location.getLongitude());
                Log.i("currentPos", currentPos.toString());
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

    }

    private void checkEnableGPS() {
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Location Services Not Active");
            builder.setMessage("Please enable Location Services and GPS");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Show location settings when the user acknowledges the alert dialog
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            Dialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            }
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkEnableGPS();


        mGoogleApiClient.connect();
        prepareRallye();


    }

    private class LocationControl extends AsyncTask<Context, Void, Void>
    {

        private final ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        protected void onPreExecute()
        {
            this.dialog.setMessage("Determining your location...");
            this.dialog.show();
        }

        protected Void doInBackground(Context... params)
        {
            Long t = Calendar.getInstance().getTimeInMillis();
            while (currentPos == null && Calendar.getInstance().getTimeInMillis() -t < 30000) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            };
            return null;
        }

        protected void onPostExecute(final Void unused)
        {
            if(this.dialog.isShowing())
            {
                this.dialog.dismiss();
            }

            getNearestPlaces(currentPos);
        }

    }


    private void prepareRallye() {

        //getStartingPoint
        LatLng startingPoint = null;


        if(startingPoint == null) {
            if(currentPos == null) {
                LocationControl loc = new LocationControl();
                loc.execute();
            }
            else {
                getNearestPlaces(currentPos);
            }

        } else {
            getNearestPlaces(startingPoint);
        }
    }

    private Vector<Place> getNearestPlaces(LatLng startingPoint) {
        //getTypes
        Vector<String> types = new Vector<String>();
        types.add("bar");

        //getnumPlaces
        int numPlaces = 5;

        makeURL(startingPoint, types);

        return null;
    }

    private void getPlaceDetails(Place place) {
        Intent intent = new Intent(this, PlacesPreviewActivity.class);

       // intent.putExtra("place", place);
        startActivity(intent);
    }

    public String makeURL (LatLng location, Vector<String> types){
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/place/nearbysearch/json\n");
        urlString.append("?location=");
        urlString.append(Double.toString(location.latitude));
        urlString.append(",");
        urlString.append(Double.toString( location.longitude));
        urlString.append("&radius=");
        urlString.append(Double.toString(1000));
        urlString.append("&types=");

        final Iterator itr = types.iterator();
        while(itr.hasNext()) {
            urlString.append(itr.next());
            if(itr.hasNext()) {
                urlString.append('|');
            }
        }
        urlString.append("&key=");
        urlString.append(API_KEY);
        Log.i("URL", urlString.toString());

        return urlString.toString();
    }



    @Override
    protected void onStop() {
        super.onStop();

        mGoogleApiClient.disconnect();
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

    @Override
    public void onConnected(Bundle bundle) {
        Log.i("Connection", "Connected");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Called whenever the API client fails to connect.
        Log.i("Error", "GoogleApiClient connection failed: " + result.toString());
        if (!result.hasResolution()) {
            // show the localized error dialog.
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this, 0).show();
            return;
        }
        // The failure has a resolution. Resolve it.
        // Called typically when the app is not yet authorized, and an
        // authorization
        // dialog is displayed to the user.
        try {
            result.startResolutionForResult(this, 3);
        } catch (IntentSender.SendIntentException e) {
            Log.e("Resolution", "Exception while starting resolution activity", e);
        }
    }
}
