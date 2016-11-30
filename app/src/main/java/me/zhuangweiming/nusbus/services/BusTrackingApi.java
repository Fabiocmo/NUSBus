package me.zhuangweiming.nusbus.services;

import java.util.List;

import me.zhuangweiming.nusbus.model.Bus;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Thibault on 16/10/2016.
 */

public interface BusTrackingApi {

    @GET("/bus_location")
    Call<List<Bus>> getBusPositions();
}
