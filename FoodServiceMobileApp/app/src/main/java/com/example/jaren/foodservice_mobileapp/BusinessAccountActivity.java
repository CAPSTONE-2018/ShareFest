package com.example.jaren.foodservice_mobileapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class BusinessAccountActivity extends AppCompatActivity {


    private Button Menu;
    private TextView BusinessUsername;
    private TextView BusinessEmail;
    private TextView BusinessAddress;
    private TextView BusinessZip;
    private TextView BusinessName;
    private TextView BusinessPhone;

    private String urlUserInfo = "http://10.0.2.2:50576/api/user/getinfo";

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
        BusinessZip = (TextView) findViewById(R.id.tvBusinessZip);

        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BusinessAccountActivity.this, BusinessMenuActivity.class);
                startActivity(i);
            }
        });

        Map<String, String> params = new HashMap<>();
        SharedPreferences session = getSharedPreferences("food_service_session", MODE_PRIVATE);
        String token = session.getString("token", "");
        HttpPostAsyncTask request = new HttpPostAsyncTask(params, new InfoCallback(), token);
        request.execute(urlUserInfo);
    }

    private class InfoCallback implements HttpPostAsyncTask.Callback
    {
        public void onPostExecute(HttpPostCallbackResult result) {
            if(result.statusCode == 200)
            {
                try
                {
                    JSONObject info = result.jsonObj;

                    BusinessUsername.setText(info.getString("username"));
                    BusinessName.setText(info.getString("name"));
                    BusinessAddress.setText(info.getString("address"));
                    BusinessPhone.setText(info.getString("work_phone"));
                    BusinessEmail.setText(info.getString("email"));
                    BusinessZip.setText(info.getString("zip"));
                } catch (org.json.JSONException e)
                {
                    Log.d("exception", e.getLocalizedMessage());
                    // FIXME: implement invalid-response action
                }
            }
            else if(result.statusCode == 401)
            {
                // FIXME: implement unauthorized action (bad/expired session token)
            }
            else if(result. statusCode == 403)
            {
                // FIXME: implement forbidden action (session is OK, user is not allowed)
            }
        }
    }
}
