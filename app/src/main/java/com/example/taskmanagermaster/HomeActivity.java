package com.example.taskmanagermaster;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    TextView txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        txtName = findViewById(R.id.txtName);
        txtName.setText(GlobalVar.currentUser.getFullname());

    }
}