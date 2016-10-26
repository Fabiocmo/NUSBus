package me.zhuangweiming.nusbus.view;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import me.zhuangweiming.nusbus.R;


public class ShuttleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuttle);

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ShuttleFragment shuttleFragment = new ShuttleFragment();
        fragmentTransaction.add(R.id.shuttle_fragment, shuttleFragment);
        fragmentTransaction.commit();
    }
}
