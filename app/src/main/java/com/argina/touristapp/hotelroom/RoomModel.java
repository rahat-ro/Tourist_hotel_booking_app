package com.argina.touristapp.hotelroom;

public class RoomModel {

    private int id;
    private String roomImg,roomName,roomType,BDT,price,discount,finalPrice,hotelName;

    public RoomModel (int id, String roomImg, String roomName, String roomType, String BDT,
                      String price, String discount, String finalPrice, String hotelName) {
        this.id = id;
        this.roomImg = roomImg;
        this.roomName = roomName;
        this.roomType = roomType;
        this.BDT = BDT;
        this.price = price;
        this.discount = discount;
        this.finalPrice = finalPrice;
        this.hotelName = hotelName;
    }

    public int getId () {
        return id;
    }

    public String getRoomImg () {
        return roomImg;
    }

    public String getRoomName () {
        return roomName;
    }

    public String getRoomType () {
        return roomType;
    }

    public String getBDT () {
        return BDT;
    }

    public String getPrice () {
        return price;
    }

    public String getDiscount () {
        return discount;
    }

    public String getFinalPrice () {
        return finalPrice;
    }

    public String getHotelName () {
        return hotelName;
    }
}
