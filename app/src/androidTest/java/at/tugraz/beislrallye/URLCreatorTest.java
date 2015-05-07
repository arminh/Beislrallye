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
        String expected = "http://maps.googleapis.com/maps/api/directions/json?origin=47.0838007,15.4934076&destination=47.1056545,15.6835298&waypoints=optimize:false%7C47.2056545,15.7335298%7C47.2056545,15.7735298&sensor=false&mode=walking";
        LatLng start = new LatLng(47.0838007, 15.4934076);
        LatLng end = new LatLng(47.1056545, 15.6835298);
        LatLng way = new LatLng(47.2056545, 15.7335298);
        LatLng way2 = new LatLng(47.2056545, 15.7735298);

        ArrayList<LatLng> locations = new ArrayList();
        locations.add(start);
        locations.add(way);
        locations.add(way2);
        locations.add(end);

        String result = URLCreator.createDirectionURL(locations, false);
        assertEquals(expected, result);

        locations.remove(1);
        locations.remove(2);
        expected = "http://maps.googleapis.com/maps/api/directions/json?origin=47.0838007,15.4934076&destination=47.1056545,15.6835298&sensor=false&mode=walking";

        result = URLCreator.createDirectionURL(locations, true);
        assertEquals(expected, result);
    }

    public void testCreateNearbyURL() {
        String expected = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=47.0838007,15.4934076&radius=1000&types=bar%7Ccafe&key=AIzaSyDOxZCpw4hqUFUNeEgpBBz_THnTJf1IveE";

        LatLng loc = new LatLng(47.0838007, 15.4934076);
        ArrayList<String> types = new ArrayList<String>();
        types.add("bar");
        types.add("cafe");

        String result = URLCreator.createNearbyURL(loc, types);
        assertEquals(expected, result);
    }

    public void testCreatePhotoURL() {
        String expected = "https://maps.googleapis.com/maps/api/place/photo?photoreference=CoQBdwAAALUvaVhmUFU8jHUEYNGwvfcZ_wBQf-KGIjDxP1HrE1v9pJTGuO3YrR5uTqisRWHoWE3YDtMzNgZw8iBWeFFSMSzxolDkE8y2lu7zX8Nm2i5lO2-yfa02e-9fnn-XG130c13Fdq1tEu361m_-Ws4j0np_YIKToQ8BiVt6MvwM0EHwEhAiCZ5qmTt-5rjC0bYDz59qGhTbAsQOnu6b7AFeQx8O0_8tPiNqUw&maxwidth=300&key=AIzaSyDOxZCpw4hqUFUNeEgpBBz_THnTJf1IveE";

        String ref = "CoQBdwAAALUvaVhmUFU8jHUEYNGwvfcZ_wBQf-KGIjDxP1HrE1v9pJTGuO3YrR5uTqisRWHoWE3YDtMzNgZw8iBWeFFSMSzxolDkE8y2lu7zX8Nm2i5lO2-yfa02e-9fnn-XG130c13Fdq1tEu361m_-Ws4j0np_YIKToQ8BiVt6MvwM0EHwEhAiCZ5qmTt-5rjC0bYDz59qGhTbAsQOnu6b7AFeQx8O0_8tPiNqUw";
        String result = URLCreator.createPhotoURL(ref, 300, 300);

        assertEquals(expected, result);
    }
}
