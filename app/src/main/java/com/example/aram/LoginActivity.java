package com.example.aram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = LoginActivity.class.getName();
    private static final int SECRET_KEY = 99;
    private FirebaseAuth mAuth;
    EditText emailET;
    EditText passwordET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        emailET = findViewById(R.id.emailEditText);
        passwordET = findViewById(R.id.passwordEditText);
        findViewById(R.id.registerButton).setOnClickListener(toRegister());
        findViewById(R.id.loginButton).setOnClickListener(login());

    }

    public View.OnClickListener login(){
        Log.d(LOG_TAG,"Login started");
        return view -> {
            String email = emailET.getText().toString();
            String password = passwordET.getText().toString();
        };

    }

    public View.OnClickListener toRegister(){
        Log.d(LOG_TAG,"To register");
        return view -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            intent.putExtra("SECRET_KEY", SECRET_KEY);
            startActivity(intent);
        };

    }
}