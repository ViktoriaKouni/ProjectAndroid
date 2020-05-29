package com.example.sep4android.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sep4android.Fragments.CO2Fragment;
import com.example.sep4android.Fragments.HomeFragment;
import com.example.sep4android.Fragments.HumidityFragment;
import com.example.sep4android.Fragments.TemperatureFragment;
import com.example.sep4android.R;
import com.example.sep4android.ViewModels.ConditionsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ConditionActivity extends AppCompatActivity {
    private ConditionsViewModel viewModel;
    private int roomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition);
        Toolbar toolbar = findViewById(R.id.tool);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        viewModel = new ViewModelProvider(this).get(ConditionsViewModel.class);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey("number")) {
            roomNumber = bundle.getInt("number");
        }

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment(ConditionActivity.this, viewModel)).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(this, MainActivity.class);
                this.startActivity(intent);
                break;
            case R.id.guidance:
                Intent intentGuidance = new Intent(this, GuidanceActivity.class);
                this.startActivity(intentGuidance);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    public int getRoomNumber()
    {
        return roomNumber;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment fragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            fragment = new HomeFragment(ConditionActivity.this, viewModel);
                            break;
                        case R.id.nav_CO2:
                            fragment = new CO2Fragment(ConditionActivity.this, viewModel);
                        break;
                        case R.id.nav_humidity:
                            fragment = new HumidityFragment();
                            break;
                        case R.id.nav_temperature:
                            fragment = new TemperatureFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            fragment).commit();
                            return true;
                }
            };
}
