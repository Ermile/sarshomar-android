package com.ermile.sarshomari.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ermile.sarshomari.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HelpActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    String mobile_help_center_url;
    WebView help_web_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationIcon(R.drawable.ic_arrow_forward_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        help_web_view = (WebView) findViewById(R.id.helpcenter_view);

        WebSettings webSettings = help_web_view.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        if (getSharedPreferences("language", MODE_PRIVATE).getString("language","en").equals("fa")){

            mobile_help_center_url =  "https://sarshomar.com/fa/help";

        }else if (getSharedPreferences("language", MODE_PRIVATE).getString("language","en").equals("en")){

            mobile_help_center_url =  "https://sarshomar.com/help";

        }else {
            mobile_help_center_url =  "https://sarshomar.com/help";
        }

        help_web_view.loadUrl(mobile_help_center_url);

        help_web_view.getSettings().setJavaScriptEnabled(true);
        help_web_view.getSettings().setAllowUniversalAccessFromFileURLs(true);
        help_web_view.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        help_web_view.getSettings().setPluginState(WebSettings.PluginState.ON);

        help_web_view.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }
        });


    }


    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && help_web_view.canGoBack()) {
            help_web_view.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}




