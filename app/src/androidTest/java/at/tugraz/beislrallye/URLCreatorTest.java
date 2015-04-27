package at.tugraz.beislrallye;

import com.google.android.gms.maps.model.LatLng;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by Matthias on 27.03.2015.
 */
public class URLCreatorTest extends TestCase {
    public void testCreateDirectionURL() {
        String expected = "http://maps.googleapis.com/maps/api/directions/json?origin=47.0838007,15.4934076&destination=47.1056545,15.6835298&waypoints=optimize:true%7C47.2056545,15.7335298%7C47.2056545,15.7735298&sensor=false&mode=walking";
        LatLng start = new LatLng(47.0838007, 15.4934076);
        LatLng end = new LatLng(47.1056545, 15.6835298);
        LatLng way = new LatLng(47.2056545, 15.7335298);
        LatLng way2 = new LatLng(47.2056545, 15.7735298);

        LatLng[] arr = new LatLng[2];
        arr[0] = way;
        arr[1] = way2;
        String result = URLCreator.createDirectionURL(start, end, arr, true);
        assertEquals(expected, result);

        expected = "http://maps.googleapis.com/maps/api/directions/json?origin=47.0838007,15.4934076&destination=47.1056545,15.6835298&sensor=false&mode=walking";

        ArrayList<LatLng> arrayList = new ArrayList<>();
        arrayList.add(way);
        arrayList.add(way2);
        result = URLCreator.createDirectionURL(arrayList, true);
        assertEquals(expected, result);
    }

    public void testCreateDirectionURLArray() {
        String expected = "http://maps.googleapis.com/maps/api/directions/json?origin=47.0838007,15.4934076&destination=47.1056545,15.6835298&waypoints=optimize:true%7C47.2056545,15.7335298%7C47.2056545,15.7735298&sensor=false&mode=walking";
        LatLng start = new LatLng(47.0838007, 15.4934076);
        LatLng end = new LatLng(47.1056545, 15.6835298);
        LatLng way = new LatLng(47.2056545, 15.7335298);
        LatLng way2 = new LatLng(47.2056545, 15.7735298);

        ArrayList<LatLng> locations = new ArrayList();
        locations.add(start);
        locations.add(way);
        locations.add(way2);
        locations.add(end);

        String result = URLCreator.createDirectionURL(locations, true);
        assertEquals(expected, result);

        locations.remove(1);
        locations.remove(2);
        expected = "http://maps.googleapis.com/maps/api/directions/json?origin=47.0838007,15.4934076&destination=47.1056545,15.6835298&sensor=false&mode=walking";

        result = URLCreator.createDirectionURL(locations, true);
        assertEquals(expected, result);
    }
}
