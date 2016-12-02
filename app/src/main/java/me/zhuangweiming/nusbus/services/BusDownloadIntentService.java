package me.zhuangweiming.nusbus.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import me.zhuangweiming.nusbus.BusApplication;
import me.zhuangweiming.nusbus.model.Bus;
import me.zhuangweiming.nusbus.utils.RestClient;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class BusDownloadIntentService extends IntentService {

    public final static String INTENT_TAG = "BROADCAST_BUSSES_POSITIONS";
    public static final String BUS_LIST_KEY = "BUS_LIST" ;

    @Inject
    RestClient client;
    @Inject
    protected DataCache dataCache;

    public BusDownloadIntentService() {
        super("BusDownloadIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startReceivingUpdates(Context context) {
        Intent intent = new Intent(context, BusDownloadIntentService.class);
        intent.setAction("DownLoad");
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            ((BusApplication) getApplication()).getAppComponent().inject(this);
            ArrayList<Bus> result = null;
            try {
                HashMap<String, Bus> busMap = new HashMap<>();
                result = new ArrayList<>(client.getBusPositions());

                for(Bus bus : result) {
                    busMap.put(bus.getVehicleSerial(), bus);
                }

                result = new ArrayList<>(busMap.values());
                Intent broadcastIntent = new Intent("hello");
                broadcastIntent.putParcelableArrayListExtra(BUS_LIST_KEY, result);
                sendBroadcast(broadcastIntent);

            } catch (Exception e) {

            }

        }
    }
}
