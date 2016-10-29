package me.zhuangweiming.nusbus.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Thibault on 16/10/2016.
 */

public class Bus implements Parcelable{

    @SerializedName("node_id")
    long nodeId;

    @SerializedName("vehicle_serial")
    String vehicleSerial;

    @SerializedName("gps_time")
    Date gpsTime;

    double latitude;
    double longitude;
    double altitude;
    double speed;
    double heading;

    public Bus(long nodeId, String vehicleSerial, Date gpsTime, double latitude, double longitude, double altitude, double speed, double heading) {
        this.nodeId = nodeId;
        this.vehicleSerial = vehicleSerial;
        this.gpsTime = gpsTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.speed = speed;
        this.heading = heading;
    }

    protected Bus(Parcel in) {
        nodeId = in.readLong();
        vehicleSerial = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        altitude = in.readDouble();
        speed = in.readDouble();
        heading = in.readDouble();
    }

    public static final Creator<Bus> CREATOR = new Creator<Bus>() {
        @Override
        public Bus createFromParcel(Parcel in) {
            return new Bus(in);
        }

        @Override
        public Bus[] newArray(int size) {
            return new Bus[size];
        }
    };

    public long getNodeId() {
        return nodeId;
    }

    public void setNodeId(long nodeId) {
        this.nodeId = nodeId;
    }

    public String getVehicleSerial() {
        return vehicleSerial;
    }

    public void setVehicleSerial(String vehicleSerial) {
        this.vehicleSerial = vehicleSerial;
    }

    public Date getGpsTime() {
        return gpsTime;
    }

    public void setGpsTime(Date gpsTime) {
        this.gpsTime = gpsTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(nodeId);
        parcel.writeString(vehicleSerial);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeDouble(altitude);
        parcel.writeDouble(speed);
        parcel.writeDouble(heading);
    }
}


