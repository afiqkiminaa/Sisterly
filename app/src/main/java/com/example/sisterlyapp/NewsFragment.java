package com.example.sisterlyapp;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class NewsFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem mNews, mDashboard;
    private PagerAdapter pagerAdapter;
    private ArrayList<NewsPost> newsPosts = new ArrayList<>();
    private ArrayList<NewsPost> dashboardPosts = new ArrayList<>();
    private int[] images = {R.drawable.news1, R.drawable.news2, R.drawable.news3, R.drawable.news4};

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.newsportal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        tabLayout = view.findViewById(R.id.tabmenu);
        viewPager = view.findViewById(R.id.newsContainer);
        mNews = view.findViewById(R.id.News);
        mDashboard = view.findViewById(R.id.Dashboard);

        // Set up news post data
//        setUpNewsPost();

        // Set up pager adapter
        pagerAdapter = new CustomPagerAdapter(getContext(), newsPosts);
        viewPager.setAdapter(pagerAdapter);

        setUpNewsPost();
        pagerAdapter = new CustomPagerAdapter(getContext(), newsPosts);
        viewPager.setAdapter(pagerAdapter); // Refresh viewpager

        // Tab layout and view pager interaction
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    // Load news-specific data for tab 0
                    setUpNewsPost(); // Update news data or reload the data specific to the News tab
                    pagerAdapter = new CustomPagerAdapter(getContext(), newsPosts); // Update adapter with new data
                    viewPager.setAdapter(pagerAdapter); // Refresh viewpager
                } else if (tab.getPosition() == 1) {
//                    // Load dashboard-specific data for tab 1
//                    setUpDashboardData(); // Add your method to handle dashboard data
//                    pagerAdapter = new CustomPagerAdapter(getContext(), dashboardPosts); // Update adapter with new data
//                    viewPager.setAdapter(pagerAdapter); // Refresh viewpager
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
        String[] publishers = getResources().getStringArray(R.array.publishers);
        String[] contents = getResources().getStringArray(R.array.newscontent);
        String[] titles = getResources().getStringArray(R.array.title);

        newsPosts.clear(); // Clear previous data

        for (int i = 0; i < Math.min(publishers.length, Math.min(contents.length, images.length)); i++) {
            newsPosts.add(new NewsPost(publishers[i], contents[i], images[i]));
        }

        // Notify the adapter that the data set has changed
//        if (pagerAdapter != null) {
//            pagerAdapter.notifyDataSetChanged();
//        }
    }

    private void setUpDashboardData() {
        String[] publishers = getResources().getStringArray(R.array.publishers);
        String[] contents = getResources().getStringArray(R.array.newscontent);
        String[] titles = getResources().getStringArray(R.array.title);

        dashboardPosts.clear(); // Clear previous data

        for (int i = 0; i < Math.min(publishers.length, Math.min(contents.length, images.length)); i++) {
            dashboardPosts.add(new NewsPost(publishers[i], contents[i], images[i]));
        }

//        // Notify the adapter that the data set has changed
//        if (pagerAdapter != null) {
//            pagerAdapter.notifyDataSetChanged();
//        }
    }

    // Example PagerAdapter class
    class CustomPagerAdapter extends PagerAdapter {
        private Context context;
        private ArrayList<NewsPost> newsPosts;

        public CustomPagerAdapter(Context context, ArrayList<NewsPost> newsPosts) {
            this.context = context;
            this.newsPosts = newsPosts;
        }

        @Override
        public int getCount() {
            return newsPosts.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view;

            // Check the position and inflate different layouts for News and Dashboard
            if (position == 0) {
                // Inflate the layout for News tab (use item_news layout)
                view = LayoutInflater.from(context).inflate(R.layout.item_news, container, false);

            } else {
                // Inflate the layout for Dashboard tab (use item_dashboard layout)
                view = LayoutInflater.from(context).inflate(R.layout.fragment_sx_violence, container, false);

            }

            // Add the view to the container
            container.addView(view);
            return view;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
