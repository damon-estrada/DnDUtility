package com.example.dndsorcerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.dndsorcerapp.Fragments.CharacterSheetFragment;
import com.example.dndsorcerapp.Fragments.SearchFragment;
import com.example.dndsorcerapp.Fragments.SpellsFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final List<String> spellsToCreate = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle("Character Sheet");
        setSupportActionBar(toolbar);

        /* find the if views below */
        drawerLayout = findViewById(R.id.drawer_layout);
        spellsToCreate.add("1"); // acid arrow
        spellsToCreate.add("7"); // Animal Messenger
        spellsToCreate.add("9"); // Animate Dead
        spellsToCreate.add("10"); // Animate Objects
        spellsToCreate.add("15"); // Arcane Hand
        spellsToCreate.add("119"); // Fireball
        spellsToCreate.add("190"); // Magic Missile

        NavigationView nV = findViewById(R.id.nav_view);
        nV.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        /* On first load, load this fragment */
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new CharacterSheetFragment()).commit();
        }

    }

    /**
     * Item was selected so we return true.
     * @param menuItem
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_character_sheet:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CharacterSheetFragment()).commit();
                onBackPressed();
                break;
            case R.id.nav_spells:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SpellsFragment(spellsToCreate)).commit();
                onBackPressed();
                break;
            case R.id.nav_search:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SearchFragment()).commit();
                onBackPressed();
                break;

            case R.id.nav_about_me:
                Toast.makeText(this, "About me clicked!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_settings:
                Toast.makeText(this, "Settings clicked!", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
