package com.example.sisterlyapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignupActivity extends AppCompatActivity {

    private EditText editTextRegisterFullName, editTextRegisterUsername, editTextRegisterEmail, editTextRegisterPwd,
                     editTextRegisterMobile;
    private ProgressBar progressBar;
    private RadioButton radioButtonAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

//        getSupportActionBar().setTitle("Register");

        Toast.makeText(SignupActivity.this, "You can register now", Toast.LENGTH_LONG).show();

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
    }
}
