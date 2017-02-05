package com.ermile.sarshomari.Classes;

/**
 * Created by AGENT_13 on 2/4/2017.
 */

import android.content.Context;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.Map;


public class HttpRequest {

    private static HttpRequest mInstance;
    private RequestQueue mRequestQueue;
    private static Context context;

    public interface OnSuccessListener {
        void onSuccess(String response);
    }

    public interface OnFailedListener {
        void onFailed(String error);
    }

    public HttpRequest(Context ctx) {
        context = ctx;
    }

    public static synchronized HttpRequest getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new HttpRequest(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return mRequestQueue;
    }

    public void post(String url, final Map<String, String> params, final OnSuccessListener success, final OnFailedListener failure) {
        StringRequest sr = new StringRequest(Request.Method.POST, url,
                new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        success.onSuccess(response);
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        failure.onFailed(error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        getRequestQueue().add(sr);
    }

    public void get(String url, final OnSuccessListener success, final OnFailedListener failure) {
        StringRequest sr = new StringRequest(Request.Method.GET, url,
                new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        success.onSuccess(response);
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        failure.onFailed(error.getMessage());
                    }
                });
        getRequestQueue().add(sr);
    }
}
