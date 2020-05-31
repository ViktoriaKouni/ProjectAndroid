package com.example.sep4android.APIS;

import androidx.annotation.Nullable;

import com.example.sep4android.Models.Condition;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class ConditionsResponse {
    private double value;

    private Date date;

    public Date getDate() {
        return date;
    }

    public double getValue() {
        return value;
    }

}
