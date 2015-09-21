package com.example.mapspractice;

/**
 * Created by Ильнур on 16.09.2015.
 */
public class Place {

    private String mName;
    private double mLongitute;
    private double mLatitude;

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitute() {
        return mLongitute;
    }

    public void setLongitute(double longitute) {
        mLongitute = longitute;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
