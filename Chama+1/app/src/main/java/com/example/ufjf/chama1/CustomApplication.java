package com.example.ufjf.chama1;

import android.app.Application;

import com.example.ufjf.model.User;
import com.firebase.client.Firebase;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eduar on 7/27/2016.
 */
public class CustomApplication extends Application {

    User currentUser;
    LatLng currentLocation;
    GoogleApiClient mGoogleApiClient;
    List<User> userList;

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        userList = new ArrayList<User>();
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

    public List<User> getUserList() { return userList; }

    public void addUserList(User user){  userList.add(user); }

    public void removeUserList(User user){ userList.remove(user); }

    public void clearUserList(){ userList.clear(); }
}
