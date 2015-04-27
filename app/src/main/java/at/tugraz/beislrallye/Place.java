package at.tugraz.beislrallye;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Matthias on 27.03.2015.
 */
public class Place {
    private final LatLng location;
    private final String name;
    private final String address;
    private final PlaceType type;

    public Place(LatLng start, String name, String address, PlaceType type) {
        this.location = start;
        this.name = name;
        this.address = address;
        this.type = type;
    }

    public LatLng getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public PlaceType getType() {return type;}
}
