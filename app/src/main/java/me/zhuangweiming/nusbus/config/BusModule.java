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
    Retrofit provideRestAdapter(OkHttpClient client, Gson gson, Properties properties) {

        Executor executor = Executors.newCachedThreadPool();

        String url = "https://" + properties.getProperty("app.busstopapi.url");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .callbackExecutor(executor)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }

    @Provides
    @Singleton
    BusStopApi provideBusStopApi(Retrofit retrofit) {

        return retrofit.create(BusStopApi.class);
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