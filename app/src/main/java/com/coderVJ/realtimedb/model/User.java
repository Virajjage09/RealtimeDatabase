package com.coderVJ.realtimedb.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Viraj Jage on 11/109/17.
 */

@IgnoreExtraProperties
public class User {

    public String name;
    public String mobileno;
    public String address;
    public String email;

    public String deliveryDate;


    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String name, String email, String mobileno, String address, String deliveryDate) {
        this.name = name;
        this.email = email;
        this.mobileno = mobileno;
        this.address = address;
        this.deliveryDate = deliveryDate;
    }

    public User(String mobileno, String deliveryDate) {

        this.mobileno = mobileno;
        this.deliveryDate = deliveryDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
