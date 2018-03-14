package com.example.farmina.sms_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private Button Login;
    private Button Register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText)findViewById(R.id.etUserName);
        Password = (EditText)findViewById(R.id.etPassword);
        Login = (Button)findViewById(R.id.btnLogin);
        Register =(Button)findViewById(R.id.btnRegister);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login(Name.getText().toString(), Password.getText().toString());
                //clear up the edit text field
                Name.setText("");
                Password.setText("");

            }
        });


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register(Name.getText().toString(), Password.getText().toString());

            }
        });

    }

    private void Login(String userName, String userPassword){
        if((userName.equals("Farmina")) && (userPassword.equals("1234"))){
            Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
            startActivity(intent);
        }
        else{
            // do nothing

            }
        }


        private  void Register(String userName, String userPassword)
        {
            if(userName.equals("") && (userPassword).equals(""))
            {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);

            }
            else
            {
                //do nothing
            }


        }
}



