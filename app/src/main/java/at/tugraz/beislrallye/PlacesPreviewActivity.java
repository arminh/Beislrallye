package at.tugraz.beislrallye;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PlacesPreviewActivity extends ActionBarActivity implements OnDownloadImageCompletedListener {

    private static final String LOG_TAG = "PlacesPreviewActivity";
    public static final String API_KEY = "AIzaSyDOxZCpw4hqUFUNeEgpBBz_THnTJf1IveE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_preview);

        Bundle extras = getIntent().getExtras();

        String placeStr;
        if(extras == null) {
            placeStr= null;
        } else {
            placeStr= extras.getString("place");
            try {
                JSONObject jPlace = new JSONObject(placeStr);
                parsePlace(jPlace);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    protected void parsePlace(JSONObject place) {

        TextView title = (TextView)findViewById(R.id.placeName);

        try {
            String name = place.getString("name");
            title.setText(name);

            JSONArray photos = place.getJSONArray("photos");
            JSONObject photo = photos.getJSONObject(0);
            loadPhoto(photo);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void loadPhoto(JSONObject photo) {
        try {
            String ref = photo.getString("photo_reference");
            int width = 300;
            int height = 200;
            String url = makeURL(ref, width, height);

            DownloadImageTask connect = new DownloadImageTask(url, this);
            connect.execute();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public <T> void onTaskCompleted(Bitmap result) {
        ImageView pic = (ImageView)findViewById(R.id.placePic);
        pic.setImageBitmap(result);
    }

    public String makeURL (String ref, int width, int height) {
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/place/photo?");
        urlString.append("photoreference=");
        urlString.append(ref);
        urlString.append("&maxwidth=");
        urlString.append(Integer.toString(width));
        urlString.append("&key=");
        urlString.append(API_KEY);
        Log.i(LOG_TAG, urlString.toString());

        return urlString.toString();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_places_preview, menu);
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
