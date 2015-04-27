package at.tugraz.beislrallye;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Matthias on 01.04.2015.
 */
public class DirectionsApiJSONParser {
    private String jsonString;

    public DirectionsApiJSONParser(String json) {
        this.jsonString = json;
    }

    public String getPoints() throws JSONException {
        final JSONObject json = new JSONObject(jsonString);
        JSONArray routeArray = json.getJSONArray("routes");
        JSONObject routes = routeArray.getJSONObject(0);
        JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
        return overviewPolylines.getString("points");
    }

    public JSONArray getWaypointOrder() throws JSONException {
        final JSONObject json = new JSONObject(jsonString);
        JSONArray routeArray = json.getJSONArray("routes");
        JSONObject routes = routeArray.getJSONObject(0);
        return routes.getJSONArray("waypoint_order");
    }

    public ArrayList<String> getDistances() throws JSONException{
        ArrayList<String> distances = new ArrayList();
        final JSONObject json = new JSONObject(jsonString);
        JSONArray array = json.getJSONArray("routes");
        JSONObject routes = array.getJSONObject(0);
        JSONArray legs = routes.getJSONArray("legs");
        for(int legCount = 0; legCount < legs.length(); legCount++) {
            JSONObject steps = legs.getJSONObject(legCount);
            JSONObject distance = steps.getJSONObject("distance");
            distances.add(distance.getString("text"));
        }
        return distances;
    }

    public ArrayList<String> getDurations() throws JSONException{
        ArrayList<String> distances = new ArrayList();
        final JSONObject json = new JSONObject(jsonString);
        JSONArray array = json.getJSONArray("routes");
        JSONObject routes = array.getJSONObject(0);
        JSONArray legs = routes.getJSONArray("legs");
        for(int legCount = 0; legCount < legs.length(); legCount++) {
            JSONObject steps = legs.getJSONObject(legCount);
            JSONObject distance = steps.getJSONObject("duration");
            distances.add(distance.getString("text"));
        }
        return distances;
    }
}
