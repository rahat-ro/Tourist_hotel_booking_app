package com.argina.touristapp.home;

public class OfferModel {

    private int id;
    private String offerImg,locationTitle,offerMoney,offerTitle,offerDes,zone;

    public OfferModel (int id, String offerImg, String locationTitle, String offerMoney, String offerTitle, String offerDes, String zone) {
        this.id = id;
        this.offerImg = offerImg;
        this.locationTitle = locationTitle;
        this.offerMoney = offerMoney;
        this.offerTitle = offerTitle;
        this.offerDes = offerDes;
        this.zone = zone;
    }

    public int getId () {
        return id;
    }

    public String getOfferImg () {
        return offerImg;
    }

    public String getLocationTitle () {
        return locationTitle;
    }

    public String getOfferMoney () {
        return offerMoney;
    }

    public String getOfferTitle () {
        return offerTitle;
    }

    public String getOfferDes () {
        return offerDes;
    }

    public String getZone () {
        return zone;
    }
}
