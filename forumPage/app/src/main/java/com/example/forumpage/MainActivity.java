package com.example.forumpage;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<ForumPost> ForumPost = new ArrayList<>();
    int[] images = {R.drawable.sisterhood, R.drawable.kpwkm, R.drawable.parking_lot};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        setUpForumPost();
        ForumAdapter adapter = new ForumAdapter(this, ForumPost);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpForumPost(){
        String[] username = getResources().getStringArray(R.array.usernames);
        String[] content = getResources().getStringArray(R.array.content);

        for (int i = 0; i < Math.min(username.length, Math.min(content.length, images.length)); i++) {
            ForumPost.add(new ForumPost(username[i], content[i], images[i]));
        }
    }
}