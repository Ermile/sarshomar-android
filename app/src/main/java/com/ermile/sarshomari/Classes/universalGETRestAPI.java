package com.ermile.sarshomari.Classes;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by AGENT_13 on 2/1/2017.
 *
 *
 * A CLASS TO SEND GET REQUESTS TO A REST API SERVER BASED ON ASYNCTASK HTTPS LIBRARY.
 *
 *
 *
 */

public class universalGETRestAPI {

    public String getStringResponse(String get_url){

        final String res="null";






        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("authorization", "Basic bXl2aWV3OjkwMTk=");






        client.get(get_url, new TextHttpResponseHandler() {



            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                throwable.printStackTrace();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

            }

            @Override
            public void onStart() {
                // called before request is started
            }


            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }



        });

        return res;

    }



}
