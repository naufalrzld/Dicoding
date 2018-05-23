package com.naufalrzld.moviecatalogue;

import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.naufalrzld.moviecatalogue.fragment.FavouriteFragment;
import com.naufalrzld.moviecatalogue.fragment.NowPlayingFragment;
import com.naufalrzld.moviecatalogue.fragment.SearchFragment;
import com.naufalrzld.moviecatalogue.fragment.UpcomingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navView;

    private int state = 1;
    private int titleId = R.string.fragment_now_playing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);

        setFragment(new NowPlayingFragment());
        setToolbarTitle(titleId);
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragment);
        fragmentTransaction.commit();
    }

    private void setToolbarTitle(int title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                if (state != 1) {
                    Fragment fragment = new NowPlayingFragment();
                    setFragment(fragment);
                    titleId = R.string.fragment_now_playing;
                    setToolbarTitle(titleId);
                    state = 1;
                }
                break;
            case R.id.nav_search:
                if (state != 2) {
                    Fragment fragment = new SearchFragment();
                    setFragment(fragment);
                    titleId = R.string.fragment_search;
                    setToolbarTitle(titleId);
                    state = 2;
                }
                break;
            case R.id.nav_upcoming:
                if (state != 3) {
                    Fragment fragment = new UpcomingFragment();
                    setFragment(fragment);
                    titleId = R.string.fragment_upcoming;
                    setToolbarTitle(titleId);
                    state = 3;
                }
                break;
            case R.id.nav_favorite:
                 if (state != 4) {
                     Fragment fragment = new FavouriteFragment();
                     setFragment(fragment);
                     titleId = R.string.fragment_favourite;
                     setToolbarTitle(titleId);
                     state = 4;
                 }
                break;
            case R.id.nav_setting:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_main);
        fragment.onActivityResult(requestCode, resultCode, data);
    }
}
