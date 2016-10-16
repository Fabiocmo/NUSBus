package me.zhuangweiming.nusbus.services;

import me.zhuangweiming.nusbus.model.BusStopResponse;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Thibault on 16/10/2016.
 */
public interface BusStopApi {

    @GET("/eventservice.svc/BusStops")
    Call<BusStopResponse> getBusStops();
}
