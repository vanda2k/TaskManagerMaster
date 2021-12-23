package com.example.taskmanagermaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskmanagermaster.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    EditText input_name, input_email, input_password, input_confirm_password;
    FirebaseAuth fAuthu;
    FirebaseDatabase db;
    DatabaseReference userRef;

    Button registerBtn;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth  mAuth;
    FirebaseUser  mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        input_name = findViewById(R.id.register_fullname);
        input_email = findViewById(R.id.register_email);
        input_password = findViewById(R.id.register_password);
        input_confirm_password = findViewById(R.id.register_confirm_password);

        registerBtn = findViewById(R.id.register_btn);
        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance();
        userRef = db.getReference("User");

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();
            }
        });

    }

    private void PerforAuth() {
        String email = input_email.getText().toString();
        String password = input_password.getText().toString();
        String confirmPassword = input_confirm_password.getText().toString();

        if (!email.matches(emailPattern))
        {
            input_email.setError("Enter connect Email!");
            Toast.makeText(RegisterActivity.this, "Enter Connect Email", Toast.LENGTH_LONG).show();
        } else if (password.isEmpty() || password.length() < 6)
        {
            input_password.setError("Enter Proper Password!");
            Toast.makeText(RegisterActivity.this, "Enter Proper Password", Toast.LENGTH_LONG).show();
        } else if (!password.equals(confirmPassword))
        {
            input_confirm_password.setError("Password not match");
            Toast.makeText(RegisterActivity.this, "Password not match", Toast.LENGTH_LONG).show();
        } else
        {
            progressDialog.setMessage("Please Wait While Registration ...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        // save data
                        User user = new User();
                        user.setEmail(input_email.getText().toString());
                        user.setFullname(input_name.getText().toString());
                        user.setPassword(input_password.getText().toString());
                        user.setConfirm(input_confirm_password.getText().toString());

                        userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(RegisterActivity.this,"Register Successfull!", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterActivity.this,"Register Failed!", Toast.LENGTH_SHORT).show();
                            }
                        });


                        sendUserToNextActivity();
                        Toast.makeText(RegisterActivity.this, "Register Successful!", Toast.LENGTH_SHORT).show();
                    } else
                    {
                        Toast.makeText(RegisterActivity.this, "Error !"+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}