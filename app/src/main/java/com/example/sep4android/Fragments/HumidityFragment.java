package com.example.sep4android.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.example.sep4android.Models.ArchiveRoom;
import com.example.sep4android.Models.ArchiveRoomIndentification;
import com.example.sep4android.Models.Condition;
import com.example.sep4android.R;
import com.example.sep4android.ViewModels.ConditionsViewModel;
import com.example.sep4android.Views.ConditionActivity;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HumidityFragment extends Fragment {
    private ConditionActivity conditionActivity;
    private ConditionsViewModel conditionsViewModel;
    private List<Condition> conditions;
    private TextView startDateText;
    private TextView endDateText;
    private TextView averageValue;
    Calendar c;
    Date startDate;
    DatePickerDialog dpd;
    Button startDateB;
    Button endDateB;
    Button resetB;
    private boolean check;
    GraphView graph;
    LineGraphSeries<DataPoint> series;

    public HumidityFragment(final ConditionActivity conditionActivity, ConditionsViewModel conditionsViewModel) {
        this.conditionActivity = conditionActivity;
        this.conditionsViewModel = conditionsViewModel;
        conditionsViewModel.getHumidityForDateInterval().observe(this, new Observer<List<Condition>>() {
            @Override
            public void onChanged(List<Condition> data) {
                conditions = data;
                handleGraph();
            }
        });
        conditionsViewModel.getAverageHumidity().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double data) {
                averageValue.setText(String.format("%.2f", data));
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_co2, container, false);
        setDefault();
        startDateB = rootView.findViewById(R.id.startDateB);
        endDateB = rootView.findViewById(R.id.endDateB);
        resetB = rootView.findViewById(R.id.resetB);
        startDateText = rootView.findViewById(R.id.startDate);
        endDateText = rootView.findViewById(R.id.endDate);
        averageValue = rootView.findViewById(R.id.averageValue);
        graph = (GraphView) rootView.findViewById(R.id.graph);
        setupGraph();
        //todo for testing: uncomment this and comment getCO2ForDateInterval observer
        //  setTest();
        //   handleGraph();
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
                        Calendar mDate = Calendar.getInstance();
                        mDate.set(year,month,dayOfMonth);
                        startDate =mDate.getTime();
                        check = true;
                        startDateText.setText("start date: "+dayOfMonth+"/"+(month+1)+"/"+year);
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
                            String endDate =year+"-"+month+"-"+dayOfMonth;
                            Calendar c = Calendar.getInstance();
                            c.setTime(startDate);
                            String sStartDate =c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH);
                            endDateText.setText("end date: " + dayOfMonth + "/" + (month + 1) + "/" + year);
                            conditionsViewModel.getConditionForDateInterval("Humidity",sStartDate,endDate);
                            conditionsViewModel.getConditionAverageForDateInterval("Humidity",sStartDate,endDate);
                        }
                    }, year, month, day);
                    dpd.getDatePicker().setMinDate(startDate.getTime());
                    dpd.getDatePicker().setMaxDate(System.currentTimeMillis()-1000);
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

    private void setDefault()
    {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        String sNow = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        String sOneWeekBack = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
        conditionsViewModel.getConditionForDateInterval("Humidity",sOneWeekBack,sNow);
        conditionsViewModel.getConditionAverageForDateInterval("Humidity",sOneWeekBack,sNow);
        check = false;
    }

    private void setupGraph()
    {
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("month/day/year");
        gridLabel.setVerticalAxisTitle("%");
        gridLabel.setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        gridLabel.setNumHorizontalLabels(5);
        gridLabel.setHumanRounding(false);
        graph.getViewport().setXAxisBoundsManual(true);

    }

    private void handleGraph()
    {
        DataPoint[] dp = new DataPoint[conditions.size()];
        for(int i=0;i<conditions.size();i++)
        {
            dp[i] = new DataPoint(conditions.get(i).getDate(), conditions.get(i).getValue());
        }
        series = new LineGraphSeries<>(dp);
        graph.addSeries(series);
        //todo efficiency or user expirience
        graph.getViewport().setMinX(conditions.get(0).getDate().getTime());
        graph.getViewport().setMaxX(conditions.get(conditions.size()-1).getDate().getTime());
    }

    private void setTest()
    {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        ArrayList<Condition> conditions1 = new ArrayList<Condition>();
        conditions1.add(new Condition(1,calendar.getTime()));
        calendar.add(Calendar.DAY_OF_YEAR, +1);
        conditions1.add(new Condition(3,calendar.getTime()));
        calendar.add(Calendar.DAY_OF_YEAR, +1);
        conditions1.add(new Condition(4,calendar.getTime()));
        calendar.add(Calendar.DAY_OF_YEAR, +1);
        conditions1.add(new Condition(7,calendar.getTime()));
        calendar.add(Calendar.DAY_OF_YEAR, +1);
        conditions1.add(new Condition(13,calendar.getTime()));
        calendar.add(Calendar.DAY_OF_YEAR, +1);
        conditions1.add(new Condition(6,calendar.getTime()));
        calendar.add(Calendar.DAY_OF_YEAR, +1);
        conditions1.add(new Condition(5,calendar.getTime()));
        calendar.add(Calendar.DAY_OF_YEAR, +1);
        conditions1.add(new Condition(3,calendar.getTime()));
        calendar.add(Calendar.DAY_OF_YEAR, +1);
        conditions1.add(new Condition(33,calendar.getTime()));
        calendar.add(Calendar.DAY_OF_YEAR, +1);
        conditions = conditions1;

    }

}
