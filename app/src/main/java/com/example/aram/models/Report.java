package com.example.aram.models;


import android.content.res.Resources;

import com.example.aram.R;

public class Report {
    private Integer amount;
    private Integer month;
    private Integer year;
    private String id;
    private String uid;

    public Report(int amount, int month, int year, String id, String uid) {
        this.amount = amount;
        this.month = month;
        this.year = year;
        this.id = id;
        this.uid = uid;
    }

    public Report(){

    }
    public Integer getAmount() {
        return amount;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getYear() {
        return year;
    }

    public String getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Report{" +
                "amount=" + amount +
                ", month=" + month +
                ", year=" + year +
                ", id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }


}
