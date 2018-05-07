package com.example.jaren.foodservice_mobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

public class RegisterCallback implements HttpPostAsyncTask.Callback {
    private AppCompatActivity currentActivity;
    private Button register;

    public RegisterCallback(AppCompatActivity currentActivity, Button register) {
        this.currentActivity = currentActivity;
        this.register = register;
    }

    public void onPostExecute(HttpPostCallbackResult result)
    {
        String msg;

        if(result.statusCode == 200) {
            msg = "Registration successful. Please log in.";
            Intent i = new Intent(currentActivity, LoginActivity.class);
            currentActivity.startActivity(i);
        }
        else {
            // FIXME: Need user-friendly information; is the connection bad, the username not unique?
            msg = "Error registering (" + Integer.toString(result.statusCode) + ")";
            register.setEnabled(true);
        }

        Toast.makeText(currentActivity.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
}
