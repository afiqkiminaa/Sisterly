package com.example.sisterlyapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateProfileActivity extends AppCompatActivity {

    private EditText editTextUpdateName, editTextUpdateMobile;
    private String textUsername, textMobile;
    private FirebaseAuth authProfile;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        progressBar = findViewById(R.id.PprogressBar);
        editTextUpdateName = findViewById(R.id.ETUpdateProfileName);
        editTextUpdateMobile = findViewById(R.id.ETUpdateProfileMobile);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        //Show Profile Data
        showProfile(firebaseUser);

        //Update Profile
        Button buttonUpdateProfile = findViewById(R.id.BtnUpdateProfile);
        buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile(firebaseUser);
            }
        });
    }
    //update profile
    private void updateProfile(FirebaseUser firebaseUser) {

        //Validate Mobile Number using matcher and pattern (Regular Expression)
        String mobileRegex = "[0][1][0-9]{8}";  //First no. can be {0} and second no. can be {1} and rest 8 numbers can be any number
        Matcher mobileMatcher;
        Pattern mobilePattern = Pattern.compile(mobileRegex);
        mobileMatcher = mobilePattern.matcher(textMobile);


        if (TextUtils.isEmpty(textUsername)){
            Toast.makeText(UpdateProfileActivity.this, "Please enter your username", Toast.LENGTH_LONG).show();;
            editTextUpdateName.setError("Username is required");
            editTextUpdateName.requestFocus();
        } else if (TextUtils.isEmpty(textMobile)) {
            Toast.makeText(UpdateProfileActivity.this, "Please enter your phone no.", Toast.LENGTH_LONG).show();
            editTextUpdateMobile.setError("Phone no. is required");
            editTextUpdateMobile.requestFocus();
        } else if (textMobile.length() != 10) {
            Toast.makeText(UpdateProfileActivity.this, "Please re-enter your phone no.", Toast.LENGTH_LONG).show();
            editTextUpdateMobile.setError("Phone no. should be 10 digits");
            editTextUpdateMobile.requestFocus();
        } else if (!mobileMatcher.find()) {
            Toast.makeText(UpdateProfileActivity.this, "Please re-enter your phone no.", Toast.LENGTH_LONG).show();
            editTextUpdateMobile.setError("Mobile no. is not valid");
            editTextUpdateMobile.requestFocus();
        } else {
            textUsername = editTextUpdateName.getText().toString();
            textMobile = editTextUpdateMobile.getText().toString();

            //Enter user data into the firebase Realtime Database. Set up dependencies
            ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textUsername, textMobile);

            //Extract user reference from Database for "Registered Users"
            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");

            String userID = firebaseUser.getUid();

            progressBar.setVisibility(View.VISIBLE);

            referenceProfile.child(userID).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){

                        //Setting new display name
//                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(textUsername).build();
//                        firebaseUser.updateProfile(profileUpdates);

                        Toast.makeText(UpdateProfileActivity.this, "Update Successful!", Toast.LENGTH_SHORT).show();

                        //Stop user from returning to UpdateProfileActivity on pressing back button and close activity
                        Intent intent = new Intent(UpdateProfileActivity.this, UserProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        try{
                            throw task.getException();
                        } catch (Exception e){
                            Toast.makeText(UpdateProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    //Fetch data from Firebase and display
    private void showProfile(FirebaseUser firebaseUser) {
        String userIDofRegistered = firebaseUser.getUid();

        //Extracting User Reference from Database for "Registered Users"
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");

        progressBar.setVisibility(View.VISIBLE);

        referenceProfile.child(userIDofRegistered).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if (readUserDetails != null){
                    textUsername = firebaseUser.getDisplayName();
                    textMobile = readUserDetails.mobile;

                    editTextUpdateName.setText(textUsername);
                    editTextUpdateMobile.setText(textMobile);

                } else {
                    Toast.makeText(UpdateProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}