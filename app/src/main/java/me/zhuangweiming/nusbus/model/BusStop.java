package me.zhuangweiming.nusbus.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by weiming on 15/10/16.
 */
public class BusStop implements Parcelable{
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

    protected BusStop(Parcel in) {
        caption = in.readString();
        name = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<BusStop> CREATOR = new Creator<BusStop>() {
        @Override
        public BusStop createFromParcel(Parcel in) {
            return new BusStop(in);
        }

        @Override
        public BusStop[] newArray(int size) {
            return new BusStop[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(caption);
        parcel.writeString(name);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
    }
}

