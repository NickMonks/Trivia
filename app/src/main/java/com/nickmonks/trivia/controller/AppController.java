package com.nickmonks.trivia.controller;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

// Singleton pattern for the Controller of the Application
// Mainly handles Volley request. Important to notice is that
// we pass the context of the applicaton and not the activity
public class AppController extends Application {
    private static AppController instance;
    private RequestQueue requestQueue;

    public static synchronized AppController getInstance(){
        return instance;
    }

    public RequestQueue getRequestQueue(){
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
