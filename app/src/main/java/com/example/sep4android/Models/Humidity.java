package com.example.sep4android.Models;

public class Humidity implements Condition
{
    private double value;

    public Humidity(double value)
    {
        this.value= value;
    }
    @Override
    public double getValue()
    {
        return value;
    }

}
