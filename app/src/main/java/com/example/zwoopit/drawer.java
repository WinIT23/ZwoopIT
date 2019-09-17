package com.example.zwoopit;

import android.content.Intent;
import android.os.Bundle;

import com.example.zwoopit.ui.gallery.GalleryFragment;
import com.example.zwoopit.ui.home.HomeFragment;
import com.example.zwoopit.ui.send.SendFragment;
import com.example.zwoopit.ui.share.ShareFragment;
import com.example.zwoopit.ui.slideshow.SlideshowFragment;
import com.example.zwoopit.ui.tools.ToolsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Toast;

public class drawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        return true;
                    }
                });
    }



    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.nav_home:
                Toast.makeText(drawer.this,"IN home now",Toast.LENGTH_SHORT).show();
                Fragment fragment = new HomeFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment).commit();
                break;
            case R.id.nav_gallery:
                Toast.makeText(drawer.this,"IN gallery now",Toast.LENGTH_SHORT).show();
                Intent gallery = new Intent(drawer.this, GalleryFragment.class);
                drawer.this.startActivity(gallery);
                break;
            case R.id.nav_send:
                Toast.makeText(drawer.this,"IN send now",Toast.LENGTH_SHORT).show();
                Intent send = new Intent(drawer.this, SendFragment.class);
                drawer.this.startActivity(send);
                break;
            case R.id.nav_share:
                Toast.makeText(drawer.this,"IN share now",Toast.LENGTH_SHORT).show();
                Intent share = new Intent(drawer.this, ShareFragment.class);
                drawer.this.startActivity(share);
                break;
            case R.id.nav_slideshow:
                Toast.makeText(drawer.this,"IN slideshow now",Toast.LENGTH_SHORT).show();
                Intent slideshow = new Intent(drawer.this, SlideshowFragment.class);
                drawer.this.startActivity(slideshow);
                break;
            case R.id.nav_tools:
                Toast.makeText(drawer.this,"IN tools now",Toast.LENGTH_SHORT).show();
                Intent tools = new Intent(drawer.this, ToolsFragment.class);
                drawer.this.startActivity(tools);
                break;
        }
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
