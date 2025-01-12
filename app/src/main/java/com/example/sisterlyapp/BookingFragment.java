package com.example.sisterlyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BookingFragment extends Fragment {

    // Parameters for the fragment (if needed)
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public BookingFragment() {
        // Required empty public constructor
    }

    /**
     * Factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static BookingFragment newInstance(String param1, String param2) {
        BookingFragment fragment = new BookingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_initial_appointment, container, false);

        // Initialize buttons
        Button viewDetailsButton = view.findViewById(R.id.viewAppointmentDetailsButton);
        Button addAppointmentButton = view.findViewById(R.id.addAppointmentButton);

        // Set click listeners for buttons
        viewDetailsButton.setOnClickListener(v -> {
            // Navigate to the Appointment Details Activity
            Intent intent = new Intent(getActivity(), ViewAppointmentActivity.class);
            startActivity(intent);
        });

        addAppointmentButton.setOnClickListener(v -> {
            // Navigate to the Add Appointment Activity
            Intent intent = new Intent(getActivity(), BookingAppointmentActivity.class);
            startActivity(intent);
        });

        return view;
    }
}
