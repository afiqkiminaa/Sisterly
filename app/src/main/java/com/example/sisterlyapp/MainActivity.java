package com.example.sisterlyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up Toolbar
        Toolbar toolbar = findViewById(R.id.TBMainAct);
        setSupportActionBar(toolbar);

        // Set up NavController
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        NavController navController = navHostFragment.getNavController();

        // Configure AppBar with NavController
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_search, R.id.nav_chat, R.id.nav_profile // Destinations
        ).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Set up BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        // Floating Action Button
        FloatingActionButton fabAdd = findViewById(R.id.fab);
        fabAdd.setOnClickListener(v -> {
            // Handle FAB click action
            Toast.makeText(this, "FAB clicked", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.fragmentContainer);
        return NavigationUI.navigateUp(navController, (AppBarConfiguration) null) || super.onSupportNavigateUp();
    }
}
