package com.example.moneymanagerapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class AccountActivity extends AppCompatActivity {

    private Toolbar settingsToolbar;
    private TextView userEmail;
    private Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        settingsToolbar = findViewById(R.id.my_Feed_Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("My Account");
        ColorDrawable colorDrawable=new ColorDrawable(Color.parseColor("#F5BD1F"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);


        logoutBtn = findViewById(R.id.logoutBtn);
        userEmail  = findViewById(R.id.userEmail);

        userEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
                builder.setTitle("Money Manager App");
                builder.setMessage("Are you sure you want to logout?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                                startActivity( intent);
                                finish();
                            }
                        });
                builder.setNegativeButton("No", null);
                AlertDialog alert = builder.create();
                alert.show();
                Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                nbutton.setTextColor(Color.BLACK);
                Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                pbutton.setTextColor(Color.BLACK);

            }
        });
    }
}