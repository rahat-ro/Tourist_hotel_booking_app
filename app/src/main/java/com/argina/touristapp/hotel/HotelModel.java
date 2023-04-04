package com.argina.touristapp.hotel;

public class HotelModel {

    private int id;
    private String imgOne,imgTwo,hotelName,hotelAddress,hotelMapLink,aboutHotel,hotelBDT,hotelPreviousBDT;

    public HotelModel (int id, String imgOne, String imgTwo, String hotelName, String hotelAddress,
                       String hotelMapLink, String aboutHotel, String hotelBDT, String hotelPreviousBDT) {
        this.id = id;
        this.imgOne = imgOne;
        this.imgTwo = imgTwo;
        this.hotelName = hotelName;
        this.hotelAddress = hotelAddress;
        this.hotelMapLink = hotelMapLink;
        this.aboutHotel = aboutHotel;
        this.hotelBDT = hotelBDT;
        this.hotelPreviousBDT = hotelPreviousBDT;
    }

    public int getId () {
        return id;
    }

    public String getImgOne () {
        return imgOne;
    }

    public String getImgTwo () {
        return imgTwo;
    }

    public String getHotelName () {
        return hotelName;
    }

    public String getHotelAddress () {
        return hotelAddress;
    }

    public String getHotelMapLink () {
        return hotelMapLink;
    }

    public String getAboutHotel () {
        return aboutHotel;
    }

    public String getHotelBDT () {
        return hotelBDT;
    }

    public String getHotelPreviousBDT () {
        return hotelPreviousBDT;
    }
}
