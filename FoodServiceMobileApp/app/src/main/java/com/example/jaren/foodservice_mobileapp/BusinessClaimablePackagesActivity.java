package com.example.jaren.foodservice_mobileapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class BusinessClaimablePackagesActivity extends AppCompatActivity {

    private ListView PackageList;

    private String[] SamplePackages = {"Marianos Package", "Jewel Osco Package", "Sam's Grocer Package", "Oriental Foods Package"};
    private String[] SampleDescriptions = {"Salads", "Breads", "Fruits", "Candy"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_claimable_packages);

        PackageList = (ListView)findViewById(R.id.lvPackageList);
    }

    class CustomAdapter extends BaseAdapter{
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
            return null;
        }
    }
}
