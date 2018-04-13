package com.example.jaren.foodservice_mobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class RegisterBusinessActivity extends AppCompatActivity {

public static final int CONNECTION_TIMEOUT = 10000; //10seconds
public static final int READ_TIMEOUT = 15000; // 15seconds

private URL url;
private Button BusinessRegister;
private Button BusinessLogin;
private EditText BusinessName;
private EditText BusinessAddress;
private EditText BusinessPhone;
private EditText BusinessEmail;
private EditText BusinessPassword;
private EditText BusinessZip;
private EditText BusinessUsername;
private final String UserType = "business";

private String username;
private String password;
private String email;
private String address;
private String zip;
private String user_type;
private String name;
private String work_phone;

public String localhost = "http://localhost:50576/api/user/register";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_business);

        BusinessRegister = (Button) findViewById(R.id.btnBusinessRegister);
        BusinessLogin = (Button) findViewById(R.id.btnLogin);

        BusinessUsername = (EditText) findViewById(R.id.etBusinessUsername);
        BusinessName = (EditText) findViewById(R.id.etBusinessName);
        BusinessAddress = (EditText) findViewById(R.id.etBusinessAddress);
        BusinessPhone = (EditText) findViewById(R.id.etBusinessPhone);
        BusinessEmail = (EditText) findViewById(R.id.etBusinessEmail);
        BusinessPassword = (EditText) findViewById(R.id.etBusinessUsername);
        BusinessZip = (EditText) findViewById(R.id.etBusinessZip);


        BusinessLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterBusinessActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        BusinessRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRegistrationPost();
                BusinessRegister.setEnabled(false);
            }

            private void sendRegistrationPost(){
                username = BusinessUsername.getText().toString();
                password = BusinessPassword.getText().toString();
                email = BusinessEmail.getText().toString();
                address = BusinessAddress.getText().toString();
                zip = BusinessZip.getText().toString();
                user_type = UserType;
                name = BusinessName.getText().toString();
                work_phone = BusinessPhone.getText().toString();

                Map<String, String> postData = new HashMap<>();
                postData.put("username", username);
                postData.put("password", password);
                postData.put("email", email);
                postData.put("address", address);
                postData.put("zip", zip);
                postData.put("user_type", user_type);
                postData.put("name", name);
                postData.put("work_phone", name);

                HttpPostAsyncTask task = new HttpPostAsyncTask(postData);
                task.execute(localhost);
            }
        });

    }



}
