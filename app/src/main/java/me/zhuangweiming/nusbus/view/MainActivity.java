package me.zhuangweiming.nusbus.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import me.zhuangweiming.nusbus.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction busStopFragTransaction = fragmentManager.beginTransaction();
        Fragment busStopFragment = new BusStopFragment();
        busStopFragTransaction.add(R.id.bus_stop_fragment, busStopFragment);
        busStopFragTransaction.commit();
    }


}

