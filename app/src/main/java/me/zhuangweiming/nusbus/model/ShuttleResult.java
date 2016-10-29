package me.zhuangweiming.nusbus.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by weiming on 26/10/16.
 */

public class ShuttleResult {
    @SerializedName("shuttles")
    List<Shuttle> shuttles;

    public ShuttleResult(List<Shuttle> shuttles) {
        this.shuttles = shuttles;
    }

    public List<Shuttle> getShuttles() {
        return shuttles;
    }

    public void setShuttles(List<Shuttle> shuttles) {
        this.shuttles = shuttles;
    }
}
