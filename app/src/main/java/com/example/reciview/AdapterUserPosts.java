package com.example.reciview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterUserPosts extends RecyclerView.Adapter<AdapterUserPosts.PostsViewHolder>{

    Context context;
    ArrayList<Post> postList;

    public AdapterUserPosts(Context context, ArrayList<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.post_view,parent,false);
        return new PostsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.txtviewPostTitle.setText(post.getTitle());
        holder.txtviewPostAuthor.setText(post.getAuthor());
        holder.txtviewPostContent.setText(post.getContent());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostsViewHolder extends RecyclerView.ViewHolder {
        TextView txtviewPostTitle;
        TextView txtviewPostAuthor;
        TextView txtviewPostContent;
        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtviewPostTitle = itemView.findViewById(R.id.post_title);
            txtviewPostAuthor = itemView.findViewById(R.id.post_author);
            txtviewPostContent = itemView.findViewById(R.id.post_content);
        }
    }
}
