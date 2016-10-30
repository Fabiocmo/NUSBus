package me.zhuangweiming.nusbus.view;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;

import me.zhuangweiming.nusbus.BusApplication;
import me.zhuangweiming.nusbus.R;
import me.zhuangweiming.nusbus.model.Bus;
import me.zhuangweiming.nusbus.services.BusBroadCastReceiver;
import me.zhuangweiming.nusbus.services.BusDownloadIntentService;
import me.zhuangweiming.nusbus.services.BusPositionsLoadedCallback;
import me.zhuangweiming.nusbus.utils.LatLngInterpolator;
import me.zhuangweiming.nusbus.utils.MarkerAnimation;

public class MapVisualizationActivity extends AppCompatActivity implements OnMapReadyCallback, BusPositionsLoadedCallback {

    private GoogleMap mMap;
    private HashMap<String, Bus> bussPositions = new HashMap<>();
    private HashMap<String, Marker> markers = new HashMap<>();
    private final static int REFRESHING_DELAY = 2000;

    private BusBroadCastReceiver busBroadcastReceiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_visualization);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ((BusApplication) getApplication()).getAppComponent().inject(this);

        busBroadcastReceiver = new BusBroadCastReceiver(this);
        intentFilter = new IntentFilter("hello");

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(busBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(busBroadcastReceiver);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng nus = new LatLng(1.290665504, 103.772663576);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nus, 17));
        scheduleAlarm();
    }

    @Override
    public void onBusPositionsLoaded(List<Bus> busses) {

        for(Bus bus : busses)
        {
            bussPositions.put(bus.getVehicleSerial(), bus);
            Marker marker = markers.get(bus.getVehicleSerial());

            if(marker == null)
            {
                LatLng pos = new LatLng(bus.getLatitude(), bus.getLongitude());
                MarkerOptions mo = new MarkerOptions().position(pos)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_icon))
                        .title(bus.getVehicleSerial());

                if(mMap != null)
                {
                    marker = mMap.addMarker(mo);
                    markers.put(bus.getVehicleSerial(), marker);
                }
            }

            if(marker != null)
            {
                LatLng pos = new LatLng(bus.getLatitude(), bus.getLongitude());
                marker.setRotation((float) bus.getHeading());
                MarkerAnimation.animateMarkerToGB(marker, pos, new LatLngInterpolator.Linear());
            }
        }
    }

    @Override
    public void onBusPositionsLoadingError(Exception exception) {

    }

    public void scheduleAlarm() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MapVisualizationActivity.this, BusDownloadIntentService.class);
                MapVisualizationActivity.this.startService(i);
                if(!MapVisualizationActivity.this.isFinishing())
                {
                    handler.postDelayed(this, REFRESHING_DELAY);
                }
            }
        }, 100);
    }



}
