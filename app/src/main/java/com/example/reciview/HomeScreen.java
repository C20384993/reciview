package com.example.reciview;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class HomeScreen extends AppCompatActivity {

    protected void OnCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        //Locate buttons for the activity
        Button btnRecipePosts = findViewById(R.id.button_posts_link);
        ImageButton btnAvatarImg = findViewById(R.id.imagebtn_avatar);
        ImageButton btnRotdImg = findViewById(R.id.imagebtn_rotd_voting_link);



    }
}
