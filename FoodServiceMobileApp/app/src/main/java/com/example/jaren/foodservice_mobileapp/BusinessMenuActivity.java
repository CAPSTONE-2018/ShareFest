package com.example.jaren.foodservice_mobileapp;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

public class BusinessMenuActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_menu);

        mToolbar = (Toolbar)findViewById(R.id.businessnav_action);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nv = (NavigationView)findViewById(R.id.nv1);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_businessaccount:
                        Intent BusinessAccountIntent = new Intent(getApplicationContext(),BusinessAccountActivity.class);
                        startActivity(BusinessAccountIntent);
                        return true;
                    case R.id.nav_createpackage:
                        Intent BusinessCreatePackageIntent = new Intent(getApplicationContext(), BusinessCreatePackageActivity.class);
                        startActivity(BusinessCreatePackageIntent);
                        return true;
                    case R.id.nav_businessclaimablepackages:
                        Intent BusinessClaimablePackagesIntent = new Intent(getApplicationContext(), BusinessClaimablePackagesActivity.class);
                        startActivity(BusinessClaimablePackagesIntent);
                        return true;
                    case R.id.nav_logout:
                        Intent LogoutIntent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(LogoutIntent);
                        return true;
                }
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
