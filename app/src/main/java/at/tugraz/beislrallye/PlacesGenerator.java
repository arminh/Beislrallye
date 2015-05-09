package at.tugraz.beislrallye;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Matthias on 28.03.2015.
 */
public class PlacesGenerator {
    public static ArrayList<Place> generatePlaces(String placesString) {
        ArrayList<Place> places = new ArrayList<>();
        try {
            Log.d("PlacesGenerato", placesString);
            JSONArray arr = new JSONArray(placesString);
            for(int i = 0; i < arr.length(); i++) {
                JSONObject jsonObj = arr.getJSONObject(i);
                Log.d("PlacesGenerator", jsonObj.toString());
                JSONObject location = jsonObj.getJSONObject("geometry").getJSONObject("location");
                String id = jsonObj.getString("id");
                String name = jsonObj.getString("name");
                String photoId = null;
                if(jsonObj.has("photos")) {
                    JSONArray photos = jsonObj.getJSONArray("photos");
                    JSONObject photosObject = photos.getJSONObject(0);
                    photoId = photosObject.getString("photo_reference");
                }
                String address = null;
                if(jsonObj.has("vicinity")) {
                    address = jsonObj.getString("vicinity");
                }
                double lat = 0;
                double lng = 0;
                if(location.has("lat") && location.has("lng")) {
                    lat = Double.valueOf(location.getString("lat"));
                    lng = Double.valueOf(location.getString("lng"));
                }
                Log.d("PlacesGenerator", "Lat|Lng|Name|Photo " + lat + "|" + lng + "|" + name);
                Place place = new Place(id, lat, lng, name, address, photoId);
                places.add(place);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<Place>();
        }

        return places;
    }
}
