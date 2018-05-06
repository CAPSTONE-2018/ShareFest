package com.example.jaren.foodservice_mobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ClientClaimPackageActivity extends AppCompatActivity {

    private Button Menu;
    private Button ClaimPackage;
    private EditText PackageID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_claim_package);

        Menu = (Button) findViewById(R.id.btnMenu);
        ClaimPackage = (Button) findViewById(R.id.btnClaimPackage);

        PackageID = (EditText) findViewById(R.id.etPackageID);

        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ClientClaimPackageActivity.this, ClientMenuActivity.class);
                startActivity(i);
            }
        });

        ClaimPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mark Claimed
                ClaimPackage.setEnabled(false);
            }
        });
    }
}
