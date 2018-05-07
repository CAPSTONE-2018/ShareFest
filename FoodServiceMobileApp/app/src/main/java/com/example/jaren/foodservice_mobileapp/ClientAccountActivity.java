package com.example.jaren.foodservice_mobileapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ClientAccountActivity extends AppCompatActivity {

    private Button Menu;
    private TextView ClientUsername;
    private TextView ClientEmail;
    private TextView ClientAddress;
    private TextView ClientZip;
    private TextView ClientFirstName;
    private TextView ClientLastName;
    private TextView ClientPhone;
    private TextView ClientID;

    private String urlUserInfo = "http://10.0.2.2:50576/api/user/getinfo";

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
        ClientZip = (TextView) findViewById(R.id.tvClientZip);
        ClientID = (TextView) findViewById(R.id.tvClientID);

        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ClientAccountActivity.this, ClientMenuActivity.class);
                startActivity(i);
            }
        });

        Map<String, String> params = new HashMap<>();
        SharedPreferences session = getSharedPreferences("food_service_session", MODE_PRIVATE);
        String token = session.getString("token", "");
        HttpPostAsyncTask request = new HttpPostAsyncTask(params, new InfoCallback(), token);
        request.execute(urlUserInfo);
    }

    private class InfoCallback implements HttpPostAsyncTask.Callback {
        public void onPostExecute(HttpPostCallbackResult result) {
            if(result.statusCode == 200) {
                try {
                    JSONObject info = result.jsonObj;

                    ClientUsername.setText(info.getString("username"));
                    ClientFirstName.setText(info.getString("first_name"));
                    ClientLastName.setText(info.getString("last_name"));
                    ClientAddress.setText(info.getString("address"));
                    ClientPhone.setText(info.getString("cell_phone"));
                    ClientEmail.setText(info.getString("email"));
                    ClientZip.setText(info.getString("zip"));
                    ClientID.setText(info.getString("cid"));
                } catch (org.json.JSONException e) {
                    Log.d("exception", e.getLocalizedMessage());
                }
            } else {
                // FIXME: Make user-friendly
                Toast.makeText(
                        getApplicationContext(),
                        "Failed to get user info (" + Integer.toString(result.statusCode) + ")",
                        Toast.LENGTH_LONG
                ).show();
            }
        }
    }
}
