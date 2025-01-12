package com.example.sisterlyapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FragmentNews extends Fragment {

    private ArrayList<NewsPost> newsPosts = new ArrayList<>();
    private int[] images = {R.drawable.news1, R.drawable.news2, R.drawable.news3, R.drawable.news4};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the fragment layout
        View v = inflater.inflate(R.layout.fragment_news, container, false);

        // Initialize RecyclerView from the inflated view
        RecyclerView recyclerView = v.findViewById(R.id.recyclerviewofnews);


        return v;
    }
}
