package com.example.sep4android.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.sep4android.Models.ArchiveRoom;
import com.example.sep4android.R;
import com.example.sep4android.ViewModels.ConditionsViewModel;
import com.example.sep4android.Views.ConditionActivity;

import java.util.List;

public class CO2Fragment extends Fragment {

        private TextView CO2Value;
        private ConditionsViewModel conditionsViewModel;
        private int roomNumber;
        Context context;
    private List<ArchiveRoom> archiveRooms;

        public CO2Fragment(ConditionActivity conditionActivity, ConditionsViewModel conditionsViewModel) {
            context = conditionActivity;
            this.conditionsViewModel = conditionsViewModel;
            roomNumber = conditionActivity.getRoomNumber();
            conditionsViewModel.getArchiveRooms().observe(this, new Observer<List<ArchiveRoom>>() {
                @Override
                public void onChanged(List<ArchiveRoom> rooms) {
                    archiveRooms = rooms;
                    updateChange();
                }
            });
        }

    private void updateChange() {
            for(int i =0;i<archiveRooms.size();i++)
            {
                if(archiveRooms.get(i).getRoomNumber()==roomNumber)
                {
                    CO2Value.setText(""+ archiveRooms.get(i).getCO2().getLevel());
                }
            }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.fragment_co2, container, false);
       CO2Value = rootView.findViewById(R.id.co2Value);
       return rootView;
    }
}
