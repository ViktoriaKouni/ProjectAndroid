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
    private TextView roomNumber;
    private ArchiveRoom archiveRoom;

    public CO2Fragment(final ConditionActivity conditionActivity, ConditionsViewModel conditionsViewModel) {
        conditionsViewModel.getArchiveRooms().observe(this, new Observer<List<ArchiveRoom>>() {
            @Override
            public void onChanged(List<ArchiveRoom> rooms) {
                for(int i = 0;i<rooms.size();i++)
                {
                    if(conditionActivity.getRoomNumber() == rooms.get(i).getRoomNumber())
                    {
                        if(!rooms.get(i).equals(archiveRoom))
                        {
                            archiveRoom = rooms.get(i);
                            CO2Value.setText(""+ rooms.get(i).getCO2());
                            roomNumber.setText(""+ rooms.get(i).getRoomNumber());
                            //todo refresh
                        }
                        break;
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_co2, container, false);
        CO2Value = rootView.findViewById(R.id.co2Value);
        roomNumber=rootView.findViewById(R.id.roomNumber);
        return rootView;
    }
}
