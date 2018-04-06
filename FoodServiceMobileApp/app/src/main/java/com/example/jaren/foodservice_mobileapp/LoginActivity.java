package com.example.jaren.foodservice_mobileapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private Button Register;


    private int NoOfAttempts = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Name = (EditText) findViewById(R.id.etName);
        Password = (EditText) findViewById(R.id.etPassword);
        Info = (TextView) findViewById(R.id.tvInfo);
        Login = (Button) findViewById(R.id.btnLogin);
        Register = (Button) findViewById(R.id.btnRegister);



        Info.setText("No of attempts remaining: " + NoOfAttempts);

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

        //that causes the problem....
        //==============================================================

//        BusinessRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent BusinessRegisterIntent = new Intent(LoginActivity.this, RegisterBusinessActivity.class);
//                startActivity(BusinessRegisterIntent);
//            }
//        });

        //==============================================================

    }


    private void ValidateLogin(String userName, String userPassword){
        if ((userName.equals("Admin")) && (userPassword.equals("1234"))){
            Intent businessIntent = new Intent(LoginActivity.this, BusinessMenuActivity.class);
            startActivity(businessIntent);
        }
        else if ((userName.equals("Client")) && (userPassword.equals("1234"))) {
            Intent clientIntent = new Intent(LoginActivity.this, ClientMenuActivity.class);
            startActivity(clientIntent);
        }
        else{
            NoOfAttempts--;

            Info.setText("No of attempts remaining: " + String.valueOf(NoOfAttempts));

            if (NoOfAttempts == 0){
                Login.setEnabled(false);
            }
        }

    }
}
