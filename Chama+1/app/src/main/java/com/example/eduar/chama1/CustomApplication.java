package com.example.eduar.chama1;

import android.app.Application;

import com.example.eduar.model.User;
import com.firebase.client.Firebase;

/**
 * Created by eduar on 7/27/2016.
 */
public class CustomApplication extends Application {

    User currentUser;

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
}
