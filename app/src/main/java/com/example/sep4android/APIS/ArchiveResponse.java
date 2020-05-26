package com.example.sep4android.APIS;

import com.example.sep4android.Models.CO2;
import com.example.sep4android.Models.Humidity;
import com.example.sep4android.Models.OptimalValues;
import com.example.sep4android.Models.Temperature;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArchiveResponse {



    private Archive archive;
    @SerializedName("carbonDioxide")
    @Expose
    private CO2 co2;
    private Temperature temperature;
    private Humidity humidity;

    public class Archive
    {
        @SerializedName("id")
        @Expose
        private int roomNumber;
        private String name;
        @SerializedName("optimalValuesEntity")
        @Expose
        private OptimalValues optimalValues;

        public int getRoomNumber() {
            return roomNumber;
        }

        public String getName() {
            return name;
        }

        public OptimalValues getOptimalValues() {
            return optimalValues;
        }
    }

    public Archive getArchive() {
        return archive;
    }

    public CO2 getCo2() {
        return co2;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public Humidity getHumidity() {
        return humidity;
    }

}