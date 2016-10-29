package me.zhuangweiming.nusbus.services;

import java.util.List;

import me.zhuangweiming.nusbus.model.Bus;

/**
 * Created by Thibault on 24/10/2016.
 */

public interface BusPositionsLoadedCallback {
    void onBusPositionsLoaded(List<Bus> busses);
    void onBusPositionsLoadingError(Exception exception);
}
