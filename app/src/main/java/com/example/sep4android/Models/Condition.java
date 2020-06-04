package com.example.sep4android.Models;

import java.util.Date;

public class Condition {
    private double value;
    private Date date;

    public Condition(double value, Date date) {
        this.value = value;
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public Date getDate() {
        return date;
    }
}
