package com.example.alex.nabat;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.alex.nabat.Utils.LogoutDialog;
import com.example.alex.nabat.Utils.NabatConnection;
import com.example.alex.nabat.data.ChangeHeader;
import com.example.alex.nabat.data.MySettings;


public class FullscreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ChangeHeader, NabatConnection {
    private static final int REQUEST_CALL = 1;
    public Button makeCall;
    public MySettings settings;
    FragmentMakeCall fragmentMakeCall;
    FragmentLogin fragmentLogin;
    FragmentTechSupport fragmentTechSupport;
    FragmentAboutSystemNabat fragmentAboutSystemNabat;
    FragmentSelfDef fragmentSelfDef;
    FragmentTransaction fTrans;

    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        settings = MySettings.getMySettings();
        Log.d("name", settings.getName());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        fTrans = getFragmentManager().beginTransaction();
        fragmentMakeCall = new FragmentMakeCall();
        fragmentLogin = new FragmentLogin();
        fragmentTechSupport = new FragmentTechSupport();
        fragmentAboutSystemNabat = new FragmentAboutSystemNabat();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if(settings.isUserActive()) {
            changeHeaderNavDrawer(true);
        }
        fragmentSelfDef = new FragmentSelfDef();
        if(settings.isUserActive()) {
            fTrans.replace(R.id.container, fragmentMakeCall);
        } else {
            fTrans.replace(R.id.container, fragmentLogin);
        }
        fTrans.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        hideSoftKeyboard(this);
        int id = item.getItemId();
        fTrans = getFragmentManager().beginTransaction();
        switch (id) {
            case R.id.main_screen : {
                fTrans.replace(R.id.container, fragmentMakeCall);
                break;
            }
            case R.id.about_nabat : {
                fTrans.replace(R.id.container, fragmentAboutSystemNabat);
                break;
            }
            case R.id.self_def : {
                fTrans.replace(R.id.container, fragmentSelfDef);
                break;
            }
            case R.id.tech_supp : {
                fTrans.replace(R.id.container, fragmentTechSupport);
                break;
            }
            case R.id.exit : {
                if(settings.isUserActive()) {
                    LogoutDialog lg = new LogoutDialog();
                    lg.setHeader(this);
                    lg.show(getSupportFragmentManager(), "logoutDialog");
                }
                break;
            }
        }
        fTrans.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void changeHeaderNavDrawer(boolean isEnter) {

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View navView =  navigationView.getHeaderView(0);
        TextView name = (TextView)navView.findViewById(R.id.header_name);
        TextView email = (TextView)navView.findViewById(R.id.header_email);
        if(isEnter) {
            name.setText(settings.getName());
            email.setText(settings.getEmail());
        } else {
            name.setText("Набат");
            email.setText("info@nabat24.ru");
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo != null) {
            return true;
        }
        return false;
    }
}
