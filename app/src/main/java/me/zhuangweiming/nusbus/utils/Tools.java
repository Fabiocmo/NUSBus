package me.zhuangweiming.nusbus.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Thibault on 16/10/2016.
 */

public class Tools {
    /**
     * This method allows to get Properties file from Assets
     *
     * @param context  context to get Assets
     * @param filename the properties filename located in Assets
     * @return the Properties object associated to the file
     * @throws IOException
     */
    public static Properties getProperties(Context context, String filename) throws IOException
    {
        InputStream inputStream = context.getAssets().open(filename);
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties;
    }
}
