package com.example.sep4android.DATA.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OptimalValues {
    @SerializedName("optimalCarbonDioxide")
    @Expose
    private double optimalCO2;
    private double optimalTemperature;
    private double optimalHumidity;

    public OptimalValues(double co2, double temperature, double humidity) {
        this.optimalCO2 = co2;
        this.optimalTemperature = temperature;
        this.optimalHumidity = humidity;
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

    public boolean equals(Object obj) {
        if (!(obj instanceof OptimalValues)) {
            return false;
        }
        OptimalValues other = (OptimalValues) obj;
        return optimalCO2 == other.getOptimalCO2() && optimalTemperature == other.getOptimalTemperature() && optimalHumidity == other.getOptimalHumidity();
    }
}
