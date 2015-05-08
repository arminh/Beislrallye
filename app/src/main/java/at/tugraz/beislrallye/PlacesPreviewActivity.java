package at.tugraz.beislrallye;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class PlacesPreviewActivity extends ActionBarActivity implements OnDownloadImageCompletedListener {

    private static final String LOG_TAG = "PlacesPreviewActivity";
    public static final String API_KEY = "AIzaSyDOxZCpw4hqUFUNeEgpBBz_THnTJf1IveE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_preview);

        Bundle extras = getIntent().getExtras();
        Place place = (Place) getIntent().getSerializableExtra("place");
        if(place != null) {
            Log.d("PlacesPreviewActivity", "Place " + place != null ? place.getName() : "not found");

            previewPlace(place);
        }
    }

    protected void previewPlace(Place place) {

        TextView title = (TextView)findViewById(R.id.placeName);
        title.setText(place.getName());

        TextView address = (TextView)findViewById(R.id.placeAddress);
        address.setText(place.getAddress());

        loadPhoto(place.getPhotoId());
        loadMap(place.getLat(), place.getLng());

    }

    protected void loadPhoto(String id) {
        int width = 300;
        int height = 200;
        String url = URLCreator.createPhotoURL(id, width, height);

        DownloadImageTask connect = new DownloadImageTask(url, this);
        connect.execute();
    }

    protected void loadMap(double lat, double lng) {
        SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.previewMap);
        GoogleMap map = fm.getMap();

        MapHandler mapHandler = new MapHandler(map);
        mapHandler.zoomTo(15);

        MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lng));
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

        mapHandler.addMarker(marker);
        mapHandler.moveCameraToFirstMarker();
    }

    @Override
    public <T> void onTaskCompleted(Bitmap result) {
        ImageView pic = (ImageView)findViewById(R.id.placePic);
        pic.setImageBitmap(result);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_places_preview, menu);
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


}
