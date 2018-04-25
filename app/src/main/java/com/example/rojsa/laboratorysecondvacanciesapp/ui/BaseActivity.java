package com.example.rojsa.laboratorysecondvacanciesapp.ui;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.rojsa.laboratorysecondvacanciesapp.R;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;

/**
 * Created by rojsa on 15.04.2018.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected void createDrawer(Toolbar toolbar) {

        AccountHeader header = new AccountHeaderBuilder()
                .withActivity(this)
                .addProfiles(new ProfileDrawerItem()
                        .withName(R.string.header_title)
                        .withIcon(R.drawable.logo)
                        .withEmail(R.string.header_version_program)
                )
                .withHeaderBackground(R.drawable.background_drawer)
                .withAlternativeProfileHeaderSwitching(false)
                .withTranslucentStatusBar(true)
                .withSelectionListEnabledForSingleProfile(false)
                .build();

        PrimaryDrawerItem search = new PrimaryDrawerItem().withName(R.string.drawer_search_item).withIdentifier(1).withIcon(R.drawable.ic_search_grey);
        PrimaryDrawerItem vacancies = new PrimaryDrawerItem().withName(R.string.drawer_item_best_vacancies).withIdentifier(2).withIcon(R.drawable.ic_search_grey);
        PrimaryDrawerItem about = new PrimaryDrawerItem().withName(R.string.drawer_item_about).withIdentifier(3).withIcon(R.drawable.ic_search_grey);
        PrimaryDrawerItem exit = new PrimaryDrawerItem().withName(R.string.drawer_item_exit).withIdentifier(4).withIcon(R.drawable.ic_search_grey);

        Drawer drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(header)
                .addDrawerItems(
                        search,
                        vacancies,
                        new DividerDrawerItem(),
                        about,
                        exit)
                .build();




    }
}
