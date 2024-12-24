package com.example.sisterlyapp;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private EditText editTextRegisterFullName, editTextRegisterUsername, editTextRegisterEmail, editTextRegisterPwd,
                     editTextRegisterMobile;
    private ProgressBar progressBar;
    private RadioButton radioButtonAgree;
    private static final String TAG = "SignupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

//        getSupportActionBar().setTitle("Register");

        Toast.makeText(SignupActivity.this, "You can register now", Toast.LENGTH_LONG).show();

        progressBar = findViewById(R.id.progressBar);
        editTextRegisterFullName = findViewById(R.id.ETFullName);
        editTextRegisterUsername = findViewById(R.id.ETUsername);
        editTextRegisterEmail = findViewById(R.id.ETEmail);
        editTextRegisterPwd = findViewById(R.id.ETPassword);
        editTextRegisterMobile = findViewById(R.id.ETPhone);

        radioButtonAgree = findViewById(R.id.RBAgreement);

        Button buttonRegister = findViewById(R.id.BtnRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Obtain the entered data
                String textFullName = editTextRegisterFullName.getText().toString();
                String textUsername = editTextRegisterUsername.getText().toString();
                String textEmail = editTextRegisterEmail.getText().toString();
                String textPwd = editTextRegisterPwd.getText().toString();
                String textMobile = editTextRegisterMobile.getText().toString();
                String textAgree;

                if (TextUtils.isEmpty(textFullName)){
                    Toast.makeText(SignupActivity.this, "Please enter your full name", Toast.LENGTH_LONG).show();;
                    editTextRegisterFullName.setError("Full Name is required");
                    editTextRegisterFullName.requestFocus();
                } else if (TextUtils.isEmpty(textUsername)) {
                    Toast.makeText(SignupActivity.this, "Please enter your username", Toast.LENGTH_LONG).show();
                    editTextRegisterUsername.setError("Username is required");
                    editTextRegisterUsername.requestFocus();
                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(SignupActivity.this, "Please enter your email", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Email is required");
                    editTextRegisterEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(SignupActivity.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmail.setError("Valid email is required");
                    editTextRegisterEmail.requestFocus();
                } else if (TextUtils.isEmpty(textPwd)) {
                    Toast.makeText(SignupActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwd.setError("Password is required");
                    editTextRegisterPwd.requestFocus();
                } else if (textPwd.length() < 6) {
                    Toast.makeText(SignupActivity.this, "Password should be at least 6 characters", Toast.LENGTH_SHORT).show();
                    editTextRegisterMobile.setError("Password is too weak");
                    editTextRegisterMobile.requestFocus();
                } else if (TextUtils.isEmpty(textMobile)) {
                    Toast.makeText(SignupActivity.this, "Please enter your phone no.", Toast.LENGTH_SHORT).show();
                    editTextRegisterMobile.setError("Phone no. is required");
                    editTextRegisterMobile.requestFocus();
                } else if (textMobile.length() != 10) {
                    Toast.makeText(SignupActivity.this, "Please re-enter your phone no.", Toast.LENGTH_SHORT).show();
                    editTextRegisterMobile.setError("Phone no. should be 10 digits");
                    editTextRegisterMobile.requestFocus();
                } else {
                    textAgree = radioButtonAgree.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(textFullName, textUsername, textEmail, textPwd, textMobile);
                }
            }
        });
    }
    // User register using the credentials given
    private void registerUser (String textFullname, String textUsername, String textEmail, String textPwd, String textMobile){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmail, textPwd).addOnCompleteListener(SignupActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SignupActivity.this, "User registered successfully", Toast.LENGTH_LONG).show();
                            FirebaseUser firebaseUser = auth.getCurrentUser();

                            //Enter user data into the Firebase Realtime Database.
                            ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textFullname, textUsername,textMobile);

                            //Extracting user reference from Database for "Registered Users"
                            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");

                            referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        //Send Verification EMail
                                        firebaseUser.sendEmailVerification();

                                        Toast.makeText(SignupActivity.this, "User registered successfully. Please verify your email", Toast.LENGTH_LONG).show();

//                                        //Open User profile after successful registration
//                                        Intent intent = new Intent(SignupActivity.this, UserProfileActivity.class);
//                                        //To prevent user from returning back to signup activity on pressing back button after registration
//                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        startActivity(intent);
//                                        finish();  // to close Signup Activity
//                                    } else {
//                                        Toast.makeText(SignupActivity.this, "User registered failed. Please try again", Toast.LENGTH_LONG).show();
//                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });


                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                editTextRegisterPwd.setError("Your password is to weak, Kingly use a mix of alphabets, number and special character");
                                editTextRegisterPwd.requestFocus();
                            } catch (FirebaseAuthInvalidCredentialsException e){
                                editTextRegisterPwd.setError("Your email is invalid or already used. Kindly re-enter.");
                                editTextRegisterPwd.requestFocus();
                            } catch (FirebaseAuthUserCollisionException e) {
                                editTextRegisterPwd.setError("User is already registered with this email. Use another email.");
                                editTextRegisterPwd.requestFocus();
                            } catch (Exception e) {
                                Log.e(TAG, e.getMessage());
                                Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }
}
