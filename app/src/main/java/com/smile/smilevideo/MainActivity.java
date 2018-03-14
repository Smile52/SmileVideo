package com.smile.smilevideo;

import android.content.Intent;
import android.graphics.ImageFormat;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.smile.smilevideo.author.AuthorFragment;
import com.smile.smilevideo.base.BaseActivity;
import com.smile.smilevideo.base.BaseFragment;
import com.smile.smilevideo.home.HomeFragment;
import com.smile.smilevideo.home.HomePresenter;
import com.smile.smilevideo.home.IHomeView;
import com.smile.smilevideo.hot.HotFragment;
import com.smile.smilevideo.player.PlayerActivity;


public class MainActivity extends BaseActivity implements  NavigationView.OnNavigationItemSelectedListener{

    private TextView mTextMessage;

    private AuthorFragment mAuthorFragment;
    private HotFragment mHotFragment;
    private HomeFragment mHomeFragment;
    private FragmentManager mManager;
    private FragmentTransaction mTransaction;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            mManager = getSupportFragmentManager();
            mTransaction = mManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (mHomeFragment == null){
                        mHomeFragment = new HomeFragment();

                    }
                    mTransaction.replace(R.id.main_content_layout, mHomeFragment);

                    mTransaction.commit();
                    return true;
                case R.id.navigation_dashboard:
                   // mTextMessage.setText(R.string.title_dashboard);
                    if (mAuthorFragment == null){
                        mAuthorFragment = new AuthorFragment();

                    }
                    mTransaction.replace(R.id.main_content_layout, mAuthorFragment);

                    mTransaction.commit();
                    return true;

                case R.id.navigation_hot:
                    if (mHotFragment == null){
                        mHotFragment = new HotFragment();
                    }
                    mTransaction.replace(R.id.main_content_layout, mHotFragment);

                    mTransaction.commit();
                    return true;

            }
            Log.e("dandy", "end...");
            return false;
        }

    };



    @Nullable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(toolbar);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        showDefaultFragment();

    }

    @Override
    protected void initView() {

    }

    @Override
    protected String[] getNeedPermissions() {
        return new String[0];
    }

    @Override
    protected void permissionGrantedSuccess() {

    }

    @Override
    protected void permissionGrantedFail() {

    }


    private void showDefaultFragment(){
        if (mManager == null){
            mManager = getSupportFragmentManager();
        }
        if (mTransaction == null){
            mTransaction = mManager.beginTransaction();
        }
        if (mHomeFragment == null){
            mHomeFragment = new HomeFragment();
            mTransaction.add(R.id.main_content_layout, mHomeFragment);
        }else {
            mTransaction.show(mHomeFragment);
        }
        mTransaction.commit();



    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            startActivity(new Intent(this, PlayerActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
