package com.example.sisterlyapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ViewAppointmentActivity extends AppCompatActivity {

    private LinearLayout bookingsContainer;
    private ImageView backButtonView;
    private ProgressBar progressBar;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_appointment);

        // Initialize UI elements
        bookingsContainer = findViewById(R.id.bookingsContainer);
        backButtonView = findViewById(R.id.backButtonView);
        progressBar = findViewById(R.id.progressBar);

        // Show progress bar while loading
        progressBar.setVisibility(View.VISIBLE);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("AppointmentDetails");

        // Fetch and display all appointments
        fetchAllAppointments();

        // Back button click listener
        backButtonView.setOnClickListener(v -> finish());

    }

    private void fetchAllAppointments() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);  // Hide progress bar

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Map<String, String> appointmentDetails = (Map<String, String>) snapshot.getValue();
                        if (appointmentDetails != null) {
                            addBookingView(snapshot.getKey(), appointmentDetails);
                        }
                    }
                } else {
                    Toast.makeText(ViewAppointmentActivity.this, "No appointment details found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);  // Hide progress bar
                Toast.makeText(ViewAppointmentActivity.this, "Error fetching data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addBookingView(String bookingId, Map<String, String> appointmentDetails) {
        // Create a TextView for each booking and set its text
        TextView bookingView = new TextView(this);
        bookingView.setTextSize(16);
        bookingView.setTextColor(getResources().getColor(R.color.black));

        // Format booking details
        String bookingText = "Booking ID: " + bookingId + "\n" +
                "Date: " + (appointmentDetails.get("Date") != null ? appointmentDetails.get("Date") : "N/A") + "\n" +
                "Time: " + (appointmentDetails.get("Time") != null ? appointmentDetails.get("Time") : "N/A") + "\n" +
                "Medium: " + (appointmentDetails.get("Medium") != null ? appointmentDetails.get("Medium") : "N/A") + "\n" +
                "Counselling Reason: " + (appointmentDetails.get("CounselingType") != null ? appointmentDetails.get("CounselingType") : "N/A") + "\n" +
                "Anonymity: " + (appointmentDetails.get("Anonymity") != null ? appointmentDetails.get("Anonymity") : "N/A") + "\n";

        bookingView.setText(bookingText);

        // Add padding and margins
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 16, 0, 16);
        bookingView.setLayoutParams(layoutParams);

        // Add the TextView to the container
        bookingsContainer.addView(bookingView);
    }
}