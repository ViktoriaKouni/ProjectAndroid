package com.example.sep4android.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CO2
{
    @SerializedName("value")
    @Expose
    private double value;
    //todo time variable, for future

    public CO2(double value){
        this.value=value;
    }

    public double getValue() {
        return value;
    }

    public boolean equals(Object obj)
    {
        if (!(obj instanceof CO2))
        {
            return false;
        }
        CO2 other = (CO2) obj;
        return value == other.getValue();
    }

}
