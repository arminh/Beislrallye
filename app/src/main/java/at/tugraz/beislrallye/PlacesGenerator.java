package at.tugraz.beislrallye;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

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
            //JSONObject obj = new JSONObject(placesString);
            JSONArray arr = new JSONArray(placesString);//obj.getJSONArray("places");
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

  //      ArrayList<Place> places = new ArrayList<>();
/*
        Place way1 = new Place(new LatLng(47.071684, 15.436167), "Flann O Brien", "Somewhere in Graz", PlaceType.PUB);
        Place way2 = new Place(new LatLng(47.071576, 15.44027), "Molly Mallone", "Place Street in Graz", PlaceType.PUB);
        Place way3 = new Place(new LatLng(47.066199, 15.441599), "O'riginal Pub", "Some Street in Graz", PlaceType.PUB);
        Place way4 = new Place(new LatLng(47.070829,15.442943), "Office Pub", "Stree Street in Graz", PlaceType.PUB);

        places.add(way1);
        places.add(way2);
        places.add(way3);
        places.add(way4); */

        return places;
    }
}
