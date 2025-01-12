package com.example.sisterlyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class HomeActivity extends Fragment {

    public HomeActivity() {
        super(R.layout.activity_home);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton news_button = view.findViewById(R.id.news_button);
        View.OnClickListener OCLNews = new View.OnClickListener(){
            public void onClick(View v){
                Navigation.findNavController(view).navigate(R.id.newsFragment);
            }
        };
        news_button.setOnClickListener(OCLNews);

        ImageButton book_button = view.findViewById(R.id.book_button);
        View.OnClickListener OCLBook = new View.OnClickListener(){
            public void onClick(View v){
                Navigation.findNavController(view).navigate(R.id.bookFragment);
            }
        };
        book_button.setOnClickListener(OCLBook);

//        ImageButton report_button = view.findViewById(R.id.report_button);
//        View.OnClickListener OCLReport = new View.OnClickListener(){
//            public void onClick(View v){
//                Navigation.findNavController(view).navigate(R.id.reportFragment);
//            }
//        };
//        report_button.setOnClickListener(OCLReport);

        // ImageButton report_button = view.findViewById(R.id.report_button);
        // View.OnClickListener OCLReport = new View.OnClickListener(){
        //     public void onClick(View v){
        //         Intent intent = new Intent(HomeActivity.this, ReportPage.class);
        //         startActivity(intent);
        //     }
        // };
        // report_button.setOnClickListener(OCLReport);
        ImageButton report_button = view.findViewById(R.id.report_button);
        View.OnClickListener OCLReport = new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(requireActivity(), ReportPage.class);
                startActivity(intent);
            }
        };
        report_button.setOnClickListener(OCLReport);

        ImageButton community_button = view.findViewById(R.id.community_button);
        View.OnClickListener OCLCommunity= new View.OnClickListener(){
            public void onClick(View v){
                Navigation.findNavController(view).navigate(R.id.forumFragment);
            }
        };
        community_button.setOnClickListener(OCLCommunity);

        ImageButton organisation_button = view.findViewById(R.id.organisation_button);
        View.OnClickListener OCLOrg = new View.OnClickListener(){
            public void onClick(View v){
                Navigation.findNavController(view).navigate(R.id.organisationFragment);
            }
        };
        organisation_button.setOnClickListener(OCLOrg);

        ImageButton sos_button = view.findViewById(R.id.sos_button1);
        View.OnClickListener OCLSOS = new View.OnClickListener(){
            public void onClick(View v){
                Navigation.findNavController(view).navigate(R.id.SOSFragment);
            }
        };
        sos_button.setOnClickListener(OCLSOS);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Try to pop the current fragment from the back stack
                if (!Navigation.findNavController(view).popBackStack()) {
                    // If no fragments are popped, let the system handle the back press (default back action)
                    requireActivity().onBackPressed();
                }
            }
        });



//        // News Button
//        view.findViewById(R.id.news_button).setOnClickListener(v -> {
//            Fragment newsFragment = new NewsFragment();
//            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
//            transaction.replace(R.id.fragmentContainer, newsFragment);
//            transaction.addToBackStack(null); // Add to back stack for back navigation
//            transaction.commit();
//        });
//
//        // Book Appointment Button
//        view.findViewById(R.id.book_button).setOnClickListener(v -> {
//            Fragment bookFragment = new BookFragment();
//            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
//            transaction.replace(R.id.fragmentContainer, bookFragment);
//            transaction.addToBackStack(null);
//            transaction.commit();
//        });
//
//        // Report Button
//        view.findViewById(R.id.report_button).setOnClickListener(v -> {
//            Fragment reportFragment = new ReportFragment();
//            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
//            transaction.replace(R.id.fragmentContainer, reportFragment);
//            transaction.addToBackStack(null);
//            transaction.commit();
//        });
//
//        // Community Forum Button
//        view.findViewById(R.id.community_button).setOnClickListener(v -> {
//            Fragment communityFragment = new CommunityFragment();
//            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
//            transaction.replace(R.id.fragmentContainer, communityFragment);
//            transaction.addToBackStack(null);
//            transaction.commit();
//        });
//
//        // Search Organisation Button
//        view.findViewById(R.id.organisation_button).setOnClickListener(v -> {
//            Fragment organisationFragment = new OrganisationFragment();
//            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
//            transaction.replace(R.id.fragmentContainer, organisationFragment);
//            transaction.addToBackStack(null);
//            transaction.commit();
//        });
//
//        // SOS Button
//        view.findViewById(R.id.sos_button).setOnClickListener(v -> {
//            Fragment sosFragment = new SOSFragment();
//            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
//            transaction.replace(R.id.fragmentContainer, sosFragment);
//            transaction.addToBackStack(null);
//            transaction.commit();
//        });
    }
}


