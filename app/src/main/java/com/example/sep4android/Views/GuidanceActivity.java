package com.example.sep4android.Views;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.Fragments.GuidanceCO2Fragment;
import com.example.sep4android.Fragments.GuidanceHumidityFragment;
import com.example.sep4android.Fragments.GuidanceTemperatureFragment;
import com.example.sep4android.R;
import com.example.sep4android.ViewModels.GuidanceViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GuidanceActivity extends AppCompatActivity {

    private GuidanceViewModel viewModel;

    RecyclerView guidanceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidance);

        viewModel = new ViewModelProvider(this).get(GuidanceViewModel.class);

        BottomNavigationView bottomNavigationView = findViewById(R.id.guidance_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        Toolbar toolbar = findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportFragmentManager().beginTransaction().replace(R.id.guidance_fragment_container,
                new GuidanceCO2Fragment(GuidanceActivity.this, viewModel)).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment fragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.guidance_nav_CO2:
                            fragment = new GuidanceCO2Fragment(GuidanceActivity.this, viewModel);
                            break;
                        case R.id.guidance_nav_humidity:
                            fragment = new GuidanceHumidityFragment(GuidanceActivity.this, viewModel);
                            break;
                        case R.id.guidance_nav_temperature:
                            fragment = new GuidanceTemperatureFragment(GuidanceActivity.this, viewModel);
                            break;
                    }
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.guidance_fragment_container,
                            fragment).commit();
                    return true;
                }
            };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
