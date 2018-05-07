package com.example.jaren.foodservice_mobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ClientCheckPackageActivity extends AppCompatActivity {
    private ListView PackageList;
    private Button Menu;

    private int[] SampleImages = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher };
    private String[] SamplePackages = {"Marianos", "Jewel Osco", "Sam's Grocer", "Oriental Foods"};
    private String[] SampleDescriptions = {"PID1 - Salads", "PID2 - Breads", "PID3 - Fruits", "PID4 - Candy"};

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

        CustomAdapter ca = new CustomAdapter();
        PackageList.setAdapter(ca);
    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return SamplePackages.length;
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

            // Sample Data
            imageView.setImageResource(SampleImages[i]);
            LVName.setText(SamplePackages[i]);
            LVDescription.setText(SampleDescriptions[i]);

            return view;
        }
    }
}
