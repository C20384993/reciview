package com.example.reciview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeScreen extends AppCompatActivity {

    private Button btnPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        //Retrieve the intent.
        Intent signInIntent = getIntent();

        btnPosts = findViewById(R.id.button_posts_link);
        TextView textview2 = findViewById(R.id.textView2);
        textview2.setText(signInIntent.getStringExtra("username"));

        btnPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Find cleaner way to pass account info.
                Intent intentPostsScreen = new Intent(getApplicationContext(), UserPosts.class);
                intentPostsScreen.putExtra("username",signInIntent.getStringExtra("username"));
                intentPostsScreen.putExtra("password",signInIntent.getStringExtra("password"));
                intentPostsScreen.putExtra("emailAddress",signInIntent.getStringExtra("emailAddress"));
                intentPostsScreen.putExtra("phonenum",signInIntent.getStringExtra("phonenum"));
                startActivity(intentPostsScreen);
            }
        });
    }
}