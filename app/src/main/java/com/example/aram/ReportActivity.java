package com.example.aram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ReportActivity extends AppCompatActivity {
    private static final int SECRET_KEY = 99;
    private final String LOG_TAG = MenuActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Bundle bundle = getIntent().getExtras();
        int secret_key = bundle.getInt("SECRET_KEY");

        if(secret_key != SECRET_KEY || user == null){
            finish();
        }
        Log.d(LOG_TAG, "Logged in user:" + user);
    }
}