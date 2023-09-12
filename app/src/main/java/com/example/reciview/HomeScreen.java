package com.example.reciview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        //Retrieve the intent.
        Intent signInIntent = getIntent();

        TextView textview2 = findViewById(R.id.textView2);
        textview2.setText(signInIntent.getStringExtra("username"));
    }
}