package com.example.sisterlyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    Context context;

    ArrayList<NewsPost> newsPost;

    public NewsAdapter(Context context, ArrayList<NewsPost> newsPosts){
        this.context=context;
        this.newsPost=newsPosts;
    }
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.item_news, parent,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.publisher.setText(NewsPost.get(position).getPublisher());
        holder.newscontent.setText(NewsPost.get(position).getContent());
        holder.postImage.setImageResource(NewsPost.get(position).getImages());
    }

    @Override
    public int getItemCount() {
        return 4;
    }
    static class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView publisher, newscontent, title;
        ImageView postImage;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            publisher = itemView.findViewById(R.id.newssource);
            title = itemView.findViewById(R.id.newstitle);
            postImage = itemView.findViewById(R.id.newsimage);
            newscontent =itemView.findViewById(R.id.contentnews);
        }
    }
}
