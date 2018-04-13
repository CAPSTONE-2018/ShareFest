package com.example.jaren.foodservice_mobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class RegisterActivity extends AppCompatActivity {

    private Button bregis;
    private RadioGroup RG;
    private RadioButton RBBusiness;
    private RadioButton RBClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        bregis = (Button) findViewById(R.id.btnNext);
        RG = (RadioGroup) findViewById(R.id.radioGroupRegister);
        RBBusiness = (RadioButton) findViewById(R.id.rbtnBusiness);
        RBClient = (RadioButton) findViewById(R.id.rbtnClient);


        bregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (RBBusiness.isChecked()) {
                    Intent registerBusinessIntent = new Intent(RegisterActivity.this, RegisterBusinessActivity.class);
                    startActivity(registerBusinessIntent);
                }

                if (RBClient.isChecked())
                {Intent registerClientIntent = new Intent(RegisterActivity.this, RegisterClientActivity.class);
                    startActivity(registerClientIntent);

                }



            }
        });

    }


}
