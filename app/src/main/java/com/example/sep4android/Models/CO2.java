package com.example.sep4android.Models;

public class CO2 implements Condition
{


    private double value;

    public CO2(double value){
        this.value=value;
    }

    @Override
    public double getValue() {
        return value;
    }

}
