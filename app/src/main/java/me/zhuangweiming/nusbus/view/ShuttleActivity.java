package me.zhuangweiming.nusbus.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import me.zhuangweiming.nusbus.R;
import me.zhuangweiming.nusbus.model.Tab;


public class ShuttleActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager tabViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuttle);
//
//        FragmentManager fragmentManager = this.getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        ShuttleFragment shuttleFragment = new ShuttleFragment();
//        fragmentTransaction.add(R.id.shuttle_fragment, shuttleFragment);
//        fragmentTransaction.commit();


        tabViewPager = (ViewPager) findViewById(R.id.viewpager);
        Tab.setupShuttleActivityViewPager(tabViewPager, getSupportFragmentManager());

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(tabViewPager);
        Tab.setupTabIcons(tabLayout);
    }
}
