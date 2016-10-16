package me.zhuangweiming.nusbus.services;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.zhuangweiming.nusbus.utils.RestClient;

/**
 * Created by Thibault on 16/10/2016.
 */
@Singleton
public class BusStopService {

    @Inject
    protected RestClient restClient;
    @Inject
    protected Context context;

    @Inject
    public BusStopService() {

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void loadBusStops(BusStopLoadedCallback callback) {

        LoadBusStopsAsyncTask asyncLoader = new LoadBusStopsAsyncTask(restClient, context, callback);

        Long[] params = {};
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        //here we check the level api
        if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            asyncLoader.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, params);
        } else {
            asyncLoader.execute(params);
        }
    }
}