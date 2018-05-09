package com.naufalrzld.kamus;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.naufalrzld.kamus.adapter.KamusAdapter;
import com.naufalrzld.kamus.db.KamusHelper;
import com.naufalrzld.kamus.model.KamusModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.naufalrzld.kamus.db.KamusContract.TABLE_EN_ID;
import static com.naufalrzld.kamus.db.KamusContract.TABLE_ID_EN;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.rv_kamus)
    RecyclerView rvKamus;

    private KamusAdapter adapter;
    private ArrayList<KamusModel> kamusList = new ArrayList<>();

    private KamusHelper kamusHelper;

    private boolean status = true;
    private String tableName;

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

        navigationView.setNavigationItemSelectedListener(this);

        kamusHelper = new KamusHelper(this);
        adapter = new KamusAdapter(this);

        rvKamus.setHasFixedSize(true);
        rvKamus.setLayoutManager(new LinearLayoutManager(this));
        rvKamus.setAdapter(adapter);

        kamusHelper.open();

        if (status) {
            tableName = TABLE_EN_ID;
            getSupportActionBar().setTitle(R.string.toolbar_title_en);
        } else {
            tableName = TABLE_ID_EN;
            getSupportActionBar().setTitle(R.string.toolbar_title_id);
        }

        kamusList = kamusHelper.getAllData(tableName);
        adapter.setData(kamusList);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_en_id :
                status = true;
                break;
            case R.id.nav_id_en:
                status = false;
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        if (status) {
            tableName = TABLE_EN_ID;
            getSupportActionBar().setTitle(R.string.toolbar_title_en);
        } else {
            tableName = TABLE_ID_EN;
            getSupportActionBar().setTitle(R.string.toolbar_title_id);
        }

        kamusList.clear();
        kamusList = kamusHelper.getAllData(tableName);
        adapter.setData(kamusList);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                kamusList.clear();

                if (status) {
                    tableName = TABLE_EN_ID;
                } else {
                    tableName = TABLE_ID_EN;
                }

                kamusList = kamusHelper.getDataByWord(tableName, newText);
                adapter.setData(kamusList);
                return true;
            }
        });
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        kamusHelper.close();
    }
}
