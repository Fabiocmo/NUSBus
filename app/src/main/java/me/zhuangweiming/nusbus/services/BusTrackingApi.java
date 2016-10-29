package me.zhuangweiming.nusbus.services;

import java.util.List;

import me.zhuangweiming.nusbus.model.Bus;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Thibault on 16/10/2016.
 */

public interface BusTrackingApi {

    @GET("/api/v1/veniam/location")
    Call<List<Bus>> getBusPositions(@Query("token") String token);

}
