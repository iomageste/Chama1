package com.example.eduar.chama1;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by eduar on 7/27/2016.
 */
public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
