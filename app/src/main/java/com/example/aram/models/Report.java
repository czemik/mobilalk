package com.example.aram.models;

public class Report {
    private int amount;
    private int month;
    private int year;
    private String id;
    private String uid;

    public Report(int amount, int month, int year, String id, String uid) {
        this.amount = amount;
        this.month = month;
        this.year = year;
        this.id = id;
        this.uid = uid;
    }

    public int getAmount() {
        return amount;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
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
}
