package com.example.ufjf.chama1;

import android.app.Application;

import com.example.ufjf.model.User;
import com.firebase.client.Firebase;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by eduar on 7/27/2016.
 */
public class CustomApplication extends Application {

    User currentUser;
    LatLng currentLocation;
    GoogleApiClient mGoogleApiClient;

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

    public void setCurrentLocation(LatLng currentLocation) { this.currentLocation = currentLocation; }

    public GoogleApiClient getmGoogleApiClient() { return mGoogleApiClient; }

    public void setmGoogleApiClient(GoogleApiClient mGoogleApiClient) {this.mGoogleApiClient = mGoogleApiClient; }
}
