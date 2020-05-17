package com.example.sep4android.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sep4android.Models.ArchiveRoom;
import com.example.sep4android.R;
import com.example.sep4android.ViewModels.ArchiveViewModel;
import com.example.sep4android.ViewModels.CO2ViewModel;
import com.example.sep4android.ViewModels.ConditionsViewModel;
import com.example.sep4android.Views.ConditionActivity;

import java.util.List;

public class CO2Fragment extends Fragment {

        private TextView CO2Value;
        private CO2ViewModel co2ViewModel;
        private ConditionsViewModel conditionsViewModel;
        private int roomNumber;
        Context context;

        public CO2Fragment(ConditionActivity conditionActivity, ConditionsViewModel conditionsViewModel) {
            context = conditionActivity;
            this.conditionsViewModel = conditionsViewModel;
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.fragment_co2, container, false);
       return rootView;

//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_co2);
//        CO2Value = findViewById(R.id.co2Value);
//
//        Bundle bundle = getIntent().getExtras();
//        roomNumber= bundle.getInt("roomNumber");


      /*  co2ViewModel = new ViewModelProvider(this).get(CO2ViewModel.class);
       archiveViewModel.getArchiveRooms().observe(this, new Observer<List<ArchiveRoom>>() {
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
        });*/
    }
}
