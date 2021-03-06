package at.tugraz.beislrallye;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by Matthias on 27.03.2015.
 */
public class URLCreator {
    public static String createDirectionURL (LatLng start, LatLng end, LatLng[] waypoints, boolean optimize){

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

    public static String createNearbyURL(LatLng location, ArrayList<String> types) {
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/place/nearbysearch/json");
        urlString.append("?location=");
        urlString.append(Double.toString(location.latitude));
        urlString.append(",");
        urlString.append(Double.toString(location.longitude));
        urlString.append("&radius=");
        urlString.append("1000");
        urlString.append("&types=");

        final Iterator itr = types.iterator();
        while (itr.hasNext()) {
            urlString.append(itr.next());
            if (itr.hasNext()) {
                urlString.append("%7C");
            }
        }

        urlString.append("&key=");
        urlString.append(Constants.API_KEY);

        return urlString.toString();
    }

    public static String createPhotoURL (String ref, int width, int height) {
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/place/photo?");
        urlString.append("photoreference=");
        urlString.append(ref);
        urlString.append("&maxwidth=");
        urlString.append(Integer.toString(width));
        urlString.append("&key=");
        urlString.append(Constants.API_KEY);

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
