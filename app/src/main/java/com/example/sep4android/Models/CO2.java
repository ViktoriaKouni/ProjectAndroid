package com.example.sep4android.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CO2 implements Condition
{
/*

    @SerializedName("value")
    @Expose*/
    private double value;

    public CO2(double value){
        this.value=value;
    }

    @Override
    public double getValue() {
        return value;
    }

}
