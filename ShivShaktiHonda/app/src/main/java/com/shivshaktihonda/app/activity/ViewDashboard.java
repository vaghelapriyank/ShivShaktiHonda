package com.shivshaktihonda.app.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.shivshaktihonda.app.R;
import com.shivshaktihonda.app.fragment.FragAdminDashboard;
import com.shivshaktihonda.app.fragment.FragAllSalesPerson;
import com.shivshaktihonda.app.fragment.FragBookingList;
import com.shivshaktihonda.app.fragment.FragChangePassword;
import com.shivshaktihonda.app.fragment.FragDeliveryList;
import com.shivshaktihonda.app.fragment.FragInquiryList;
import com.shivshaktihonda.app.fragment.FragPersonDashboard;
import com.shivshaktihonda.app.fragment.FragPriceList;
import com.shivshaktihonda.app.fragment.FragSummaryList;
import com.shivshaktihonda.app.utils.CM;
import com.shivshaktihonda.app.utils.CV;

public class ViewDashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtUserName;
    MenuItem menuSalesPerson,menuSummary,menuChangePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED ) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE , Manifest.permission.READ_EXTERNAL_STORAGE},
                        1001);
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        txtUserName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.header_txtUserName);
        txtUserName.setText(CM.getSp(ViewDashboard.this,CV.USER_NAME,"").toString());
        navigationView.setNavigationItemSelectedListener(this);
        menuSalesPerson=navigationView.getMenu().findItem(R.id.nav_salesPerson);
        menuSummary=navigationView.getMenu().findItem(R.id.nav_summary);
        menuChangePassword=navigationView.getMenu().findItem(R.id.nav_changepassword);
        if ((boolean) CM.getSp(ViewDashboard.this, CV.IS_ADMIN,false)){
            menuSalesPerson.setVisible(true);
            menuSummary.setVisible(true);
            menuChangePassword.setVisible(false);
            getSupportFragmentManager().beginTransaction().add(R.id.container,new FragAdminDashboard()).commit();
        }else{
            menuChangePassword.setVisible(true);
            menuSalesPerson.setVisible(false);
            menuSummary.setVisible(false);
            getSupportFragmentManager().beginTransaction().add(R.id.container,new FragPersonDashboard()).commit();
        }


    }

    public void setToolbarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.view_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            if ((boolean) CM.getSp(ViewDashboard.this, CV.IS_ADMIN,false)){
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new FragAdminDashboard()).commit();
            }else{
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new FragPersonDashboard()).commit();
            }
//            getSupportFragmentManager().beginTransaction().replace(R.id.container,new FragPersonDashboard()).commit();
        } else if (id == R.id.nav_salesPerson) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new FragAllSalesPerson()).commit();
        } else if (id == R.id.nav_inquiry) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new FragInquiryList()).commit();
        } else if (id == R.id.nav_booking) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new FragBookingList()).commit();
        } else if (id == R.id.nav_delivery) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new FragDeliveryList()).commit();
        } else if (id == R.id.nav_changepassword) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new FragChangePassword()   ).commit();
        } else if (id == R.id.nav_summary) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new FragSummaryList()   ).commit();
        } else if (id == R.id.nav_pricelist) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new FragPriceList()   ).commit();
        } else if (id == R.id.nav_logout) {
            showLogoutDialog();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewDashboard.this);
        builder.setMessage(getResources().getString(R.string.logout_msg))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.common_yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CM.clearSessionSp(ViewDashboard.this);
                        Intent intent=new Intent(ViewDashboard.this,ViewLogin.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        CM.startActivity(intent,ViewDashboard.this);
                        ViewDashboard.this.finish();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.common_no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment= getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment instanceof FragInquiryList){
            if (requestCode==1500 && resultCode==RESULT_OK){
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new FragBookingList()).commit();
            }else{
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }else if (fragment instanceof FragPriceList){
            fragment.onActivityResult(requestCode, resultCode, data);
        }else if (fragment instanceof FragBookingList){
            if (requestCode==2500 && resultCode==RESULT_OK){
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new FragDeliveryList()).commit();
            }else {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }else if (fragment instanceof FragAllSalesPerson){
            fragment.onActivityResult(requestCode, resultCode, data);
        }

    }
}
