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
import java.util.ArrayList;
import java.util.List;

public class ClientCheckPackageActivity extends AppCompatActivity {
    private ListView PackageList;
    private Button Menu;

    private String urlGetPackages = "http://10.0.2.2:50576/api/package/getpackages";
    private String urlClaimPackage = "http://10.0.2.2:50576/api/package/claim";
    private String sessionToken;

    List<Package> packages;

    class Package {
        int pid;
        String name, businessName, businessAddress;
        boolean claimed;
        boolean modified;

        public Package(JSONObject jsonPkg) {
            try {
                pid = jsonPkg.getInt("pid");
                name = jsonPkg.getString("name");
                businessName = jsonPkg.getString("business_name");
                businessAddress = jsonPkg.getString("business_address");
                claimed = !jsonPkg.isNull("claimed");
                modified = false;
            } catch (org.json.JSONException e) {
                Log.d("exception", e.getLocalizedMessage());
            }
        }
    }

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

        sessionToken = getSharedPreferences("food_service_session", MODE_PRIVATE).
                getString("token", "");

        HttpPostAsyncTask request = new HttpPostAsyncTask(params, new GetPackagesCallback(), sessionToken);
        request.execute(urlGetPackages);
    }

    private class GetPackagesCallback implements HttpPostAsyncTask.Callback {
        public void onPostExecute(HttpPostCallbackResult result) {
            if(result.statusCode == 200) {
                try {
                    JSONArray jsonPkgs = result.jsonObj.getJSONArray("packages");
                    packages = new ArrayList<>(jsonPkgs.length());

                    for(int i = 0; i < jsonPkgs.length(); i++) {
                        packages.add(new Package(jsonPkgs.getJSONObject(i)));
                    }

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
            return packages.size();
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
            if(view == null) {
                view = getLayoutInflater().inflate(R.layout.listviewlayout, null);
            }

            final Package pkg = packages.get(i);

            TextView LVName = (TextView)view.findViewById(R.id.tvLVName);
            TextView LVDescription = (TextView)view.findViewById(R.id.tvLVDescription);

            String title = Integer.toString(pkg.pid) + " - " + pkg.name;
            String sub = pkg.businessName + " - " + pkg.businessAddress;
            LVName.setText(title);
            LVDescription.setText(sub);

            final Button btnClaim = (Button)view.findViewById(R.id.btnLVClaim);
            btnClaim.setText(pkg.claimed ? "Unclaim" : "Claim");

            /* FIXME: The client is currently prevented from unclaiming and trying to claim the
            package again. Since renotification is gradual right now, an immediate attempt to
            reclaim will fail because they don't have a notice anymore. */
            btnClaim.setEnabled(!pkg.modified || pkg.claimed);

            // FIXME: This is a hacked-together mess :^)
            btnClaim.setOnClickListener(new View.OnClickListener() {
                Button btn = btnClaim;

                public void onClick(View view) {
                    btn.setEnabled(false);

                    Map<String, String> params = new HashMap<>();
                    params.put("pid", Integer.toString(pkg.pid));
                    params.put("claim", Boolean.toString(!pkg.claimed));

                    HttpPostAsyncTask request = new HttpPostAsyncTask(params, new ClaimCallback(), sessionToken);
                    request.execute(urlClaimPackage);
                }

                class ClaimCallback implements HttpPostAsyncTask.Callback {
                    public void onPostExecute(HttpPostCallbackResult result) {
                        String msg;

                        if(result.statusCode == 200) {
                            pkg.modified = true;

                            if(pkg.claimed) {
                                msg = "Unclaimed package.";
                                pkg.claimed = false;
                            } else {
                                msg = "Claimed package.";
                                pkg.claimed = true;
                            }
                        } else {
                            // FIXME: Make user-friendly
                            msg = "Failed to " + (pkg.claimed ? "unclaim" : "claim") + " package (" + Integer.toString(result.statusCode) + ")";
                        }

                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        notifyDataSetChanged(); // getView is called again for each displayed list item
                    }
                }
            });

            return view;
        }
    }
}
