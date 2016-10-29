package me.zhuangweiming.nusbus.view;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhuangweiming.nusbus.BusApplication;
import me.zhuangweiming.nusbus.R;
import me.zhuangweiming.nusbus.model.Bus;
import me.zhuangweiming.nusbus.services.BusBroadCastReceiver;
import me.zhuangweiming.nusbus.services.BusDownloadIntentService;
import me.zhuangweiming.nusbus.services.BusPositionsLoadedCallback;
import me.zhuangweiming.nusbus.services.BusTrackingService;
import me.zhuangweiming.nusbus.utils.LatLngInterpolator;
import me.zhuangweiming.nusbus.utils.MarkerAnimation;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapTabFragment extends Fragment implements OnMapReadyCallback, BusPositionsLoadedCallback {


    private GoogleMap mMap;
    private HashMap<String, Bus> bussPositions = new HashMap<>();
    private HashMap<String, Marker> markers = new HashMap<>();
    private final static int REFRESHING_DELAY = 2000;


    private BusBroadCastReceiver busBroadcastReceiver;
    private IntentFilter intentFilter;

    @Inject
    BusTrackingService busTrackingService;

    @BindView(R.id.mapView)
    MapView mapView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        busBroadcastReceiver = new BusBroadCastReceiver(this);
        intentFilter = new IntentFilter("hello");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View currentView = inflater.inflate(R.layout.fragment_map_tab, container, false);

        ButterKnife.bind(this, currentView);

        mapView.onCreate(savedInstanceState);

        mapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(this);

        ((BusApplication) getActivity().getApplication()).getAppComponent().inject(this);

        return currentView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(mMap != null)
        {
            busTrackingService.registerForUpdates(this);
        }

        //getActivity().registerReceiver(busBroadcastReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();

        busTrackingService.unregisterForUpdates(this);
        //getActivity().unregisterReceiver(busBroadcastReceiver);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        busTrackingService.registerForUpdates(this);

        mMap = googleMap;
        LatLng nus = new LatLng(1.290665504, 103.772663576);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nus, 10));
        busTrackingService.registerForUpdates(this);
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
                MarkerOptions mo = new MarkerOptions().position(pos).icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_icon)).title(bus.getVehicleSerial());

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
                Intent i = new Intent(getActivity(), BusDownloadIntentService.class);
                getActivity().startService(i);
                if(!getActivity().isFinishing())
                {
                    handler.postDelayed(this, REFRESHING_DELAY);
                }
            }
        }, 100);
    }
}
