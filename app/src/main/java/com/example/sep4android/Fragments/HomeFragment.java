package com.example.sep4android.Fragments;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;

import com.example.sep4android.Models.ArchiveRoom;
import com.example.sep4android.R;
import com.example.sep4android.ViewModels.ConditionsViewModel;
import com.example.sep4android.Views.ConditionActivity;

import java.util.List;

public class HomeFragment extends Fragment {
    private TextView CO2Value;
    private TextView TemperatureValue;
    private TextView HumidityValue;
    private TextView roomName;
    private ArchiveRoom archiveRoom;

    public HomeFragment(final ConditionActivity conditionActivity, ConditionsViewModel conditionsViewModel) {
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
                            CO2Value.setText(""+ archiveRoom.getCO2().getValue());
                            TemperatureValue.setText(""+archiveRoom.getTemperature().getValue());
                            HumidityValue.setText(""+archiveRoom.getHumidity().getValue());
                            roomName.setText(""+archiveRoom.getRoomName());

                            //On click on the current value, a toast appears with the recommended one
                            SpannableString spannableStringCO2=new SpannableString(String.valueOf(archiveRoom.getCO2().getValue()));
                            ClickableSpan clickableSpanCO2=new ClickableSpan() {
                                @Override
                                public void onClick(@NonNull View widget) {
                                    Toast.makeText(conditionActivity, "The recommended value for CO2 is " + archiveRoom.getOptimalValues().getOptimalCO2() + "%", Toast.LENGTH_LONG).show();
                                }
                            };
                            spannableStringCO2.setSpan(clickableSpanCO2,0,3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                            CO2Value.setText(spannableStringCO2);
                            CO2Value.setMovementMethod(LinkMovementMethod.getInstance());

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
        TemperatureValue = rootView.findViewById(R.id.temperatureValue);
        HumidityValue = rootView.findViewById(R.id.humidityValue);
        roomName =rootView.findViewById(R.id.archiveRoom);
        return rootView;
    }
}
