package com.example.sep4android.Models;

public class CO2
{
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
