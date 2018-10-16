package com.hci.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

import com.pixplicity.fontview.FontAppCompatTextView;

import com.hci.project.fragment.Help;
import com.hci.project.fragment.HomeFragment;
import com.hci.project.fragment.VolumeFragment;
import com.hci.project.fragment.WorkoutList;
import com.hci.project.fragment.addWorkouts;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String SELECTED_ITEM_ID = "SELECTED_ITEM_ID";
    private final Handler mDrawerHandler = new Handler();
    private DrawerLayout mDrawerLayout;
    private int mPrevSelectedId;
    private NavigationView mNavigationView;
    private int mSelectedId;
    private Toolbar mToolbar;
    private FontAppCompatTextView id;
    private  String Userss,pwd,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awal);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        View headerView = LayoutInflater.from(this).inflate(R.layout.header, mNavigationView, true);


        id = (FontAppCompatTextView) headerView.findViewById(R.id.textView);
        Bundle extras = getIntent().getExtras();
        String[] recordsOfUser = extras.getStringArray("records");
        setId(recordsOfUser[0]);
        setEmail(recordsOfUser[1]);
        setPass(recordsOfUser[2]);
        id.setText(recordsOfUser[0]);


        assert mNavigationView != null;
        mNavigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                super.onDrawerSlide(drawerView, 0);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, 0);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mSelectedId = mNavigationView.getMenu().getItem(prefs.getInt("default_view", 0)).getItemId();
        mSelectedId = savedInstanceState == null ? mSelectedId : savedInstanceState.getInt(SELECTED_ITEM_ID);
        mPrevSelectedId = mSelectedId;
        mNavigationView.getMenu().findItem(mSelectedId).setChecked(true);

        if (savedInstanceState == null) {
            mDrawerHandler.removeCallbacksAndMessages(null);
            mDrawerHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    navigate(mSelectedId);
                }
            }, 250);

            boolean openDrawer = prefs.getBoolean("open_drawer", false);

            if (openDrawer)
                mDrawerLayout.openDrawer(GravityCompat.START);
            else
                mDrawerLayout.closeDrawers();
        }
    }


    public void setId (String user) {
         Userss = user;
    }
    public String getId(){
        return Userss;
    }
    public void setEmail (String emails) {
        email = emails;
    }
    public String getEmail(){
        return email;
    }
    public void setPass (String pass) {
       pwd = pass  ;
    }
    public String getPass(){
        return pwd;
    }
    private void navigate(final int itemId) {
        final View elevation = findViewById(R.id.elevation);
        Fragment navFragment = null;
        switch (itemId) {
            case R.id.nav_1:
                mPrevSelectedId = itemId;
                setTitle(R.string.nav_home);
                navFragment = new HomeFragment();

                break;
            case R.id.nav_2:
                mPrevSelectedId = itemId;
                setTitle("Personal Trainer");
                navFragment = new VolumeFragment();
                break;
            case R.id.nav_3:
                mPrevSelectedId = itemId;
                setTitle("Workout List");
                navFragment = new WorkoutList();
                break;
            case R.id.nav_7:
                mPrevSelectedId = itemId;
                setTitle("Add Workout");
                mNavigationView.getMenu().findItem(mPrevSelectedId).setChecked(true);
                navFragment = new addWorkouts();
                break;
            case R.id.nav_5:
                mPrevSelectedId = itemId;
                setTitle("Help");
                mNavigationView.getMenu().findItem(mPrevSelectedId).setChecked(true);
                navFragment = new Help();
                break;
            case R.id.nav_8:
                mPrevSelectedId = itemId;
                Intent accountsIntent = new Intent(this, LoginActivity.class);
                startActivity(accountsIntent);
                break;
            //case R.id.nav_5:
            //startActivity(new Intent(this, SettingsActivity.class));
            //mNavigationView.getMenu().findItem(mPrevSelectedId).setChecked(true);
            //return;
            /*case R.id.nav_6:
                startActivity(new Intent(this, AboutActivity.class));
                mNavigationView.getMenu().findItem(mPrevSelectedId).setChecked(true);
                return;*/
        }

        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(4));

        if (navFragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            try {
                transaction.replace(R.id.content_frame, navFragment).commit();

                //setting jarak elevasi bayangan ketika menggunakan tabs
                if (elevation != null) {
                    params.topMargin = navFragment instanceof VolumeFragment ? dp(48) : 0;

                    Animation a = new Animation() {
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                            elevation.setLayoutParams(params);
                        }
                    };
                    a.setDuration(150);
                    elevation.startAnimation(a);
                }
            } catch (IllegalStateException ignored) {
            }
        }
    }

    public int dp(float value) {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;

        if (value == 0) {
            return 0;
        }
        return (int) Math.ceil(density * value);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        mSelectedId = menuItem.getItemId();
        mDrawerHandler.removeCallbacksAndMessages(null);
        mDrawerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigate(mSelectedId);
            }
        }, 250);
        mDrawerLayout.closeDrawers();
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_ITEM_ID, mSelectedId);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}