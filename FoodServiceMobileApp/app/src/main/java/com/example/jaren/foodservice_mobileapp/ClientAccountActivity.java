package com.example.jaren.foodservice_mobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ClientAccountActivity extends AppCompatActivity {

    private Button Menu;
    private TextView ClientUsername;
    private TextView ClientPassword;
    private TextView ClientEmail;
    private TextView ClientAddress;
    private TextView ClientZip;
    private TextView ClientFirstName;
    private TextView ClientLastName;
    private TextView ClientPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_account);

        Menu = (Button) findViewById(R.id.btnMenu);

        ClientUsername = (TextView) findViewById(R.id.tvClientUsername);
        ClientFirstName = (TextView) findViewById(R.id.tvClientFirstName);
        ClientLastName = (TextView) findViewById(R.id.tvClientLastName);
        ClientAddress = (TextView) findViewById(R.id.tvClientAddress);
        ClientPhone = (TextView) findViewById(R.id.tvClientPhone);
        ClientEmail = (TextView) findViewById(R.id.tvClientEmail);
        ClientPassword = (TextView) findViewById(R.id.tvClientPassword);
        ClientZip = (TextView) findViewById(R.id.tvClientZip);

        SampleValues();

        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ClientAccountActivity.this, ClientMenuActivity.class);
                startActivity(i);
            }
        });
    }

    public void SampleValues(){
        ClientUsername.setText("client1");
        ClientFirstName.setText("John");
        ClientLastName.setText("Smith");
        ClientAddress.setText("123 Sample Lane");
        ClientPhone.setText("(555)630-5555");
        ClientEmail.setText("johnsmith@sample.com");
        ClientPassword.setText("password123");
        ClientZip.setText("60504");
    }
}
