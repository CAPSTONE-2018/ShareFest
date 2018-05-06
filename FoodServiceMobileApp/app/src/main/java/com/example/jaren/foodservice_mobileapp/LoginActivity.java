package com.example.jaren.foodservice_mobileapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.util.Log;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private Button Register;

    private int NoOfAttempts = 5;

    public String urlLogin = "http://10.0.2.2:50576/api/user/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Name = (EditText) findViewById(R.id.etName);
        Password = (EditText) findViewById(R.id.etBusinessUsername);
        Info = (TextView) findViewById(R.id.tvInfo);
        Login = (Button) findViewById(R.id.btnLogin);
        Register = (Button) findViewById(R.id.btnRegister);

        SetInfoMessage(null);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateLogin(Name.getText().toString(), Password.getText().toString());
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    private void SetInfoMessage(String extra)
    {
        String msg = "";

        if(extra != null && extra != "") {
            msg = extra + "; ";
        }

        msg += "No of attempts remaining: " + NoOfAttempts;
        Info.setText(msg);
    }

    private void ValidateLogin(String userName, String userPassword){
        Login.setEnabled(false);

        Map<String, String> params = new HashMap<>();
        params.put("username", userName);
        params.put("password", userPassword);

        HttpPostAsyncTask request = new HttpPostAsyncTask(params, new LoginCallback());
        request.execute(urlLogin);
    }

    private class LoginCallback implements HttpPostAsyncTask.Callback
    {
        public void onPostExecute(HttpPostCallbackResult result) {
            if(result.statusCode == 200)
            {
                try {
                    // FIXME: currently assuming the user is a business
                    JSONObject data = result.jsonObj.getJSONObject("data");
                    String token = data.getString("session_token");

                    SharedPreferences session = getSharedPreferences("food_service_session", MODE_PRIVATE);
                    SharedPreferences.Editor sesEdit = session.edit();
                    sesEdit.putString("token", token);
                    sesEdit.putString("user_type", "business");
                    sesEdit.apply();

                    Intent businessIntent = new Intent(LoginActivity.this, BusinessMenuActivity.class);
                    startActivity(businessIntent);
                } catch(org.json.JSONException e) {
                    Log.d("exception", e.getLocalizedMessage());
                }
            }
            else
            {
                NoOfAttempts--;

                if(result.statusCode == 401) {
                    SetInfoMessage("Invalid credentials");
                }
                else {
                    SetInfoMessage("Issue with the service");
                }
            }

            if (NoOfAttempts > 0){
                Login.setEnabled(true);
            }
        }
    }
}
