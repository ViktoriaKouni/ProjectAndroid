package com.example.sep4android.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4android.Models.ArchiveRoom;
import com.example.sep4android.Models.Condition;
import com.example.sep4android.Repositories.ArchiveRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConditionsViewModel extends AndroidViewModel {
    private ArchiveRepository repository;

    public ConditionsViewModel(@NonNull Application application) {
        super(application);
        repository = ArchiveRepository.getInstance();
    }
    public void getConditionForDateInterval(String condition, String startDate, String endDate)
    {
        repository.getConditionForDateInterval(condition,startDate,endDate);
    }
    public void getConditionAverageForDateInterval(String condition, String startDate, String endDate)
    {
        repository.getConditionAverageForDateInterval(condition,startDate,endDate);
    }

    public LiveData<ArchiveRoom> getArchiveRoomLatestValue(){
        return repository.getArchiveRoomLatestValue();
    }

    public LiveData<ArrayList<Condition>> getCO2ForDateInterval()
    {
        return repository.getCO2ForDateInterval();
    }
    public LiveData<ArrayList<Condition>> getTemperatureForDateInterval()
    {
        return repository.getTemperatureForDateInterval();
    }
    public LiveData<ArrayList<Condition>> getHumidityForDateInterval()
    {
        return repository.getHumidityForDateInterval();
    }
    public LiveData<Double> getAverageCO2()
    {
        return repository.getAverageCO2();
    }
    public LiveData<Double> getAverageTemperature()
    {
        return repository.getAverageTemperature();
    }
    public LiveData<Double> getAverageHumidity()
    {
        return repository.getAverageHumidity();
    }
}
