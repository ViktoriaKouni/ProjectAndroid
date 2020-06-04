package com.example.sep4android.DATA.Models;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ChartUtility {
    ArrayList<Entry> values;
    LineDataSet set;

    public void setupGraph(LineChart graph) {
        values = new ArrayList<>();
        set = new LineDataSet(values, "");

        set.setColor(Color.parseColor("#ffe6cc"));
        set.setCircleColor(Color.LTGRAY);
        set.setLineWidth(2f);
        set.setValueTextColor(Color.parseColor("#ff8c1a"));
        set.setValueTextSize(13f);

        YAxis yAxis = graph.getAxisLeft();
        yAxis.setTextColor(Color.BLACK);
        yAxis.setTextSize(14f);


        YAxis rightAxis = graph.getAxisRight();
        rightAxis.setEnabled(false);


        XAxis xAxis = graph.getXAxis();
        xAxis.setGranularity(24);
        xAxis.setTextSize(14f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setValueFormatter(new ValueFormatter() {

            private final SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM", Locale.ENGLISH);

            @Override
            public String getFormattedValue(float value) {

                long millis = TimeUnit.HOURS.toMillis((long) value);
                return mFormat.format(new Date(millis));
            }
        });
        graph.getDescription().setEnabled(false);
        graph.getLegend().setEnabled(false);

    }

    public void handleGraph(LineChart graph, List<Condition> conditions) {
        values.clear();
        for (int i = 0; i < conditions.size(); i++) {
            long time = TimeUnit.MILLISECONDS.toHours(conditions.get(i).getDate().getTime());
            values.add(new Entry(time, (long) conditions.get(i).getValue()));
        }
        set.setValues(values);
        LineData data = new LineData(set);
        graph.setData(data);
        graph.notifyDataSetChanged(); // let the chart know it's data changed
        graph.invalidate(); // refresh chart
    }
}
