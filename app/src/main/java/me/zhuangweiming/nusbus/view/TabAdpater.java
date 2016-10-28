package me.zhuangweiming.nusbus.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiming on 29/10/16.
 */

public class TabAdpater extends FragmentPagerAdapter {
    private final List<Fragment> tabs = new ArrayList<>();
    private final List<String> tabTitles = new ArrayList<>();

    public TabAdpater(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    public void addFragment(Fragment fragment, String title) {
        tabs.add(fragment);
        tabTitles.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
//        return tabTitles.get(position);
    }
}
