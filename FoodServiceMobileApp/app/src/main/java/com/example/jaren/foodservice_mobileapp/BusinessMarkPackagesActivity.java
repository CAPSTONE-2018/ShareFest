package com.example.jaren.foodservice_mobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BusinessMarkPackagesActivity extends AppCompatActivity {

    private Button Menu;
    private Button MarkGiven;
    private Button DeletePackage;
    private EditText MarkGivenID;
    private EditText DeletePackageID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_mark_packages);

        Menu = (Button) findViewById(R.id.btnMenu);
        MarkGiven = (Button) findViewById(R.id.btnMarkGiven);
        DeletePackage = (Button) findViewById(R.id.btnDeletePackage);

        MarkGivenID = (EditText) findViewById(R.id.etMarkGiven);
        DeletePackageID = (EditText) findViewById(R.id.etDeletePackage);

        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BusinessMarkPackagesActivity.this, BusinessMenuActivity.class);
                startActivity(i);
            }
        });

        MarkGiven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mark Package
                MarkGiven.setEnabled(false);
            }
        });

        DeletePackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delete Package
                DeletePackage.setEnabled(false);
            }
        });
    }
}
