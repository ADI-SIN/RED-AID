package com.example.charul.webservicedemo;

/**
 * Created by Arushi on 6/12/2017.
 */
public class Person {
    private String name;
    private String address;
    private String age;
    private String number;
    private String height;
    private String weight;
    private String blood;
    private double lat;
    private double lon;

    public Person() {
      /*Blank default constructor essential for Firebase*/
    }
    //Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLong(Double lon) {
        this.lon = lon;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setHeight(String height){
        this.height = height;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getAddress() {
        return address;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLong() {
        return lon;
    }

    public String getAge() {
        return age;
    }

    public String getNumber() {
        return number;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getBlood() {
        return blood;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
