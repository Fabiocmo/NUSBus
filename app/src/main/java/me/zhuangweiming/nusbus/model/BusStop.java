package me.zhuangweiming.nusbus.model;

/**
 * Created by weiming on 15/10/16.
 */
public class BusStop {
    String caption;
    String name;
    double latitude;
    double longitude;

    public BusStop (String caption, String name, double latitude, double longtitude) {
        this.caption = caption;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longtitude;
    }

    public String getCaption() {
        return caption;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}

