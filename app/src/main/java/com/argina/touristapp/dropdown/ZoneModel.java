package com.argina.touristapp.dropdown;

public class ZoneModel {

    private int id;
    private String zone_Name;

    public ZoneModel (int id, String zone_Name) {
        this.id = id;
        this.zone_Name = zone_Name;
    }

    public int getId () {
        return id;
    }

    public String getZone_Name () {
        return zone_Name;
    }
}
