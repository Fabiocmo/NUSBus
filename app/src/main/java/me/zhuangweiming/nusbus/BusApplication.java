package me.zhuangweiming.nusbus;

import android.app.Application;

import java.io.IOException;

import me.zhuangweiming.nusbus.config.AppComponent;
import me.zhuangweiming.nusbus.config.AppModule;
import me.zhuangweiming.nusbus.config.BusModule;
import me.zhuangweiming.nusbus.config.DaggerAppComponent;

/**
 * Created by Thibault on 16/10/2016.
 */
public class BusApplication  extends Application {

    private AppComponent dependencyGraph;

    public AppComponent getAppComponent()
    {
        return dependencyGraph;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            dependencyGraph = DaggerAppComponent
                    .builder()
                    .appModule(new AppModule(this, "app.properties"))
                    .busModule(new BusModule())
                    .build();
        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }
}
