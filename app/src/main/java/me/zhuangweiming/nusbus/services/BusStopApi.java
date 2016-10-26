package me.zhuangweiming.nusbus.services;

import me.zhuangweiming.nusbus.model.BusStopResponse;
import me.zhuangweiming.nusbus.model.ShuttleResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Thibault on 16/10/2016.
 */
public interface BusStopApi {

    @GET("/eventservice.svc/BusStops")
    Call<BusStopResponse> getBusStops();

    @GET("/eventservice.svc/Shuttleservice")
    Call<ShuttleResponse> getShuttles(@Query("busstopname") String busStopName);

}
