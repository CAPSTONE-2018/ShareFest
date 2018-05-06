package com.example.jaren.foodservice_mobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BusinessCreatePackageActivity extends AppCompatActivity {

    private Button Menu;
    private Button CreatePackage;
    private EditText PackageName;
    private EditText PackageDescription;
    private EditText PackageQuantity;
    private EditText PackagePrice;
    private EditText PackageExpiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_create_package);

        Menu = (Button) findViewById(R.id.btnMenu);
        CreatePackage = (Button) findViewById(R.id.btnCreatePackage);

        PackageName = (EditText) findViewById(R.id.etPackageName);
        PackageDescription = (EditText) findViewById(R.id.etPackageDescription);
        PackageQuantity = (EditText) findViewById(R.id.etPackageQuantity);
        PackagePrice = (EditText) findViewById(R.id.etPackagePrice);
        PackageExpiry = (EditText) findViewById(R.id.etPackageExpiry);

        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BusinessCreatePackageActivity.this, BusinessMenuActivity.class);
                startActivity(i);
            }
        });

        CreatePackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send Package
                CreatePackage.setEnabled(false);
            }
        });
    }
}
