package com.example.sep4android.Models;

public class Temperature implements Condition
{
    private double value;
    //todo time variable, for future

    public Temperature(double value) { this.value = value; }

    @Override
    public double getValue() {
        return value;    }

}