package com.example.sep4android.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CO2 {


    @SerializedName("value")
    @Expose
    private int level;

    public CO2(int level){
        this.level=level;
    }

    public int getLevel() {
        return level;
    }

    public void setlevel(int level) {
        this.level = level;
    }
}
