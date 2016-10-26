package me.zhuangweiming.nusbus.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by weiming on 26/10/16.
 */

public class ShuttleResponse {
    @SerializedName("ShuttleServiceResult")
    ShuttleResult shuttleResult;

    public ShuttleResponse(ShuttleResult shuttleResult) {
        this.shuttleResult = shuttleResult;
    }

    public ShuttleResult getShuttleResult() {
        return shuttleResult;
    }

    public void setShuttleResult(ShuttleResult shuttleResult) {
        this.shuttleResult = shuttleResult;
    }
}
