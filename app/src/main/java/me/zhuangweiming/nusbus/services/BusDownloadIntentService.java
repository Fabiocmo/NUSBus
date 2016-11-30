package me.zhuangweiming.nusbus.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import me.zhuangweiming.nusbus.BusApplication;
import me.zhuangweiming.nusbus.model.Bus;
import me.zhuangweiming.nusbus.model.Shuttle;
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
            String token;
            try {
                HashMap<String, Bus> busMap = new HashMap<>();
                result = new ArrayList<>(client.getBusPositions());

                for(Bus bus : result)
                {
                    busMap.put(bus.getVehicleSerial(), bus);
                }

                HashMap<String, Shuttle> shuttles;
                result = new ArrayList<>(busMap.values());
               /* HashMap<String, List<Bus>> candidates = new HashMap<>();

                List<BusStop> stops = dataCache.getBusStops();
                for(BusStop stop : stops)
                {
                    List<Shuttle> sh = client.getShuttles(stop.getName());
                    for(Shuttle s:sh)
                    {
                        if(s.getArrivalTime().compareTo("-")!=0 &&( s.getArrivalTime().compareTo("Arr")==0 || Double.parseDouble(s.getArrivalTime()) <2))
                        {

                            Toast.makeText(this, "bus arriving at "+stop.getName(), Toast.LENGTH_LONG).show();
                            Location loc2 = new Location("");
                            loc2.setLatitude(stop.getLatitude());
                            loc2.setLongitude(stop.getLongitude());

                            for(Bus bus : result)
                            {
                                Location loc1 = new Location("");
                                loc1.setLatitude(bus.getLatitude());
                                loc1.setLongitude(bus.getLongitude());

                                double dist = loc1.distanceTo(loc2);
                                Log.d("distance ", "" +dist);
                                if(dist < 200)
                                {
                                    if(candidates.get(s.getName()) == null)
                                    {
                                        candidates.put(s.getName(), new ArrayList<Bus>());
                                    }
                                    candidates.get(s.getName()).add(bus);


                                    Log.e("bus pos ",  "Bus "+bus.getVehicleSerial()+" is on line "+s.getName()+" at station "+stop.getName());
                                    Toast.makeText(getApplicationContext(), "Bus "+bus.getVehicleSerial()+" is on line "+s.getName()+" at station "+stop.getName(), Toast.LENGTH_LONG).show();
                                }
                            }

                        }

                    }

                }
                Log.e("fin", "finished ");*/


                Intent broadcastIntent = new Intent("hello");
                broadcastIntent.putParcelableArrayListExtra(BUS_LIST_KEY, result);
                sendBroadcast(broadcastIntent);

            } catch (Exception e) {


            }

        }
    }
}
