package com.example.sep4android.Models;

public class Humidity {
    private double value;
    //todo time variable, for future

    public Humidity(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Humidity)) {
            return false;
        }
        Humidity other = (Humidity) obj;
        return value == other.getValue();
    }

}
