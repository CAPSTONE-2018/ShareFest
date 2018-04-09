package com.example.jaren.foodservice_mobileapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RegisterBusinessActivity extends AppCompatActivity {

public static final int CONNECTION_TIMEOUT = 10000; //10seconds
public static final int READ_TIMEOUT = 15000; // 15seconds

private URL url;
private Button BusinessRegister;
private EditText BusinessName;
private EditText BusinessAddress;
private EditText BusinessPhone;
private EditText BusinessEmail;
private EditText BusinessPassword;
private EditText BusinessZip;
private EditText BusinessUsername;
private final String UserType = "business";

public String localhost = "http://localhost:50576/api/user/register";

//WebServiceURL = new URL("http://localhost:50576/api/user/register");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BusinessRegister = (Button) findViewById(R.id.btnBusinessRegister);
        BusinessUsername = (EditText) findViewById(R.id.etBusinessUsername);
        BusinessName = (EditText) findViewById(R.id.etBusinessName);
        BusinessAddress = (EditText) findViewById(R.id.etBusinessAddress);
        BusinessPhone = (EditText) findViewById(R.id.etBusinessPhone);
        BusinessEmail = (EditText) findViewById(R.id.etBusinessEmail);
        BusinessPassword = (EditText) findViewById(R.id.etBusinessPassword);
        BusinessZip = (EditText) findViewById(R.id.etBusinessZip);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_business);


        BusinessRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = BusinessUsername.getText().toString();
                final String password = BusinessPassword.getText().toString();
                final String email = BusinessEmail.getText().toString();
                final String address = BusinessAddress.getText().toString();
                final String zip = BusinessZip.getText().toString();
                final String user_type = UserType;
                final String name = BusinessName.getText().toString();
                final String work_phone = BusinessPhone.getText().toString();

                try {
                    url = new URL(localhost);
                    String type = "application/json";
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("Content-Type", type);
                    httpURLConnection.setRequestProperty("Accept", type);
                    httpURLConnection.connect();

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("username", username);
                    jsonObject.put("password", password);
                    jsonObject.put("email", email);
                    jsonObject.put("address", address);
                    jsonObject.put("zip", zip);
                    jsonObject.put("user_type", user_type);
                    jsonObject.put("name", name);
                    jsonObject.put("work_phone", name);

                    DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                    wr.writeBytes(jsonObject.toString());
                    wr.flush();
                    wr.close();

                }catch (MalformedURLException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        });

    }

}
