package at.tugraz.beislrallye;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by Matthias on 27.03.2015.
 */
public class Place implements Serializable {
    //private LatLng location;
    private double lat;
    private double lng;
    private String name;
    private String address;
    //private PlaceType type;
    private String id;
    private String photoId;
/*
    public Place(String id, LatLng start, String name, String address, PlaceType type,  String photoId) {
        this.location = start;
        this.name = name;
        this.address = address;
       // this.type = type;
        this.id = id;
        this.photoId = photoId;
    } */


    public Place(String id, double lat, double lng, String name, String address, String photoId) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.address = address;
        this.photoId = photoId;
    }

    public Place() {}

    public double getLng() {
        return lng;
    }public void setLng(double lng) {
        this.lng = lng;
    }public double getLat() {
        return lat;
    }public void setLat(double lat) {
        this.lat = lat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }


    /*
    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {this.location = location;} */

    public void setName(String name) {this.name = name;}

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
/*
    public PlaceType getType() {return type;}

    public void setType(PlaceType type) {this.type = type;}; */
}
