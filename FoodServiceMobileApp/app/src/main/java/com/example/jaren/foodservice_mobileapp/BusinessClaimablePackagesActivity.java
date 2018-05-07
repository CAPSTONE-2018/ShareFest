package com.example.jaren.foodservice_mobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusinessClaimablePackagesActivity extends AppCompatActivity {

    private ListView PackageList;
    private Button Menu;

    private String urlGetPackages = "http://10.0.2.2:50576/api/package/getpackages";
    private String urlMarkReceived = "http://10.0.2.2:50576/api/package/markreceived";
    private String urlDeletePackage = "http://10.0.2.2:50576/api/package/deletepackage";
    private String sessionToken;

    List<Package> packages;

    class Package {
        int pid, claimerCID;
        String name;
        boolean claimed, received, deleted;

        public Package(JSONObject jsonPkg) {
            try {
                pid = jsonPkg.getInt("pid");
                claimerCID = jsonPkg.optInt("claimer_cid", 0);
                name = jsonPkg.getString("name");
                claimed = !jsonPkg.isNull("claimed");
                received = false;
                deleted = false;
            } catch (org.json.JSONException e) {
                Log.d("exception", e.getLocalizedMessage());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_claimable_packages);

        Menu = (Button) findViewById(R.id.btnMenu);
        PackageList = (ListView)findViewById(R.id.lvBusinessPackageList);

        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BusinessClaimablePackagesActivity.this, BusinessMenuActivity.class);
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

    /* FIXME: Copy and pasted from ClientCheckPackageActivity; find a way to make generic to reduce
    redundancy */
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
                view = getLayoutInflater().inflate(R.layout.listview_business_package, null);
            }

            final Package pkg = packages.get(i);

            TextView LVName = (TextView)view.findViewById(R.id.tvLVName);
            TextView LVDescription = (TextView)view.findViewById(R.id.tvLVDescription);

            String title = Integer.toString(pkg.pid) + " - " + pkg.name;
            LVName.setText(title);

            String sub;

            if(pkg.deleted)
                sub = "Deleted";
            else if(pkg.received)
                sub = "Received";
            else if(pkg.claimed) {
                sub = "Claimed by Client ID: " + Integer.toString(pkg.claimerCID);
            } else
                sub = "Not Claimed";

            LVDescription.setText(sub);

            final Button btnReceive = (Button)view.findViewById(R.id.btnLVReceive);
            btnReceive.setEnabled(pkg.claimed && !pkg.deleted && !pkg.received);

            final Button btnDelete = (Button)view.findViewById(R.id.btnLVDelete);
            btnDelete.setEnabled(!pkg.deleted && !pkg.received);

            btnReceive.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    btnReceive.setEnabled(false);
                    btnDelete.setEnabled(false);

                    Map<String, String> params = new HashMap<>();
                    params.put("pid", Integer.toString(pkg.pid));

                    HttpPostAsyncTask request = new HttpPostAsyncTask(params, new ReceiveCallback(), sessionToken);
                    request.execute(urlMarkReceived);
                }

                class ReceiveCallback implements HttpPostAsyncTask.Callback {
                    public void onPostExecute(HttpPostCallbackResult result) {
                        String msg;

                        if(result.statusCode == 200) {
                            pkg.received = true;
                            msg = "Package marked 'received.'";
                        } else {
                            // FIXME: Make user-friendly
                            msg = "Failed to mark package (" + Integer.toString(result.statusCode) + ")";
                        }

                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        notifyDataSetChanged();
                    }
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    btnDelete.setEnabled(false);
                    btnReceive.setEnabled(false);

                    Map<String, String> params = new HashMap<>();
                    params.put("pid", Integer.toString(pkg.pid));

                    HttpPostAsyncTask request = new HttpPostAsyncTask(params, new DeleteCallback(), sessionToken);
                    request.execute(urlDeletePackage);
                }

                class DeleteCallback implements HttpPostAsyncTask.Callback {
                    public void onPostExecute(HttpPostCallbackResult result) {
                        String msg;

                        if(result.statusCode == 200) {
                            pkg.deleted = true;
                            msg = "Package deleted.";
                        } else {
                            // FIXME: Make user-friendly
                            msg = "Failed to delete package (" + Integer.toString(result.statusCode) + ")";
                        }

                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        notifyDataSetChanged();
                    }
                }
            });

            return view;
        }
    }
}
