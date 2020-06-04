package com.example.sep4android.DATA.Models;

public class Temperature {
    private double value;
    //todo time variable, for future

    public Temperature(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Temperature)) {
            return false;
        }
        Temperature other = (Temperature) obj;
        return value == other.getValue();
    }

}