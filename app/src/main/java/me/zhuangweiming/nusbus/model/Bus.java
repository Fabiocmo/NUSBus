package me.zhuangweiming.nusbus.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Thibault on 16/10/2016.
 */

public class Bus {

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
}


