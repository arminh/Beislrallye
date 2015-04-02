package at.tugraz.beislrallye;

import android.graphics.Color;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/**
 * Created by Matthias on 31.03.2015.
 */
public class MapHandler {
    private GoogleMap map;
    private ArrayList<Polyline> lines;
    private ArrayList<Marker> markers;

    public MapHandler(GoogleMap map) {
        this.map = map;
        this.lines = new ArrayList<>();
        this.markers = new ArrayList<>();
    }

    public void addPolyline(LatLng src, LatLng dest, int width, int color, boolean geodesic) {
        Polyline line = map.addPolyline(new PolylineOptions()
                .add(new LatLng(src.latitude, src.longitude), new LatLng(dest.latitude,   dest.longitude))
                .width(2)
                .color(color).geodesic(geodesic));
        lines.add(line);
    }

    public void removeAllPolylines() {
        for(Polyline line : lines) {
            line.remove();
        }
        lines.clear();
    }

    public void addMarker(MarkerOptions marker) {
        markers.add(map.addMarker(marker));
    }

    public void moveCameraToFirstMarker() {
        if(markers.size() > 0) {

            CameraUpdate center = CameraUpdateFactory.newLatLng(markers.get(0).getPosition());
            map.moveCamera(center);
        }
    }

    public void zoomTo(int zoomLevel) {
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(zoomLevel);
        map.animateCamera(zoom);
    }

    public void removeAllMarkers() {
        for(Marker marker : markers) {
            marker.remove();
        }
    }
}
