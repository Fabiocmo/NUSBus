package me.zhuangweiming.nusbus.services;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import me.zhuangweiming.nusbus.utils.RestClient;

/**
 * Created by Thibault on 24/10/2016.
 */
public class BusTrackingService {

    @Inject
    protected RestClient restClient;
    @Inject
    protected Context context;

    private AtomicBoolean isDownloadScheduled;

    private List<BusPositionsLoadedCallback> observers;

    @Inject
    public BusTrackingService() {
        isDownloadScheduled.set(false);
    }

    public void registerForUpdates(BusPositionsLoadedCallback callback)
    {
        observers.add(callback);

        if(!isDownloadScheduled.get())
        {
            isDownloadScheduled.set(true);
            scheduleAlarm();
        }
    }

    public void unregisterForUpdates(BusPositionsLoadedCallback callback)
    {
        observers.remove(callback);

        if(observers.isEmpty())
        {
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
                    handler.postDelayed(this, 5000);
                }
            }
        }, 100);
    }
}
