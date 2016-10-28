package me.zhuangweiming.nusbus.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import me.zhuangweiming.nusbus.R;
import me.zhuangweiming.nusbus.model.Tab;


public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager tabViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FragmentManager fragmentManager = this.getSupportFragmentManager();
//        FragmentTransaction busStopFragTransaction = fragmentManager.beginTransaction();
//        Fragment busStopFragment = new BusStopFragment();
//        busStopFragTransaction.add(R.id.bus_stop_fragment, busStopFragment);
//        busStopFragTransaction.commit();

        tabViewPager = (ViewPager) findViewById(R.id.viewpager);
        Tab.setupMainActivityViewPager(tabViewPager, getSupportFragmentManager());

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(tabViewPager);
        Tab.setupTabIcons(tabLayout);
    }
}

