package com.example.sisterlyapp;



import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class NewsPortalMain extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    TabItem mnews, mdashboard;
    PagerAdapter pagerAdapter;
    ArrayList<NewsPost> NewsPost = new ArrayList<>();
    int[] images = {R.drawable.news1, R.drawable.news2, R.drawable.news3, R.drawable.news4};

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsportal);
        /* RecyclerView recyclerView = findViewById(R.id.recyclerviewofnews); */
        mnews = findViewById(R.id.News);
        mdashboard = findViewById(R.id.Dashboard);
        ViewPager viewPager = findViewById(R.id.newsContainer);
        tabLayout = findViewById(R.id.tabmenu);
//        pagerAdapter=new PagerAdapter(getSupportFragmentManager(),6);
        viewPager.setAdapter(pagerAdapter);


        setUpNewsPost();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0 || tab.getPosition() == 1) {
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    private void setUpNewsPost() {
        String[] Publisher = getResources().getStringArray(R.array.publishers);
        String[] Content = getResources().getStringArray(R.array.newscontent);
        String[] Title = getResources().getStringArray(R.array.title);

        for (int i = 0; i < Math.min(Publisher.length, Math.min(Content.length, images.length)); i++) {
            NewsPost.add(new NewsPost(Publisher[i], Content[i], images[i]));
        }

    }
}





        //        EdgeToEdge.enable(this);
//        tabLayout = findViewById(R.id.tabmenu);
//        viewPager2 =findViewById(R.id.view_pager);
//        PagerAdapter PagerAdapter = new PagerAdapter(this);
//        viewPager2.setAdapter(pagerAdapter);
//
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager2.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//
//        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                tabLayout.getTabAt(position).select();
//            }
//        });
//
//



//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;



//            public void OnClick
//        });

