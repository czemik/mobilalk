package com.example.aram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuActivity extends AppCompatActivity {
    private static final int SECRET_KEY = 99;
    private final String LOG_TAG = MenuActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Bundle bundle = getIntent().getExtras();
        int secret_key = bundle.getInt("SECRET_KEY");

        if(secret_key != SECRET_KEY || user == null){
            finish();
        }
        Log.d(LOG_TAG, "Logged in user:" + user);
        findViewById(R.id.logoutButton).setOnClickListener(logout());
        findViewById(R.id.reportButton).setOnClickListener(toReport());
        findViewById(R.id.oldReportButton).setOnClickListener(toOldReports());


    }

    public View.OnClickListener logout(){
        Log.d(LOG_TAG,"To register");
        return view -> {
            FirebaseAuth.getInstance().signOut();
            finish();
        };

    }
    public View.OnClickListener toReport(){
        Log.d(LOG_TAG,"To report");
        return view -> {
            Intent intent = new Intent(this, ReportActivity.class);
            intent.putExtra("SECRET_KEY", SECRET_KEY);
            startActivity(intent);
        };

    }
    public View.OnClickListener toOldReports(){
        Log.d(LOG_TAG,"To old reports");
        return view -> {
            Intent intent = new Intent(this, OldReportsActivity.class);
            intent.putExtra("SECRET_KEY", SECRET_KEY);
            startActivity(intent);
        };

    }


}