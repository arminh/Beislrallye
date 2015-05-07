package at.tugraz.beislrallye;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Matthias on 27.03.2015.
 */
public class URLCreator {
    public static String createDirectionURL (LatLng start, LatLng end, LatLng[] waypoints, boolean optimize){
       // return "http://maps.googleapis.com/maps/api/directions/json?origin=Adelaide,SA&destination=Adelaide,SA&waypoints=optimize:true%7C-34.5333333,138.9500000&sensor=false";

        StringBuilder urlString = new StringBuilder();
        urlString.append("http://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(generateLatLngString(start));
        urlString.append("&destination=");// to
        urlString.append(generateLatLngString(end));
        urlString.append(generateWaypointString(waypoints, optimize));
        urlString.append("&sensor=false&mode=walking");
        return urlString.toString();
    }


    public static String createDirectionURL(ArrayList<LatLng> locations, boolean optimize) {
        LatLng[] a = new LatLng[locations.size() - 2];
        for(int i = 1; i < locations.size() - 1; i++) {
            a[i-1] = locations.get(i);
        }
        return createDirectionURL(locations.get(0), locations.get(locations.size()-1), a, optimize);
    }

    private static String generateWaypointString(LatLng[] waypoints, boolean optimize) {
        String waypointStr = waypoints.length > 0 ? "&waypoints=optimize:" + String.valueOf(optimize) + "%7C" : "";
        for(int i = 0; i < waypoints.length; i++) {
            waypointStr += generateLatLngString(waypoints[i]);
            if(i != waypoints.length - 1)
                waypointStr += "%7C";
        }
        return waypointStr;
    }

    private static String generateLatLngString(LatLng latlng) {
        return Double.toString(latlng.latitude) + "," + Double.toString(latlng.longitude);
    }

    public static String createAutocompleteURL(String apiKey, String countryCode, String input) {
        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place" + "/autocomplete" + "/json");
        sb.append("?key=" + apiKey);
        sb.append("&components=country:" + countryCode);
        sb.append("&input=" + input);

        return sb.toString();
    }
}
