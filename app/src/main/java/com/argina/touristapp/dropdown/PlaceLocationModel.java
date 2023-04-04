package com.argina.touristapp.dropdown;

public class PlaceLocationModel {

    private int id;
    private String placeLocationName;

    public PlaceLocationModel (int id, String placeLocationName) {
        this.id = id;
        this.placeLocationName = placeLocationName;
    }

    public int getId () {
        return id;
    }

    public String getPlaceLocationName () {
        return placeLocationName;
    }
}
