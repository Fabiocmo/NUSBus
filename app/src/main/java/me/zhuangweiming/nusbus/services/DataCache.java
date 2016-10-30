package me.zhuangweiming.nusbus.services;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.zhuangweiming.nusbus.model.BusStop;
import me.zhuangweiming.nusbus.model.Shuttle;

/**
 * Created by Thibault on 30/10/2016.
 */
@Singleton
public class DataCache {

    List<BusStop> bustops;
    HashMap<String, List<Shuttle>> shuttles;

    @Inject
    public DataCache()
    {

    }

    public void storeBusStops(List<BusStop> stops)
    {
        synchronized(this)
        {
            bustops = stops;
        }
    }

    public List<BusStop> getBusStops()
    {
        synchronized(this)
        {
           return bustops;
        }
    }


}
