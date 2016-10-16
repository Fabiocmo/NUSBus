package me.zhuangweiming.nusbus.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Thibault on 16/10/2016.
 */

public class BusStopsResult {

    @SerializedName("busstops")
    List<BusStop> busStops;

    public BusStopsResult(List<BusStop> busStops) {
        this.busStops = busStops;
    }

    public List<BusStop> getBusStops() {
        return busStops;
    }

    public void setBusStops(List<BusStop> busStops) {
        this.busStops = busStops;
    }
}
