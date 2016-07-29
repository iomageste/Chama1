package com.example.eduar.chama1;

import android.app.Application;

import com.example.eduar.model.User;
import com.firebase.client.Firebase;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by eduar on 7/27/2016.
 */
public class CustomApplication extends Application {

    User currentUser;
    LatLng currentLocation;

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }

    public void setCurrentUser(User user){
        this.currentUser = user;
    }

    public User getCurrentUser(){
        return this.currentUser;
    }

    public LatLng getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(LatLng currentLocation) {
        this.currentLocation = currentLocation;
    }
}
