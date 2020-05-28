package com.example.sep4android.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "guidance_table")
public class Guidance {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String type;
    private String description;
    static String CO2="CO2";

    public Guidance(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}