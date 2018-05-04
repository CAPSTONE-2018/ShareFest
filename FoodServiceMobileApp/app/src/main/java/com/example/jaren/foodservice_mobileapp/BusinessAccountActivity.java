package com.example.jaren.foodservice_mobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BusinessAccountActivity extends AppCompatActivity {


    private Button Menu;
    private TextView BusinessUsername;
    private TextView BusinessPassword;
    private TextView BusinessEmail;
    private TextView BusinessAddress;
    private TextView BusinessZip;
    private TextView BusinessName;
    private TextView BusinessPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_account);

        Menu = (Button) findViewById(R.id.btnMenu);

        BusinessUsername = (TextView) findViewById(R.id.tvBusinessUsername);
        BusinessName = (TextView) findViewById(R.id.tvBusinessName);
        BusinessAddress = (TextView) findViewById(R.id.tvBusinessAddress);
        BusinessPhone = (TextView) findViewById(R.id.tvBusinessPhone);
        BusinessEmail = (TextView) findViewById(R.id.tvBusinessEmail);
        BusinessPassword = (TextView) findViewById(R.id.tvBusinessPassword);
        BusinessZip = (TextView) findViewById(R.id.tvBusinessZip);

        SampleValues();

        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BusinessAccountActivity.this, BusinessMenuActivity.class);
                startActivity(i);
            }
        });
    }

    public void SampleValues(){
        BusinessUsername.setText("jewelosco1");
        BusinessName.setText("Jewel Osco");
        BusinessAddress.setText("123 Sample Lane");
        BusinessPhone.setText("(555)630-5555");
        BusinessEmail.setText("jewelosco@sample.com");
        BusinessPassword.setText("password123");
        BusinessZip.setText("60504");
    }
}
