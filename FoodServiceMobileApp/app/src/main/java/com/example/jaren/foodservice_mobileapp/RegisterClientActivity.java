package com.example.jaren.foodservice_mobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class RegisterClientActivity extends AppCompatActivity {

    public static final int CONNECTION_TIMEOUT = 10000; //10seconds
    public static final int READ_TIMEOUT = 15000; // 15seconds

    private Button ClientRegister;
    private Button ClientLogin;
    private EditText ClientFirstName;
    private EditText ClientLastName;
    private EditText ClientPhone;
    private EditText ClientUsername;
    private EditText ClientPassword;
    private EditText ClientEmail;
    private EditText ClientAddress;
    private EditText ClientZip;
    private final String UserType = "client";

    private String username;
    private String password;
    private String email;
    private String address;
    private String zip;
    private String user_type;
    private String first_name;
    private String last_name;
    private String cell_phone;

    // Currently all clients are paying
    boolean paying = true;

    public String localhost = "http://10.0.2.2:50576/api/user/register";

    private class RegisterCallback implements HttpPostAsyncTask.Callback {
        public void onPostExecute(HttpPostCallbackResult result)
        {
            // FIXME: notify user of success/failure, redirect to login, etc.
            if(result.statusCode == 200)
                ClientUsername.setText("registered :^)");
            else
                ClientUsername.setText("fail: " + Integer.toString(result.statusCode));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client);

        ClientRegister = (Button) findViewById(R.id.btnClientRegister);
        ClientLogin = (Button) findViewById(R.id.btnLogin);

        ClientFirstName = (EditText) findViewById(R.id.etClientFirstName);
        ClientLastName = (EditText) findViewById(R.id.etClientLastName);
        ClientPhone = (EditText) findViewById(R.id.etClientPhone);
        ClientUsername = (EditText) findViewById(R.id.etClientUsername);
        ClientPassword = (EditText) findViewById(R.id.etClientPassword);
        ClientEmail = (EditText) findViewById(R.id.etClientEmail);
        ClientAddress = (EditText) findViewById(R.id.etClientAddress);
        ClientZip = (EditText) findViewById(R.id.etClientZip);

        ClientLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterClientActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        ClientRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRegistrationPost();
                ClientRegister.setEnabled(false);
            }

            private void sendRegistrationPost(){
                username = ClientUsername.getText().toString();
                password = ClientPassword.getText().toString();
                email = ClientEmail.getText().toString();
                address = ClientAddress.getText().toString();
                zip = ClientZip.getText().toString();
                user_type = UserType;
                first_name = ClientFirstName.getText().toString();
                last_name = ClientLastName.getText().toString();
                cell_phone = ClientPassword.getText().toString();
                paying = true;

                Map<String, String> postData = new HashMap<>();
                postData.put("username", username);
                postData.put("password", password);
                postData.put("email", email);
                postData.put("address", address);
                postData.put("zip", zip);
                postData.put("user_type", user_type);
                postData.put("first_name", first_name);
                postData.put("last_name", last_name);
                postData.put("cell_phone", cell_phone);

                // This gives error passing 'paying' value as a boolean instead of string
                postData.put("paying", "true");

                HttpPostAsyncTask task = new HttpPostAsyncTask(postData, new RegisterCallback());
                task.execute(localhost);
                Log.d("RegisClientActivity", "sent");
            }
        });

    }
}
