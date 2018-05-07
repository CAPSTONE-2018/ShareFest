package com.example.jaren.foodservice_mobileapp;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;


public class RegisterBusinessActivity extends AppCompatActivity {

public static final int CONNECTION_TIMEOUT = 10000; //10seconds
public static final int READ_TIMEOUT = 15000; // 15seconds

private Button BusinessRegister;
private Button BusinessLogin;
private EditText BusinessName;
private EditText BusinessAddress;
private EditText BusinessPhone;
private EditText BusinessEmail;
private EditText BusinessPassword;
private EditText BusinessZip;
private EditText BusinessUsername;
private EditText BusinessInstructions;
private final String UserType = "business";

private String username;
private String password;
private String email;
private String address;
private String zip;
private String user_type;
private String name;
private String work_phone;
private String instructions;

public String localhost = "http://10.0.2.2:50576/api/user/register";

private class RegisterCallback implements HttpPostAsyncTask.Callback {
    public void onPostExecute(HttpPostCallbackResult result)
    {
        // FIXME: notify user of success/failure, redirect to login, etc.
        if(result.statusCode == 200)
            BusinessName.setText("registered :^)");
        else
            BusinessName.setText("fail: " + Integer.toString(result.statusCode));
    }
}

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
        BusinessPassword = (EditText) findViewById(R.id.etBusinessPassword);
        BusinessZip = (EditText) findViewById(R.id.etBusinessZip);
        BusinessInstructions = (EditText) findViewById(R.id.etBusinessInstructions);


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
                instructions = BusinessInstructions.getText().toString();

                Map<String, String> postData = new HashMap<>();
                postData.put("username", username);
                postData.put("password", password);
                postData.put("email", email);
                postData.put("address", address);
                postData.put("zip", zip);
                postData.put("user_type", user_type);
                postData.put("name", name);
                postData.put("work_phone", work_phone);
                postData.put("instructions", instructions);

                HttpPostAsyncTask task = new HttpPostAsyncTask(postData, new RegisterCallback());
                task.execute(localhost);
                Log.d("RegisBusinessActivity", "sent");
            }
        });

    }
}
