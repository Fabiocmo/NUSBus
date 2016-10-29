package me.zhuangweiming.nusbus.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.zhuangweiming.nusbus.model.Bus;
import me.zhuangweiming.nusbus.model.BusStop;
import me.zhuangweiming.nusbus.model.BusStopsResult;
import me.zhuangweiming.nusbus.model.Shuttle;
import me.zhuangweiming.nusbus.model.ShuttleResult;
import me.zhuangweiming.nusbus.services.BusStopApi;
import me.zhuangweiming.nusbus.services.BusTrackingApi;

/**
 * Created by Thibault on 16/10/2016.
 */
@Singleton
public class RestClient {

    private static final String TAG = "RestClient";
    @Inject
    protected BusStopApi busStopApi;
    @Inject
    protected BusTrackingApi busTrackingService;
    @Inject
    protected Context context;
    @Inject
    protected Properties properties;

    @Inject
    public RestClient() {
    }

    public BusStopApi getForecastService() {
        return busStopApi;
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();

    }

    public List<BusStop> getBusStops() throws IOException {
        List<BusStop> result = null;

        try {
            BusStopsResult busStopsResult = this.busStopApi.getBusStops().execute().body().getBusStopsResult();
            result = busStopsResult.getBusStops();
        } catch (Exception e) {
            throw new IOException("Network not available");
        }
        return result;
    }


    public List<Bus> getBusPositions() throws IOException {
        List<Bus> result;

        try {
            result = this.busTrackingService.getBusPositions(properties.getProperty("app.token")).execute().body();
        } catch (Exception e) {
            throw new IOException("Network not available");
        }

        return result;
    }


    public List<Shuttle> getShuttles(String busStopName) throws IOException {
        List<Shuttle> result = null;
        try {
            ShuttleResult shuttleResult = this.busStopApi.getShuttles(busStopName).execute().body().getShuttleResult();
            result = shuttleResult.getShuttles();
        } catch (Exception e) {
            throw new IOException("Network not available");
        }

        return result;
    }
}