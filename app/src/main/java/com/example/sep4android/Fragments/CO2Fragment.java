package com.example.sep4android.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.sep4android.Models.ArchiveRoom;
import com.example.sep4android.R;
import com.example.sep4android.ViewModels.ConditionsViewModel;
import com.example.sep4android.Views.ConditionActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CO2Fragment extends Fragment {
    private ConditionActivity conditionActivity;
    private ArchiveRoom archiveRoom;
    private TextView startDate;
    private TextView endDate;
    Calendar c;
    Calendar minDate;
    Calendar maxDate;
    DatePickerDialog dpd;
    Button startDateB;
    Button endDateB;
    Button resetB;
    private boolean check;

    public CO2Fragment(final ConditionActivity conditionActivity, ConditionsViewModel conditionsViewModel) {
        this.conditionActivity = conditionActivity;
        conditionsViewModel.getArchiveRoomLatestValue().observe(this, new Observer<ArchiveRoom>() {
            @Override
            public void onChanged(ArchiveRoom room) {
                if(!room.equals(archiveRoom))
                {
                    archiveRoom = room;

                }
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_co2, container, false);
        startDateB = rootView.findViewById(R.id.startDateB);
        endDateB = rootView.findViewById(R.id.endDateB);
        resetB = rootView.findViewById(R.id.resetB);
        startDate = rootView.findViewById(R.id.startDate);
        endDate = rootView.findViewById(R.id.endDate);
        startDateB.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(conditionActivity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {
                        minDate = Calendar.getInstance();
                        minDate.set(year,month,dayOfMonth);
                        check = true;
                        startDate.setText("start date: "+dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },year,month,day);
                dpd.getDatePicker().setMaxDate(System.currentTimeMillis()-1000);
                dpd.show();
            }
        });
        endDateB.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                if (check == false) {
                    Toast.makeText(conditionActivity, "Please select start date first", Toast.LENGTH_SHORT).show();
                } else {
                    c = Calendar.getInstance();
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    int month = c.get(Calendar.MONTH);
                    int year = c.get(Calendar.YEAR);

                    dpd = new DatePickerDialog(conditionActivity, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                maxDate = Calendar.getInstance();
                                maxDate.set(year, month, dayOfMonth);
                                endDate.setText("end date: " + dayOfMonth + "/" + (month + 1) + "/" + year);
                                //todo get api shit
                        }
                    }, year, month, day);
                    dpd.getDatePicker().setMinDate(minDate.getTimeInMillis());
                    dpd.show();
                }
            }
        });
        resetB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDate.setText("Last week readings");
                endDate.setText("");
                //todo get api last week
            }
        });
        return rootView;
    }

}
