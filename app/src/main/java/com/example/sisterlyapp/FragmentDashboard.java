package com.example.sisterlyapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


public class FragmentDashboard extends Fragment {
    private Button buttonfm, buttonan, buttonsv, buttonhm;
    ImageView imageView;

//    FragmentManager fragmentManager = getSupportFragmentManager();
//    fragmentManager.beginTransaction()
//            .replace(R.id.fragment_container, ExampleFragment.class, null)
//    .setReorderingAllowed(true)
//    .addToBackStack("name") // Name can be null
//    .commit();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // Initialize buttons
        buttonfm = view.findViewById(R.id.btnfm);
        buttonan = view.findViewById(R.id.btnan);
        buttonsv = view.findViewById(R.id.btnsv);
        buttonhm = view.findViewById(R.id.btnhm);

        // Set click listeners
        buttonfm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Button forced marriage clicked.", Toast.LENGTH_SHORT).show();
            }
        });

        buttonsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Button sexual violence clicked.", Toast.LENGTH_SHORT).show();
            }
        });
        buttonan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Button awareness clicked.", Toast.LENGTH_SHORT).show();
            }
        });
        buttonhm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Button heatmap clicked.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

//    private void replaceFragment(Fragment fragment) {
//        if (getFragmentManager() != null) {
//            FragmentTransaction transaction = getFragmentManager().beginTransaction();
//            transaction.replace(R.id.fragment_container, fragment); // Replace fragment_container with the ID of your container
//            transaction.addToBackStack(null); // Optional: Add to back stack
//            transaction.commit();
        }


