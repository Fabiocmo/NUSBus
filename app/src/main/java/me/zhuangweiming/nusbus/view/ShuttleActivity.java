package me.zhuangweiming.nusbus.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

import me.zhuangweiming.nusbus.R;
import me.zhuangweiming.nusbus.model.BusStop;


public class ShuttleActivity extends AppCompatActivity {

    private static final String STOP_KEY = "stop_key";

    private BusStop stop;

    public static Intent getIntent(Context ctx, BusStop stop) {
        Intent intent = new Intent(ctx, ShuttleActivity.class);
        intent.putExtra(STOP_KEY, stop);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuttle_collapse);

        stop = getIntent().getParcelableExtra(STOP_KEY);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        collapsingToolbar.setTitleEnabled(false);
        getSupportActionBar().setTitle(stop.getName());

        ShuttleFragment shuttleFragment = ShuttleFragment.newInstance(stop);
        MapTabFragment mapTabFragment = MapTabFragment.newInstance(new LatLng(stop.getLatitude(), stop.getLongitude()));

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, shuttleFragment)//TODO use tags
                .commit();
        fragmentManager.beginTransaction()
                .replace(R.id.map_container, mapTabFragment)//TODO use tags
                .commit();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
