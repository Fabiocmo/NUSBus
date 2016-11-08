package me.zhuangweiming.nusbus.services;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.zhuangweiming.nusbus.model.Bus;
import me.zhuangweiming.nusbus.utils.RestClient;

/**
 * Created by Thibault on 24/10/2016.
 */
@Singleton
public class BusTrackingService implements BusPositionsLoadedCallback {

    @Inject
    protected RestClient restClient;
    @Inject
    protected Context context;

    BusBroadCastReceiver busBroadCastReceiver;

    IntentFilter filter;

    private AtomicBoolean isDownloadScheduled;

    private List<BusPositionsLoadedCallback> observers;

    @Inject
    public BusTrackingService() {
        isDownloadScheduled = new AtomicBoolean(false);
        filter = new IntentFilter("hello");
        busBroadCastReceiver = new BusBroadCastReceiver(this);
        observers = new ArrayList<>();
    }

    public void registerForUpdates(BusPositionsLoadedCallback callback)
    {
        if(observers.contains(callback))
        {
            return;
        }

        observers.add(callback);

        if(!isDownloadScheduled.get())
        {
            context.registerReceiver(busBroadCastReceiver, filter);
            isDownloadScheduled.set(true);
            scheduleAlarm();
        }
    }

    public void unregisterForUpdates(BusPositionsLoadedCallback callback)
    {
        try
        {
            observers.remove(callback);
        }
        catch (Exception e) {}

        if(observers.isEmpty() && isDownloadScheduled.get())
        {
            context.unregisterReceiver(busBroadCastReceiver);
            isDownloadScheduled.set(false);
        }
    }

    public void scheduleAlarm() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(context, BusDownloadIntentService.class);
                context.startService(i);
                if(isDownloadScheduled.get())
                {
                    handler.postDelayed(this, 2000);
                }
            }
        }, 100);
    }

    @Override
    public void onBusPositionsLoaded(List<Bus> busses) {
        for(BusPositionsLoadedCallback callback : observers)
        {
            callback.onBusPositionsLoaded(busses);
        }
    }

    @Override
    public void onBusPositionsLoadingError(Exception exception) {
        for(BusPositionsLoadedCallback callback : observers)
        {
            callback.onBusPositionsLoadingError(exception);
        }
    }
}
