package me.zhuangweiming.nusbus.services;

import java.util.List;

import me.zhuangweiming.nusbus.model.BusStop;

/**
 * Created by Thibault on 16/10/2016.
 */
public interface BusStopLoadedCallback {

    void onBusStopsLoaded(List<BusStop> busStops);
    void onBusStopLoadingError(Exception exception);
}
