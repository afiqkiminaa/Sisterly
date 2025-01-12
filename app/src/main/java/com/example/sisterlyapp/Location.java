package com.example.sisterlyapp;

import java.io.Serializable;

public class Location implements Serializable {
    private double latitude;
    private double longitude;
    private String address;

    public Location(String latitude, String longitude) {
        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}