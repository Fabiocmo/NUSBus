package me.zhuangweiming.nusbus.view;


import android.Manifest;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
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
import me.zhuangweiming.nusbus.model.BusStop;
import me.zhuangweiming.nusbus.services.BusBroadCastReceiver;
import me.zhuangweiming.nusbus.services.BusPositionsLoadedCallback;
import me.zhuangweiming.nusbus.services.BusStopLoadedCallback;
import me.zhuangweiming.nusbus.services.BusStopService;
import me.zhuangweiming.nusbus.services.BusTrackingService;
import me.zhuangweiming.nusbus.services.DataCache;
import me.zhuangweiming.nusbus.utils.LatLngInterpolator;
import me.zhuangweiming.nusbus.utils.MarkerAnimation;
import me.zhuangweiming.nusbus.view.fragmentcallbacks.BusStopClickCallback;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapTabFragment extends Fragment implements OnMapReadyCallback, BusPositionsLoadedCallback, LocationListener,
        BusStopLoadedCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private final static String POS_KEY = "pos_key";
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 133 ;
    private static final String LOCATION_KEY = "loc_key";
    private static final String REQUESTING_LOCATION_UPDATES_KEY = "req_key";

    private GoogleMap mMap;
    private HashMap<String, Bus> bussPositions = new HashMap<>();
    private HashMap<String, Marker> markers = new HashMap<>();
    private List<BusStop> stops;

    private BusBroadCastReceiver busBroadcastReceiver;
    private IntentFilter intentFilter;
    private LatLng zoomPos;
    private Marker mPos;
    boolean mRequestingLocationUpdates;

    @Inject
    BusTrackingService busTrackingService;
    @Inject
    BusStopService busStopService;
    @Inject
    DataCache cache;

    @BindView(R.id.mapView)
    MapView mapView;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;

    public static MapTabFragment newInstance(LatLng pos)
    {
        MapTabFragment mp = new MapTabFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(POS_KEY, pos);
        mp.setArguments(bundle);
        return mp;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        busBroadcastReceiver = new BusBroadCastReceiver(this);
        intentFilter = new IntentFilter("hello");

        updateValuesFromBundle(savedInstanceState);

    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // Update the value of mRequestingLocationUpdates from the Bundle, and
            // make sure that the Start Updates and Stop Updates buttons are
            // correctly enabled or disabled.
            if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        REQUESTING_LOCATION_UPDATES_KEY);
            }

            // Update the value of mCurrentLocation from the Bundle and update the
            // UI to show the correct latitude and longitude.
            if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
                // Since LOCATION_KEY was found in the Bundle, we can be sure that
                // mCurrentLocationis not null.
                mCurrentLocation = savedInstanceState.getParcelable(LOCATION_KEY);
            }

            updateMap();
        }
    }

    private void updateMap()
    {
        LatLng pos = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        if(mMap!=null)
        {
            if(mPos ==  null)
            {
                MarkerOptions mo = new MarkerOptions()
                        .position(pos)
                        .title("You");
                mPos = mMap.addMarker(mo);

                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(pos);
                builder.include(zoomPos);
                int padding = 200;
                LatLngBounds bounds = builder.build();
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                mMap.animateCamera(cu);
            }

            mPos.setPosition(pos);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View currentView = inflater.inflate(R.layout.fragment_map_tab, container, false);

        ButterKnife.bind(this, currentView);

        this.zoomPos = getArguments().getParcelable(POS_KEY);

        mapView.onCreate(savedInstanceState);

        mapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(this);

        ((BusApplication) getActivity().getApplication()).getAppComponent().inject(this);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(12000);
        mLocationRequest.setFastestInterval(6000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        launchLocationRequests();

        return currentView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(mMap != null)
        {
            busTrackingService.registerForUpdates(this);
        }

        launchLocationRequests();

    }

    @Override
    public void onPause() {
        super.onPause();

        busTrackingService.unregisterForUpdates(this);

        if(mGoogleApiClient.isConnected() && mRequestingLocationUpdates)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mRequestingLocationUpdates = false;
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY,
                mRequestingLocationUpdates);
        savedInstanceState.putParcelable(LOCATION_KEY, mCurrentLocation);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public  void onStop()
    {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        busTrackingService.registerForUpdates(this);

        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zoomPos, 17));
        busTrackingService.registerForUpdates(this);

        if(cache.getBusStops() == null)
        {
            busStopService.loadBusStops(this);
        }
        else {
            this.onBusStopsLoaded(cache.getBusStops());
        }
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

    @Override
    public void onBusStopsLoaded(List<BusStop> busStops) {
        this.stops = busStops;

        for(BusStop stop:busStops)
        {
            LatLng pos = new LatLng(stop.getLatitude(), stop.getLongitude());
            MarkerOptions mo = new MarkerOptions()
                    .position(pos)
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ic_launcher", 70, 70)))
                    .title(stop.getName());
            mMap.addMarker(mo);
        }
    }

    public Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "mipmap", getActivity().getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        imageBitmap.recycle();
        return resizedBitmap;
    }

    @Override
    public void onBusStopLoadingError(Exception exception) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        for(BusStop stop : stops)
        {
            if(marker.getTitle().compareTo(stop.getName()) == 0)
            {
                if(getActivity() instanceof BusStopClickCallback)
                {
                    ((BusStopClickCallback)getActivity()).onBusStopClicked(stop);
                }
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        launchLocationRequests();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        updateMap();
    }

    public void launchLocationRequests()
    {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
        else {

            if(mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
                PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                mRequestingLocationUpdates = true;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    if(mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
                        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                        mRequestingLocationUpdates = true;
                    }
                } else {

                }
                return;
            }

        }
    }
}
