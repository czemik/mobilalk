package com.example.aram;

import android.content.res.Resources;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aram.models.Report;

public class DateConverter {
    public static String convertDate(AppCompatActivity appCompatActivity, Report report){
        String month_str = "none";
        switch (report.getMonth()){
            case 0:
                month_str = appCompatActivity.getString(R.string.jan);
                break;
            case 1:
                month_str = appCompatActivity.getString(R.string.feb);
                break;
            case 2:
                month_str = appCompatActivity.getString(R.string.mar);
                break;
            case 3:
                month_str = appCompatActivity.getString(R.string.apr);
                break;
            case 4:
                month_str = appCompatActivity.getString(R.string.may);
                break;
            case 5:
                month_str = appCompatActivity.getString(R.string.jun);
                break;
            case 6:
                month_str = appCompatActivity.getString(R.string.jul);
                break;
            case 7:
                month_str = appCompatActivity.getString(R.string.aug);
                break;
            case 8:
                month_str = appCompatActivity.getString(R.string.sep);
                break;
            case 9:
                month_str = appCompatActivity.getString(R.string.oct);
                break;
            case 10:
                month_str = appCompatActivity.getString(R.string.nov);
                break;
            case 11:
                month_str = appCompatActivity.getString(R.string.dec);
                break;
        }


        return appCompatActivity.getString(R.string.date)+ " " + report.getYear() + ". " +month_str;

    }



}
