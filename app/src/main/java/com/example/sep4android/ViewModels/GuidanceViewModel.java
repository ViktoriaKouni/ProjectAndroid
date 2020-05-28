package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4android.Models.Guidance;
import com.example.sep4android.Repositories.GuidanceRepository;

import java.util.List;

public class GuidanceViewModel extends AndroidViewModel {
    private GuidanceRepository repository;

    public GuidanceViewModel(@NonNull Application application) {
        super(application);
        repository = GuidanceRepository.getInstance(application);
    }

    public LiveData<List<Guidance>> getGuidanceCO2(){
        return repository.getAllGuidanceCO2();
    }
}
