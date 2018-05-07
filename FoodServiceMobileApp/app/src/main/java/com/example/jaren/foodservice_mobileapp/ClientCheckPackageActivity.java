package com.example.jaren.foodservice_mobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ClientCheckPackageActivity extends AppCompatActivity {
    private ListView PackageList;
    private Button Menu;

    private String urlGetPackages = "http://10.0.2.2:50576/api/package/getpackages";
    JSONArray packages; // FIXME: A Package class might be appropriate, but this works for now

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_check_package);

        Menu = (Button) findViewById(R.id.btnMenu);
        PackageList = (ListView)findViewById(R.id.lvPackageList);

        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ClientCheckPackageActivity.this, ClientMenuActivity.class);
                startActivity(i);
            }
        });

        Map<String, String> params = new HashMap<>();
        params.put("only_eligible", "true");

        String token = getSharedPreferences("food_service_session", MODE_PRIVATE).
                getString("token", "");

        HttpPostAsyncTask request = new HttpPostAsyncTask(params, new GetPackagesCallback(), token);
        request.execute(urlGetPackages);
    }

    private class GetPackagesCallback implements HttpPostAsyncTask.Callback {
        public void onPostExecute(HttpPostCallbackResult result) {
            if(result.statusCode == 200) {
                try {
                    packages = result.jsonObj.getJSONArray("packages");
                    CustomAdapter ca = new CustomAdapter();
                    PackageList.setAdapter(ca);
                    return;
                } catch(org.json.JSONException e) {
                    Log.d("exception", e.getLocalizedMessage());
                }
            }

            // FIXME: Make user-friendly
            Toast.makeText(
                    getApplicationContext(),
                    "Failed to get packages (" + Integer.toString(result.statusCode) + ")",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return packages.length();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.listviewlayout, null);

            ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
            TextView LVName = (TextView)view.findViewById(R.id.tvLVName);
            TextView LVDescription = (TextView)view.findViewById(R.id.tvLVDescription);

            imageView.setImageResource(R.mipmap.ic_launcher);

            try {
                JSONObject pck = packages.getJSONObject(i);

                String title = pck.getString("pid") + " - " + pck.getString("name");
                String sub = pck.getString("business_name") + " - " + pck.getString("business_address");

                LVName.setText(title);
                LVDescription.setText(sub);
            } catch (org.json.JSONException e) {
                Log.d("exception", e.getLocalizedMessage());
            }

            return view;
        }
    }
}
