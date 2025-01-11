package com.example.forumpage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ForumViewHolder> {
    Context context;
    ArrayList<ForumPost> ForumPost;

    public ForumAdapter(Context context, ArrayList<ForumPost> ForumPost) {
        this.context = context;
        this.ForumPost = ForumPost;
    }

    @NonNull
    @Override
    public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.forum_item, parent,false);
        return new ForumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForumViewHolder holder, int position) {
        holder.username.setText(ForumPost.get(position).getUsername());
        holder.content.setText(ForumPost.get(position).getContent());
        holder.postImage.setImageResource(ForumPost.get(position).getImages());
    }

    @Override
    public int getItemCount() {
        return ForumPost.size();
    }

    static class ForumViewHolder extends RecyclerView.ViewHolder {

        TextView username, content;
        ImageView postImage;

        public ForumViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.tv_username);
            content = itemView.findViewById(R.id.tv_post_content);
            postImage = itemView.findViewById(R.id.iv_post_image);
        }
    }
}


