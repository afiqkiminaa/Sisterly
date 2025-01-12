package com.example.sisterlyapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter{

    int tabcount;
    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm,behavior);
        tabcount=behavior;
    }

    public PagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity.getSupportFragmentManager());
    }

    @NonNull
//    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0:
                return new FragmentNews();
            case 1:
                return new FragmentDashboard();
            default:
                return new FragmentNews();

        }
    }

//    @Override
    public int getItemCount() {
        return 2;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
