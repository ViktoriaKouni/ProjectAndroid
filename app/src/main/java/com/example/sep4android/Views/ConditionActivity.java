package com.example.sep4android.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.sep4android.Fragments.CO2Fragment;
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

        viewModel = new ViewModelProvider(this).get(ConditionsViewModel.class);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey("number")) {
            roomNumber = bundle.getInt("number");

        }

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new CO2Fragment(ConditionActivity.this, viewModel)).commit();
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
