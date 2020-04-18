package com.example.sep4android.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sep4android.Models.ArchiveRoom;
import com.example.sep4android.Models.CO2;
import com.example.sep4android.R;
import com.example.sep4android.ViewModels.CO2ViewModel;

import org.w3c.dom.Text;

import java.util.List;

public class CO2Activity extends AppCompatActivity {

        private TextView CO2Value;
        private CO2ViewModel co2ViewModel;
        private int roomNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition);
        CO2Value = findViewById(R.id.co2Value);

        Bundle bundle = getIntent().getExtras();
        roomNumber= bundle.getInt("roomNumber");


        co2ViewModel = new ViewModelProvider(this).get(CO2ViewModel.class);
        co2ViewModel.getArchiveRooms().observe(this, new Observer<List<ArchiveRoom>>() {
            @Override
            public void onChanged(List<ArchiveRoom> archiveRooms) {
                if(!archiveRooms.isEmpty())
                {
                    for(int i=0;i<archiveRooms.size();i++)
                    {
                        if(archiveRooms.get(i).getRoomNumber()==roomNumber)
                        {
                            CO2Value.setText(String.valueOf(archiveRooms.get(i).getCO2().getLevel()));
                            break;
                        }
                    }

                }
                else
                {
                    CO2Value.setText("No data");
                }
            }
        });
    }

    public void onClickBack(View v)
    {
        finish();
    }
}
