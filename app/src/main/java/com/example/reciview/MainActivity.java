package com.example.reciview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

//MainActivity is the Home Screen
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Locate buttons for the activity.
        Button btnSignIn = findViewById(R.id.button_signin);
        Button btnRegister = findViewById(R.id.button_create_account);

        //TODO: Implement proper Google sign in method.
        Button btnGoogleSign = findViewById(R.id.button_google_signin);

    }
}