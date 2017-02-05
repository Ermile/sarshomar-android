package com.ermile.sarshomari;

import android.app.Application;
import android.content.res.Configuration;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by AGENT_13 on 1/4/2017.
 */

public class AppController extends Application {

    RequestQueue mRequestQueue;





    @Override
    public void onCreate() {
        super.onCreate();
        setLanguage();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/IRANSans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );



// Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

// Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

// Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

// Start the queue
        mRequestQueue.start();



    }

    public void setLanguage(){



        if (getSharedPreferences("language", MODE_PRIVATE).getString("language","en").equals("fa")){

            String languageToLoad  = "fa"; // your language
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getResources().updateConfiguration(config,
                    getResources().getDisplayMetrics());
            // setSystemLocale(config, locale);



            Toast.makeText(getApplicationContext(),"iran",Toast.LENGTH_SHORT).show();



        }else if (getSharedPreferences("language", MODE_PRIVATE).getString("language","en").equals("en")){
            String languageToLoad  = "en"; // your language
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getResources().updateConfiguration(config,
                    getResources().getDisplayMetrics());
            Toast.makeText(getApplicationContext(),"us",Toast.LENGTH_SHORT).show();

        }



    }

    public RequestQueue getmRequestQueue(){
        return mRequestQueue;
    }


}