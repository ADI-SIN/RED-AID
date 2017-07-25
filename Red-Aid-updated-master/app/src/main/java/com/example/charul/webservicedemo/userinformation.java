package com.example.charul.webservicedemo;

/**
 * Created by charul on 17/4/17.
 */
public class userinformation {
    public String  name, contact,spinner;
    public double lat,lon;

    public userinformation(){

    }
    public userinformation(String name,String contact,String spinner,double lat,double lon){
        this.name=name;
        this.contact=contact;
        this.spinner=spinner;
       this.lat=lat;
        this.lon=lon;
    }
}
