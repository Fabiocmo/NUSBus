package me.zhuangweiming.nusbus.model;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.google.android.gms.maps.model.LatLng;

import me.zhuangweiming.nusbus.R;
import me.zhuangweiming.nusbus.view.BusStopFragment;
import me.zhuangweiming.nusbus.view.MapTabFragment;
import me.zhuangweiming.nusbus.view.TabAdpater;

/**
 * Created by weiming on 29/10/16.
 */

public class Tab {
    private static int[] tabIcons = {
            R.drawable.icon_map,
            R.drawable.icon_bus
    };

    public static void setupMainActivityViewPager(ViewPager viewPager, FragmentManager fragmentManager) {
        TabAdpater adpater = new TabAdpater(fragmentManager);
        LatLng nus = new LatLng(1.290665504, 103.772663576);
        adpater.addFragment(MapTabFragment.newInstance(nus), "Map");
        adpater.addFragment(new BusStopFragment(), "Bus Stop");
        viewPager.setAdapter(adpater);
    }

    public static void setupTabIcons(TabLayout tabLayout) {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }
}
