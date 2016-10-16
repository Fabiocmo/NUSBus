package me.zhuangweiming.nusbus.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Thibault on 16/10/2016.
 */

public class BusStopResponse {

    @SerializedName("BusStopsResult")
    BusStopsResult busStopsResult;

    public BusStopResponse(BusStopsResult busStopsResult) {
        this.busStopsResult = busStopsResult;
    }

    public BusStopsResult getBusStopsResult() {
        return busStopsResult;
    }

    public void setBusStopsResult(BusStopsResult busStopsResult) {
        this.busStopsResult = busStopsResult;
    }
}
