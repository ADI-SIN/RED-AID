package com.example.charul.webservicedemo;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Arushi on 6/8/2017.
 */
public class dataget extends Application {

    public void onCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
