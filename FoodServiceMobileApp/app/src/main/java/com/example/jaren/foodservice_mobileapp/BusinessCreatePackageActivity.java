package com.example.jaren.foodservice_mobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;

public class BusinessCreatePackageActivity extends AppCompatActivity {

    private Button Menu;
    private Button CreatePackage;
    private EditText PackageName;
    private EditText PackageDescription;
    private EditText PackageQuantity;
    private EditText PackagePrice;
    private EditText PackageExpiry;

    private String urlCreatePackage = "http://10.0.2.2:50576/api/package/createpackage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_create_package);

        Menu = (Button) findViewById(R.id.btnMenu);
        CreatePackage = (Button) findViewById(R.id.btnCreatePackage);

        PackageName = (EditText) findViewById(R.id.etPackageName);
        PackageDescription = (EditText) findViewById(R.id.etPackageDescription);
        PackageQuantity = (EditText) findViewById(R.id.etPackageQuantity);
        PackagePrice = (EditText) findViewById(R.id.etPackagePrice);
        PackageExpiry = (EditText) findViewById(R.id.etPackageExpiry);

        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BusinessCreatePackageActivity.this, BusinessMenuActivity.class);
                startActivity(i);
            }
        });

        CreatePackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send Package
                CreatePackage.setEnabled(false);

                Map<String, String> params = new HashMap<>();
                params.put("name", PackageName.getText().toString());
                putIfNotEmpty(params, "description", PackageDescription.getText().toString());

                /* FIXME: The database doesn't treat quantity as a number (e.g. business might want
                to specify units), so PackageQuantity should probably not be limited to integers
                only */
                putIfNotEmpty(params, "quantity", PackageQuantity.getText().toString());

                params.put("price", PackagePrice.getText().toString());

                // FIXME: Limit PackageExpiry to date format
                // FIXME: Convert local time to UTC
                putIfNotEmpty(params, "expires", PackageExpiry.getText().toString());

                String token = getSharedPreferences("food_service_session", MODE_PRIVATE).
                        getString("token", "");

                HttpPostAsyncTask request = new HttpPostAsyncTask(params, new CreatePackageCallback(), token);
                request.execute(urlCreatePackage);
            }
        });
    }

    private String putIfNotEmpty(Map<String, String> map, String key, String value) {
        // FIXME: Ignore white space
        if(!value.equals("")) {
            return map.put(key, value);
        }

        return null;
    }

    private class CreatePackageCallback implements HttpPostAsyncTask.Callback {
        public void onPostExecute(HttpPostCallbackResult result) {
            String msg;

            if(result.statusCode == 200) {
                msg = "Created package.";

                PackageName.getText().clear();
                PackageDescription.getText().clear();
                PackageQuantity.getText().clear();
                PackagePrice.getText().clear();
                PackageExpiry.getText().clear();
            } else {
                // FIXME: Make user-friendly
                msg = "Creation failed (" + Integer.toString(result.statusCode) + ")";
            }

            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            CreatePackage.setEnabled(true);
        }
    }
}
