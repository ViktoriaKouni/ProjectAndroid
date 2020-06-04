package com.example.sep4android.UI.Views.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.sep4android.DATA.Models.ChartUtility;
import com.example.sep4android.DATA.Models.Condition;
import com.example.sep4android.R;
import com.example.sep4android.UI.ViewModels.ConditionsViewModel;
import com.example.sep4android.UI.Views.Activities.ConditionActivity;
import com.github.mikephil.charting.charts.LineChart;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class TemperatureFragment extends Fragment {
    private ConditionActivity conditionActivity;
    private ConditionsViewModel conditionsViewModel;
    private List<Condition> conditions;
    private TextView startDateText;
    private TextView endDateText;
    private TextView averageValue;
    private Calendar c;
    private Date startDate;
    private DatePickerDialog dpd;
    private boolean check;
    private LineChart graph;
    private ChartUtility chartUtility;

    public TemperatureFragment(final ConditionActivity conditionActivity, ConditionsViewModel conditionsViewModel,ChartUtility cUtility) {
        this.conditionActivity = conditionActivity;
        this.conditionsViewModel = conditionsViewModel;
        this.chartUtility = cUtility;
        conditionsViewModel.getTemperatureForDateInterval().observe(this, new Observer<List<Condition>>() {
            @Override
            public void onChanged(List<Condition> data) {
                conditions = data;
                Collections.sort(conditions, new Comparator<Condition>() {
                    @Override
                    public int compare(Condition u1, Condition u2) {
                        return u1.getDate().compareTo(u2.getDate());
                    }
                });
                chartUtility.handleGraph(graph, conditions);
            }
        });
        conditionsViewModel.getAverageTemperature().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double data) {
                averageValue.setText(String.format("%.2f", data));
            }
        });
        setDefault();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_condition, container, false);
        Button startDateB = rootView.findViewById(R.id.startDateB);
        Button endDateB = rootView.findViewById(R.id.endDateB);
        Button resetB = rootView.findViewById(R.id.resetB);
        startDateText = rootView.findViewById(R.id.startDate);
        endDateText = rootView.findViewById(R.id.endDate);
        averageValue = rootView.findViewById(R.id.averageValue);
        graph = (LineChart) rootView.findViewById(R.id.graph);
        chartUtility.setupGraph(graph);
        startDateB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(conditionActivity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar mDate = Calendar.getInstance();
                        mDate.set(year, month, dayOfMonth);
                        startDate = mDate.getTime();
                        check = true;
                        startDateText.setText("start date: " + dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                dpd.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                dpd.show();
            }
        });
        endDateB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!check) {
                    Toast.makeText(conditionActivity, "Please select start date first", Toast.LENGTH_SHORT).show();
                } else {
                    c = Calendar.getInstance();
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    int month = c.get(Calendar.MONTH);
                    int year = c.get(Calendar.YEAR);

                    dpd = new DatePickerDialog(conditionActivity, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            String endDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                            Calendar c = Calendar.getInstance();
                            c.setTime(startDate);
                            String sStartDate = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH);
                            endDateText.setText("end date: " + dayOfMonth + "/" + (month + 1) + "/" + year);
                            conditionsViewModel.getConditionForDateInterval("Temperature", sStartDate, endDate);
                            conditionsViewModel.getConditionAverageForDateInterval("Temperature", sStartDate, endDate);
                        }
                    }, year, month, day);
                    dpd.getDatePicker().setMinDate(startDate.getTime());
                    dpd.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                    dpd.show();
                }
            }
        });
        resetB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDateText.setText("Last week readings");
                endDateText.setText("");
                setDefault();
            }
        });
        return rootView;
    }

    private void setDefault() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        String sNow = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        String sOneWeekBack = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        conditionsViewModel.getConditionForDateInterval("Temperature", sOneWeekBack, sNow);
        conditionsViewModel.getConditionAverageForDateInterval("Temperature", sOneWeekBack, sNow);
        check = false;
    }
}
