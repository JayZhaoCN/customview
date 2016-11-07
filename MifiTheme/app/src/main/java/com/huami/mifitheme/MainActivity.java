package com.huami.mifitheme;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.huami.mifitheme.fragment.ButtonFragment;
import com.huami.mifitheme.fragment.EditTextFragment;
import com.huami.mifitheme.fragment.ImageButtonFragment;
import com.huami.mifitheme.fragment.ImageViewFragment;
import com.huami.mifitheme.fragment.SwitcherFragment;
import com.huami.mifitheme.fragment.TextViewFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.WindowBase_BrownDeepOrangeTheme);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                int themeColor = new ThemeUtils().getThemeColor(MainActivity.this, R.attr.actionBarColor);
                StatusBarUtil.setStatusBarColor(MainActivity.this, themeColor);
                StatusBarUtil.StatusBarLightMode(MainActivity.this, 3);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        replaceFragment(TextViewFragment.newInstance());
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
        getMenuInflater().inflate(R.menu.main, menu);
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
        replaceFragment(item);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_text_view) {
            replaceFragment(TextViewFragment.newInstance());
        } else if (id == R.id.nav_edit_text) {
            replaceFragment(EditTextFragment.newInstance());
        } else if (id == R.id.nav_button) {
            replaceFragment(ButtonFragment.newInstance());
        } else if (id == R.id.nav_image_view) {
            replaceFragment(ImageViewFragment.newInstance());
        } else if (id == R.id.nav_image_button) {
            replaceFragment(ImageButtonFragment.newInstance());
        } else if (id == R.id.nav_switcher) {
            replaceFragment(SwitcherFragment.newInstance());
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_container, fragment);
        fragmentTransaction.commit();
    }
}
