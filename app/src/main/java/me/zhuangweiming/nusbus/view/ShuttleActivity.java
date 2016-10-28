package me.zhuangweiming.nusbus.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import me.zhuangweiming.nusbus.R;


public class ShuttleActivity extends AppCompatActivity {
    private Toolbar shuttleToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuttle);

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ShuttleFragment shuttleFragment = new ShuttleFragment();
        fragmentTransaction.add(R.id.shuttle_fragment, shuttleFragment);
        fragmentTransaction.commit();

        shuttleToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(shuttleToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        shuttleToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
