package com.example.sep4android.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OptimalValues {
    private double optimalTemperature;

    private double optimalHumidity;
    @SerializedName("optimalCarbonDioxide")
    @Expose
    private double optimalCO2;

    public OptimalValues( double co2)
    {
        this.optimalCO2 = co2;
    }

    public double getOptimalCO2() {
        return optimalCO2;
    }

    public double getOptimalHumidity() {
        return optimalHumidity;
    }

    public double getOptimalTemperature() {
        return optimalTemperature;
    }
    public boolean equals(Object obj)
    {
        if (!(obj instanceof OptimalValues))
        {
            return false;
        }
        OptimalValues other = (OptimalValues) obj;
        return  optimalCO2 == other.getOptimalCO2();
    }
}
