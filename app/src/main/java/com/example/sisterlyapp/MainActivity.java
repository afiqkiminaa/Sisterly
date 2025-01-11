package com.example.sisterlyapp;

import static androidx.navigation.ui.NavigationUI.navigateUp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.TBMainAct);
        setSupportActionBar(toolbar);

        NavHostFragment host = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        NavController navController = host.getNavController();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        setupBottomNavMenu(navController);



        FloatingActionButton fabAdd = findViewById(R.id.fab);
        fabAdd.setOnClickListener(v -> {
            // Handle FAB click (e.g., navigate to an Add screen)
            // For now, just show a Toast
            Toast.makeText(this, "Add Action", Toast.LENGTH_SHORT).show();
        });
    }

    private void setupBottomNavMenu(NavController navController) {
        BottomNavigationView bottomNavigationView = findViewById(R.id.helpline_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment = null;

            // Check which item was clicked and set the corresponding fragment
            if (item.getItemId() == R.id.nav_home) {
                fragment = new HomeActivity();
            } else if (item.getItemId() == R.id.nav_search) {
                fragment = new SearchFragment();
            } else if (item.getItemId() == R.id.nav_chat) {
                fragment = new ChatListFragment();
            } else if (item.getItemId() == R.id.nav_profile) {
                fragment = new UserProfileFragment();
            }

            // Replace the fragment in the container
            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .commit();
            }

            return true; // Return true to indicate the menu item was successfully handled
        });

        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_bottom, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        try{
            Navigation.findNavController(this, R.id.fragmentContainer).navigate(item.getItemId());
            return true;
        }
        catch (Exception ex){
            return super.onOptionsItemSelected(item);
        }
    }
}



//        // Bottom Navigation
//        BottomNavigationView bottomNavigationView = findViewById(R.id.helpline_nav);
//        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
//            Fragment fragment = null;
//
//            if (item.getItemId() == R.id.nav_home) {
//                fragment = new HomeFragment();
//            } else if (item.getItemId() == R.id.nav_search) {
//                fragment = new SearchFragment();
//            } else if (item.getItemId() == R.id.nav_chat) {
//                fragment = new ChatFragment();
//            } else if (item.getItemId() == R.id.nav_profile) {
//                fragment = new ProfileFragment();
//            }
//
//            if (fragment != null) {
//                loadFragment(fragment); // Load the selected fragment
//            }
//
//            return true;
//        });
//        // Floating Action Button
//        FloatingActionButton fabAdd = findViewById(R.id.fab);
//        fabAdd.setOnClickListener(v -> {
//            // Handle FAB click (e.g., navigate to an Add screen)
//            // For now, just show a Toast
//            Toast.makeText(this, "Add Action", Toast.LENGTH_SHORT).show();
//        });
//    }
//
//    private boolean loadFragment(Fragment fragment) {
//        if (fragment != null) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.fragmentContainer, fragment)
//                    .commit();
//            return true;
//        }
//        return false;
//    }
//}

