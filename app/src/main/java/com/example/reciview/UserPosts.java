package com.example.reciview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
//TODO: Add create post feature.
public class UserPosts extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Post> postList;
    DatabaseReference dbRef1 = FirebaseDatabase.getInstance().getReference("posts");
    AdapterUserPosts adapterUserPosts;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posts_viewer);

        recyclerView = findViewById(R.id.recyclerView);
        postList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterUserPosts = new AdapterUserPosts(this, postList);
        recyclerView.setAdapter(adapterUserPosts);

        dbRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Post post = dataSnapshot.getValue(Post.class);
                    postList.add(post);
                }//end for
                adapterUserPosts.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }//end OnCreate
}