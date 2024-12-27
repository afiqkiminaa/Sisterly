package com.example.sisterlyapp;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity{

    private EditText editTextLoginEmailOrUsername, editTextLoginPwd;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

//        getSupportActionBar().setTitle("Login");

        editTextLoginEmailOrUsername = findViewById(R.id.ETEmailOrUsername);
        editTextLoginPwd = findViewById(R.id.ETLoginPassword);
        progressBar = findViewById(R.id.LoadBar);

        authProfile = FirebaseAuth.getInstance();

        //Show Hide Password using Eye Icon
        ImageView imageViewShowHidePwd = findViewById(R.id.imageView_show_hide_pwd);
        imageViewShowHidePwd.setImageResource(R.drawable.eye);
        imageViewShowHidePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextLoginPwd.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    //If password is visible then hide it
                    editTextLoginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //Change icon
                    imageViewShowHidePwd.setImageResource(R.drawable.eye);
                } else {
                    editTextLoginPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewShowHidePwd.setImageResource(R.drawable.show);
                }
            }
        });

        //Login User
        Button buttonLogin = findViewById(R.id.BtnLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = editTextLoginEmailOrUsername.getText().toString();
                String textUsername = editTextLoginEmailOrUsername.getText().toString();
                String textPwd = editTextLoginPwd.getText().toString();

                if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(LoginActivity.this, "Please enter your email or username", Toast.LENGTH_SHORT).show();
                    editTextLoginEmailOrUsername.setError("Email or Username is required");
                    editTextLoginEmailOrUsername.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(LoginActivity.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
                    editTextLoginEmailOrUsername.setError("Valid email is required");
                    editTextLoginEmailOrUsername.requestFocus();
                } else if (TextUtils.isEmpty(textPwd)) {
                    Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    editTextLoginPwd.setError("Password is required");
                    editTextLoginPwd.requestFocus();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    loginUser(textEmail, textUsername, textPwd);
                }
            }
        });

    }

    private void loginUser(String email, String username, String pwd) {
        authProfile.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    //Get instance of the current user
                    FirebaseUser firebaseUser = authProfile.getCurrentUser();

                    //Check if email is verified before user can access their profile
                    if (firebaseUser.isEmailVerified()) {
                        Toast.makeText(LoginActivity.this, "You are logged in now", Toast.LENGTH_SHORT).show();
                        //Open User profile after successful registration
                        Intent intent = new Intent(LoginActivity.this, UserProfileActivity.class);
                        //To prevent user from returning back to signup activity on pressing back button after registration
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();  // to close Login Activity
                    } else {
                        firebaseUser.sendEmailVerification();
                        authProfile.signOut();
                        showAlertDialog();
                    }
                } else {
                    try{
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        editTextLoginEmailOrUsername.setError("User does not exist or is no longer valid. Please register again.");
                        editTextLoginEmailOrUsername.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        editTextLoginEmailOrUsername.setError("Invalid credentials. Kindly, check and re-enter.");
                        editTextLoginEmailOrUsername.requestFocus();
                    } catch (Exception e) {
                        Log.e (TAG, e.getMessage());
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void showAlertDialog() {
        //Setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Email not verified");
        builder.setMessage("Please verify your email now. You can't login without email verification.");

        //Open email apps if user click continue button
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //to email app in new window and not within our app
                startActivity(intent);
            }
        });

        //Create the AlertDialog
        AlertDialog alertDialog = builder.create();

        //Show the AlertDialog
        alertDialog.show();
    }

    //Check if user is already logged in. In such case, straightaway take the user to the user's profile
    @Override
    protected void onStart() {
        super.onStart();
        if(authProfile.getCurrentUser() != null){
            Toast.makeText(LoginActivity.this, "Already Logged In!", Toast.LENGTH_SHORT).show();

            //Start the UserProfileActivity
            startActivity(new Intent(LoginActivity.this, UserProfileActivity.class));
            finish();   //Close login activity
        } else {
            Toast.makeText(LoginActivity.this, "You can login now", Toast.LENGTH_SHORT).show();
        }
    }
}
