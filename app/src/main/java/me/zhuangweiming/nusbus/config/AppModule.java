package me.zhuangweiming.nusbus.config;

import android.content.Context;

import java.io.IOException;
import java.util.Properties;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.zhuangweiming.nusbus.BusApplication;
import me.zhuangweiming.nusbus.utils.Tools;

/**
 * Created by Thibault on 16/10/2016.
 */

@Module
public class AppModule {
    protected final BusApplication application;
    protected final Properties properties;

    public AppModule(BusApplication application, String property) throws IOException {
        this.application = application;

        this.properties = Tools.getProperties(application, property);
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    Properties provideProperties() {
        return properties;
    }
}