package me.zhuangweiming.nusbus.services;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.zhuangweiming.nusbus.utils.RestClient;

/**
 * Created by weiming on 26/10/16.
 */

@Singleton
public class ShuttleService {
    @Inject
    protected RestClient client;

    @Inject
    protected Context context;

    @Inject
    public ShuttleService() {

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void loadShuttles(LoadShuttleCallback loadShuttleCallback, String busStopName) {
        LoadShuttlesAsyncTask asyncLoader = new LoadShuttlesAsyncTask(client, context, loadShuttleCallback);

        String [] params = {busStopName};
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        //here we check the level api
        if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            asyncLoader.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, params);
        } else {
            asyncLoader.execute(params);
        }
    }

}
