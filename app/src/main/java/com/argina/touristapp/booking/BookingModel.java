package com.argina.touristapp.booking;

public class BookingModel {

    private int id;
    private String room_name,room_type,final_price,hotel_name,client_mob,checkin_date,checkout_date,room_number,room_status;

    public BookingModel (int id, String room_name, String room_type, String final_price,
                         String hotel_name, String client_mob, String checkin_date, String checkout_date, String room_number, String room_status) {
        this.id = id;
        this.room_name = room_name;
        this.room_type = room_type;
        this.final_price = final_price;
        this.hotel_name = hotel_name;
        this.client_mob = client_mob;
        this.checkin_date = checkin_date;
        this.checkout_date = checkout_date;
        this.room_number = room_number;
        this.room_status = room_status;
    }


    public int getId () {
        return id;
    }

    public String getRoom_name () {
        return room_name;
    }

    public String getRoom_type () {
        return room_type;
    }

    public String getFinal_price () {
        return final_price;
    }

    public String getHotel_name () {
        return hotel_name;
    }

    public String getClient_mob () {
        return client_mob;
    }

    public String getCheckin_date () {
        return checkin_date;
    }

    public String getCheckout_date () {
        return checkout_date;
    }

    public String getRoom_number () {
        return room_number;
    }

    public String getRoom_status () {
        return room_status;
    }
}