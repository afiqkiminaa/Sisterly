package com.example.sisterlyapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sisterlyapp.databinding.ActivitySignupBinding;
import com.example.sisterlyapp.utilities.Constants;
import com.example.sisterlyapp.utilities.HashUtils;
import com.example.sisterlyapp.utilities.PreferenceManager;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private PreferenceManager preferenceManager;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        firebaseAuth = FirebaseAuth.getInstance();
        setListeners();
    }

    private void setListeners() {
        binding.BtnRegister.setOnClickListener(v -> {
            if (isValidSignUpDetails()) {
                signUp();
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void signUp() {
        loading(true);

        String email = binding.ETEmail.getText().toString().trim();
        String password = binding.ETPassword.getText().toString().trim();

        // Hash the password before creating the user
        String hashedPassword = HashUtils.hashPassword(password, email);  // Using email as salt

        // Create a user with email and hashed password
        firebaseAuth.createUserWithEmailAndPassword(email, hashedPassword)
                .addOnCompleteListener(task -> {
                    loading(false);
                    if (task.isSuccessful()) {
                        // Save additional user details to Firestore with hashed password
                        saveUserDetailsToFirestore(hashedPassword);
                    } else {
                        if (task.getException() != null) {
                            showToast(task.getException().getMessage());
                        } else {
                            showToast("Registration failed. Please try again.");
                        }
                    }
                });
    }

    private void saveUserDetailsToFirestore(String hashedPassword) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> user = new HashMap<>();
        user.put(Constants.KEY_NAME, binding.ETFullName.getText().toString());
        user.put(Constants.KEY_USERNAME, binding.ETUsername.getText().toString());
        user.put(Constants.KEY_EMAIL, binding.ETEmail.getText().toString());
        user.put(Constants.KEY_PASSWORD, hashedPassword);  // Store the hashed password
        user.put(Constants.KEY_MOBILE, binding.ETPhone.getText().toString());

        database.collection(Constants.KEY_COLLECTION_USERS)
                .add(user)
                .addOnSuccessListener(documentReference -> {
                    preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                    preferenceManager.putString(Constants.KEY_USER_ID, documentReference.getId());
                    preferenceManager.putString(Constants.KEY_NAME, binding.ETFullName.getText().toString());
                    preferenceManager.putString(Constants.KEY_USERNAME, binding.ETUsername.getText().toString());
                    preferenceManager.putString(Constants.KEY_EMAIL, binding.ETEmail.getText().toString());
                    preferenceManager.putString(Constants.KEY_MOBILE, binding.ETPhone.getText().toString());

                    Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .addOnFailureListener(exception -> showToast(exception.getMessage()));
    }

    private Boolean isValidSignUpDetails() {
        if (binding.ETFullName.getText().toString().trim().isEmpty()) {
            showToast("Enter Fullname");
            return false;
        } else if (binding.ETUsername.getText().toString().trim().isEmpty()) {
            showToast("Enter your Username");
            return false;
        } else if (binding.ETEmail.getText().toString().trim().isEmpty()) {
            showToast("Enter your email");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.ETEmail.getText().toString()).matches()) {
            showToast("Valid email is required");
            return false;
        } else if (binding.ETPassword.getText().toString().trim().isEmpty()) {
            showToast("Enter your password");
            return false;
        } else if (binding.ETPhone.getText().toString().trim().isEmpty()) {
            showToast("Enter your phone no.");
            return false;
        } else {
            return true;
        }
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            binding.BtnRegister.setVisibility(View.INVISIBLE);
            binding.ProgressBar.setVisibility(View.VISIBLE);
        } else {
            binding.ProgressBar.setVisibility(View.INVISIBLE);
            binding.BtnRegister.setVisibility(View.VISIBLE);
        }
    }
}

