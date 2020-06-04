package com.example.sep4android.Views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sep4android.R;

public class SettingsActivity extends AppCompatActivity {
    private CheckBox receiveNotifications;
    private EditText notificationTimeInterval;
    private Button language, save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        receiveNotifications = findViewById(R.id.notifications_checkBox);
        notificationTimeInterval = findViewById(R.id.notifications_time_interval);
        language = findViewById(R.id.settingsLanguage);
        save = findViewById(R.id.settingsSave);

        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
