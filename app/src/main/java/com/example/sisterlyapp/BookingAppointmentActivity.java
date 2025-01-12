package com.example.sisterlyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookingAppointmentActivity extends AppCompatActivity {

    private CalendarView appointmentCalendar;
    private RadioGroup timeRadioGroup, mediumRadioGroup;
    private Button nextButton;
    private ImageView backButton;
    private ProgressBar progressBar;
    private DatabaseReference databaseReference;
    private String selectedDate = ""; // Holds the selected date


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking_appointment);

        // Initialize views
        appointmentCalendar = findViewById(R.id.appointmentCalendar);
        timeRadioGroup = findViewById(R.id.RGTime);
        mediumRadioGroup = findViewById(R.id.mediumRadioGroup);
        nextButton = findViewById(R.id.nextButton);
        progressBar = findViewById(R.id.ProgressBar);
        backButton = findViewById(R.id.backButtonBooking);

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("AppointmentDetails");

        // Set listener for date selection
        appointmentCalendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Format selected date as "YYYY-MM-DD"
            selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
        });

        // Set onClickListener for the Next button
        nextButton.setOnClickListener(v -> storeAppointmentData());

        // Back Button Click Listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous activity
                Intent intent = new Intent(BookingAppointmentActivity.this, InitialAppointmentActivity.class);
                startActivity(intent);
            }
        });
    }

    private void storeAppointmentData() {
        // Get selected time
        int selectedTimeId = timeRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedTimeButton = findViewById(selectedTimeId);
        String selectedTime = (selectedTimeButton != null) ? selectedTimeButton.getText().toString() : "";

        // Get selected medium
        int selectedMediumId = mediumRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedMediumButton = findViewById(selectedMediumId);
        String selectedMedium = (selectedMediumButton != null) ? selectedMediumButton.getText().toString() : "";

        // Validate inputs
        if (selectedDate.isEmpty() || selectedTime.isEmpty() || selectedMedium.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show progress bar
        progressBar.setVisibility(View.VISIBLE);

        // Check Firebase for existing appointment with the same date and time
        databaseReference.orderByChild("Date").equalTo(selectedDate).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean timeSlotTaken = false;

                // Check each appointment on the same date
                for (DataSnapshot appointmentSnapshot : dataSnapshot.getChildren()) {
                    String existingTime = appointmentSnapshot.child("Time").getValue(String.class);
                    if (existingTime != null && existingTime.equals(selectedTime)) {
                        timeSlotTaken = true;
                        break;
                    }
                }

                if (timeSlotTaken) {
                    // Time slot is already booked
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(BookingAppointmentActivity.this, "Time slot is already booked. Please select a different time.", Toast.LENGTH_SHORT).show();
                } else {
                    // Time slot is available, proceed to InfoDetailsActivity
                    Intent intent = new Intent(BookingAppointmentActivity.this, InfoDetailsActivity.class);
                    intent.putExtra("Date", selectedDate);
                    intent.putExtra("Time", selectedTime);
                    intent.putExtra("Medium", selectedMedium);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(BookingAppointmentActivity.this, "Failed to check availability. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}