package com.example.sisterlyapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class InfoDetailsActivity extends AppCompatActivity {

    private EditText nameInput, ageInput, contactInput, nricInput, counselingTypeInput;
    private RadioGroup anonymityRadioGroup;
    private DatabaseReference databaseReference;
    private Button submitButton;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_info_details);

        // Initialize views
        nameInput = findViewById(R.id.nameInput);
        ageInput = findViewById(R.id.ageInput);
        contactInput = findViewById(R.id.contactInput);
        nricInput = findViewById(R.id.nricInput);
        counselingTypeInput = findViewById(R.id.counselingTypeInput);
        anonymityRadioGroup = findViewById(R.id.anonymityRadioGroup);
        backButton = findViewById(R.id.backButtonInfoDetails);
        submitButton = findViewById(R.id.submitButton);

        // Initialize Firebase database
        databaseReference = FirebaseDatabase.getInstance().getReference("AppointmentDetails");

        // Get appointment details from BookingAppointmentActivity
        Intent intent = getIntent();
        String date = intent.getStringExtra("Date");
        String time = intent.getStringExtra("Time");
        String medium = intent.getStringExtra("Medium");

        // Submit data to Firebase
        submitButton.setOnClickListener(v -> submitDataToFirebase(date, time, medium));

        // Back Button Click Listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous activity
                Intent intent = new Intent(InfoDetailsActivity.this, BookingAppointmentActivity.class);
                startActivity(intent);
            }
        });
    }

    private void submitDataToFirebase(String date, String time, String medium) {
        String name = nameInput.getText().toString().trim();
        String age = ageInput.getText().toString().trim();
        String contact = contactInput.getText().toString().trim();
        String nric = nricInput.getText().toString().trim();
        String counselingType = counselingTypeInput.getText().toString().trim();

        int selectedAnonymityId = anonymityRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedAnonymity = findViewById(selectedAnonymityId);

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(age) || TextUtils.isEmpty(contact) ||
                TextUtils.isEmpty(nric) || TextUtils.isEmpty(counselingType) || selectedAnonymity == null) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        String anonymity = selectedAnonymity.getText().toString();

        // Create a map to store appointment details
        Map<String, String> appointmentDetails = new HashMap<>();
        appointmentDetails.put("Date", date);
        appointmentDetails.put("Time", time);
        appointmentDetails.put("Medium", medium);
        appointmentDetails.put("Name", name);
        appointmentDetails.put("Age", age);
        appointmentDetails.put("Contact", contact);
        appointmentDetails.put("NRIC", nric);
        appointmentDetails.put("CounselingType", counselingType);
        appointmentDetails.put("Anonymity", anonymity);

        // Store data in Firebase
        databaseReference.push().setValue(appointmentDetails)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Appointment booked successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, InitialAppointmentActivity.class);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to book appointment: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}