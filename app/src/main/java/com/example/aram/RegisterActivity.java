package com.example.aram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String LOG_TAG = RegisterActivity.class.getName();
    private static final int SECRET_KEY = 99;

    EditText nameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    EditText passwordConfirmEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bundle bundle = getIntent().getExtras();
        int secret_key = bundle.getInt("SECRET_KEY");

        if(secret_key != SECRET_KEY){
            finish();
        }

        mAuth = FirebaseAuth.getInstance();
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordConfirmEditText = findViewById(R.id.passwordConfirmEditText);
        findViewById(R.id.registerButton).setOnClickListener(register());
        findViewById(R.id.cancelButton).setOnClickListener(cancel());
    }

    public void startMenu(){
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }

    public View.OnClickListener register(){
        return view -> {
            String fullName = nameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String passwordConfirm = passwordConfirmEditText.getText().toString();
            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()){
                Log.e(LOG_TAG, "All fields must contain something");
                return;
            }
            if(!password.equals(passwordConfirm)){
                Log.e(LOG_TAG, "Passwords don't match");
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Log.d(LOG_TAG, "User created successfully");
                        startMenu();
                    }
                    else{
                        Log.d(LOG_TAG, "User creation failed");
                        Toast.makeText(RegisterActivity.this, "Problem:" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        };
    }

    public View.OnClickListener cancel(){
        return view -> finish();
    }

}