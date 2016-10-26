package me.zhuangweiming.nusbus.services;

import java.util.List;

import me.zhuangweiming.nusbus.model.Shuttle;

/**
 * Created by weiming on 26/10/16.
 */

public interface LoadShuttleCallback {
    void onShuttleLoaded(List<Shuttle> shuttles);
    void onShuttleLoadingError(Exception e);
}
