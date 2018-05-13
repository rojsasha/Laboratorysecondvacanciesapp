package com.example.rojsa.laboratorysecondvacanciesapp.ui;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.rojsa.laboratorysecondvacanciesapp.R;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.Objects;

/**
 * Created by rojsa on 15.04.2018.
 */

public abstract class BaseActivity extends AppCompatActivity {
    Drawer drawer;
    AccountHeader header;


    private String packageInfo() {
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return String.format(getString(R.string.header_version_program), pInfo.versionName);
    }

    private void createAccountHeader() {
        header = new AccountHeaderBuilder()
                .withActivity(this)
                .addProfiles(new ProfileDrawerItem()
                        .withName(R.string.header_title)
                        .withIcon(R.drawable.logo)
                        .withEmail(packageInfo())
                )
                .withHeaderBackground(R.drawable.background_drawer)
                .withAlternativeProfileHeaderSwitching(false)
                .withTranslucentStatusBar(true)
                .withSelectionListEnabledForSingleProfile(false)
                .build();

    }

    protected void createDrawer(Toolbar toolbar, boolean b) {

        createAccountHeader();

        PrimaryDrawerItem search = new PrimaryDrawerItem().withName(R.string.drawer_search_item).withIdentifier(1).withIcon(R.drawable.ic_search_grey);
        PrimaryDrawerItem vacancies = new PrimaryDrawerItem().withName(R.string.drawer_item_best_vacancies).withIdentifier(2).withIcon(R.drawable.ic_search_grey);
        PrimaryDrawerItem about = new PrimaryDrawerItem().withName(R.string.drawer_item_about).withIdentifier(3).withIcon(R.drawable.ic_search_grey);
        PrimaryDrawerItem exit = new PrimaryDrawerItem().withName(R.string.drawer_item_exit).withIdentifier(4).withIcon(R.drawable.ic_search_grey);

        Drawer.OnDrawerItemClickListener click = new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                switch (position) {
                    case 2:
                        Intent intent = new Intent(getApplicationContext(), FavoriteVacancyActivity.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        };

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(header)
                .withOnDrawerItemClickListener(click)
                .addDrawerItems(
                        search,
                        vacancies,
                        new DividerDrawerItem(),
                        about,
                        exit)
                .build();

        if (b) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);

        } else {
            drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                Toast.makeText(getApplicationContext(),"123",Toast.LENGTH_LONG).show();
                return true;



            default:
                Toast.makeText(getApplicationContext(),"456",Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
