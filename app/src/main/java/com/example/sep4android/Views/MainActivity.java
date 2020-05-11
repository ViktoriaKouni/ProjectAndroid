package com.example.sep4android.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.sep4android.Models.NotificationService;
import com.example.sep4android.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStop () {
        super.onStop() ;
        startService( new Intent( this, NotificationService. class )) ;
    }

    public void onClickSeeCondition(View v)
    {
        Intent intent = new Intent(MainActivity.this, CO2Activity.class);
        intent.putExtra("roomNumber",1);
        startActivity(intent);
    }
}
