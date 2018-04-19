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

public class ClientMenuActivity extends AppCompatActivity {

    private DrawerLayout CmDrawerLayout;
    private ActionBarDrawerToggle CmToggle;
    private Toolbar CmToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_menu);

        CmToolbar = (Toolbar)findViewById(R.id.clientnav_action);
        setSupportActionBar(CmToolbar);

        CmDrawerLayout = (DrawerLayout) findViewById(R.id.CdrawerLayout);
        CmToggle = new ActionBarDrawerToggle(this, CmDrawerLayout, R.string.open, R.string.close);

        CmDrawerLayout.addDrawerListener(CmToggle);
        CmToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView Cnv = (NavigationView)findViewById(R.id.nv2);

        Cnv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    // MENU ITEMS
                    case R.id.nav_clientaccount:
                        Intent ClientAccountIntent = new Intent(getApplicationContext(),ClientAccountActivity.class);
                        startActivity(ClientAccountIntent);
                        return true;
                    case R.id.nav_clientclaimablepackages:
                        Intent ClientCheckPackageIntent = new Intent(getApplicationContext(), ClientCheckPackageActivity.class);
                        startActivity(ClientCheckPackageIntent);
                        return true;
                    case R.id.nav_claimpacakage:
                        Intent ClientClaimPackageIntent = new Intent(getApplicationContext(), ClientClaimPackageActivity.class);
                        startActivity(ClientClaimPackageIntent);
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

        if(CmToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
