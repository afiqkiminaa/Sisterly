package com.example.sisterlyapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ForumFragment extends Fragment {

    private ArrayList<ForumPost> forumPosts = new ArrayList<>();
    private int[] images = {R.drawable.sisterhood, R.drawable.kpwkm, R.drawable.parking_lot};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_forum, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        setUpForumPosts();
        ForumAdapter adapter = new ForumAdapter(requireContext(), forumPosts);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        return view;
    }

    private void setUpForumPosts() {
        String[] username = getResources().getStringArray(R.array.usernames);
        String[] content = getResources().getStringArray(R.array.content);

        for (int i = 0; i < Math.min(username.length, Math.min(content.length, images.length)); i++) {
            forumPosts.add(new ForumPost(username[i], content[i], images[i]));
        }
    }
}
