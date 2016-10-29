package me.zhuangweiming.nusbus.config;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.zhuangweiming.nusbus.services.BusStopApi;
import me.zhuangweiming.nusbus.services.BusTrackingApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Thibault on 16/10/2016.
 */
@Module
public class BusModule {

    @Provides
    @Singleton
    Gson provideGson() {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        return gson;
    }

    @Provides
    @Singleton
    Retrofit.Builder provideRestAdapter(OkHttpClient client, Gson gson) {

        Executor executor = Executors.newCachedThreadPool();

        Retrofit.Builder retrofit = new Retrofit.Builder()
                .client(client)
                .callbackExecutor(executor)
                .addConverterFactory(GsonConverterFactory.create(gson));

        return retrofit;
    }

    @Provides
    @Singleton
    BusStopApi provideBusStopApi(Retrofit.Builder retrofit,  Properties properties) {

        String url = "https://" + properties.getProperty("app.busstopapi.url")+"/";
        return retrofit.baseUrl(url).build().create(BusStopApi.class);
    }

    @Provides
    @Singleton
    BusTrackingApi provideBusTrackingApi(Retrofit.Builder retrofit,  Properties properties) {

        String url = "https://" + properties.getProperty("app.busposapi.url")+"/";
        return retrofit.baseUrl(url).build().create(BusTrackingApi.class);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Context context) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient clientOK = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        return clientOK;
    }

}