package com.example.sep4android.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.sep4android.Models.Guidance;

import java.util.List;


@Dao
public interface GuidanceDao {

    @Insert
    void insert(Guidance guidance);

    @Update
    void update(Guidance guidance);

    @Delete
    void delete(Guidance guidance);

    @Query("DELETE FROM guidance_table")
    void deleteAllGuidance();

    @Query("SELECT * FROM guidance_table  WHERE type == 'CO2'; ")
    LiveData<List<Guidance>> getAllCO2Guidance();

    @Query("SELECT * FROM guidance_table  WHERE type == 'Temperature'; ")
    LiveData<List<Guidance>> getAllTemperatureGuidance();

    @Query("SELECT * FROM guidance_table  WHERE type == 'Humidity'; ")
    LiveData<List<Guidance>> getAllHumidityGuidance();

}

