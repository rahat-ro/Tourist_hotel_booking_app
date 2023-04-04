package com.argina.touristapp.home;

public class PlaceModel {

    private int id;
    private String placeImg,placeTitle,placeDescription,placeDivision;

    public PlaceModel(int id, String placeImg, String placeTitle, String placeDescription, String placeDivision) {
        this.id = id;
        this.placeImg = placeImg;
        this.placeTitle = placeTitle;
        this.placeDescription = placeDescription;
        this.placeDivision = placeDivision;
    }

    public int getId() {
        return id;
    }

    public String getPlaceImg() {
        return placeImg;
    }

    public String getPlaceTitle() {
        return placeTitle;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public String getPlaceDivision() {
        return placeDivision;
    }
}
