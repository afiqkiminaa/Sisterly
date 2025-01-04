package com.example.sisterlyapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sisterlyapp.databinding.ActivityLoginBinding;
import com.example.sisterlyapp.utilities.Constants;
import com.example.sisterlyapp.utilities.HashUtils;
import com.example.sisterlyapp.utilities.PreferenceManager;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(getApplicationContext());

        // Change the main page when users that already logged in starts the app
        if (preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)) {
            Intent intent = new Intent(getApplicationContext(), ChatListActivity.class);
            startActivity(intent);
            finish();
        }

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }

    private void setListeners() {
        binding.BtnLogin.setOnClickListener(v -> {
            if (isValidLoginDetails()) {
                login();
            }
        });
    }

    private void login() {
        loading(true);

        // Get the entered email and password
        String email = binding.inputEmail.getText().toString();
        String password = binding.inputPassword.getText().toString();

        // Hash the password before querying the database
        String hashedPassword = HashUtils.hashPassword(password, email);  // Using email as salt

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_EMAIL, email)  // Search by email
                .whereEqualTo(Constants.KEY_PASSWORD, hashedPassword)  // Search by hashed password
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() > 0) {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                        preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());
                        preferenceManager.putString(Constants.KEY_NAME, documentSnapshot.getString(Constants.KEY_NAME));
                        preferenceManager.putString(Constants.KEY_USERNAME, documentSnapshot.getString(Constants.KEY_USERNAME));
                        preferenceManager.putString(Constants.KEY_EMAIL, documentSnapshot.getString(Constants.KEY_EMAIL));
                        preferenceManager.putString(Constants.KEY_MOBILE, documentSnapshot.getString(Constants.KEY_MOBILE));
                        Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        loading(false);
                        showToast("Unable to sign in. Please try again.");
                    }
                });
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            binding.BtnLogin.setVisibility(View.INVISIBLE);
            binding.LoadBar.setVisibility(View.VISIBLE);
        } else {
            binding.LoadBar.setVisibility(View.INVISIBLE);
            binding.BtnLogin.setVisibility(View.VISIBLE);
        }
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private Boolean isValidLoginDetails() {
        if (binding.inputEmail.getText().toString().trim().isEmpty()) {
            showToast("Enter your email");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.inputEmail.getText().toString()).matches()) {
            showToast("Enter valid email");
            return false;
        } else if (binding.inputPassword.getText().toString().trim().isEmpty()) {
            showToast("Enter your password");
            return false;
        } else {
            return true;
        }
    }
}
