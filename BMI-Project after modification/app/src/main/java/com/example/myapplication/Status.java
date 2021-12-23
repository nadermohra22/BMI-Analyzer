package com.example.myapplication;

import java.io.Serializable;

public class Status {


    private String Date;
    private  int weight;
    private  String statusRate;
    private  int length;


    public Status() {
    }

    public Status(String date, int weight, String statusRate, int length) {
        Date = date;
        this.weight = weight;
        this.statusRate = statusRate;
        this.length = length;
    }


    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getStatusRate() {
        return statusRate;
    }

    public void setStatusRate(String statusRate) {
        this.statusRate = statusRate;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}