package com.example.sep4android.UI.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4android.DATA.Models.Guidance;
import com.example.sep4android.DATA.Repositories.GuidanceRepository;

import java.util.List;

public class GuidanceViewModel extends AndroidViewModel {
    private GuidanceRepository repository;
    private LiveData<List<Guidance>> mListLiveData;

    public GuidanceViewModel(@NonNull Application application) {
        super(application);
        repository = GuidanceRepository.getInstance(application);
        mListLiveData = repository.getAllGuidanceCO2();
    }

    public LiveData<List<Guidance>> getGuidanceCO2() {
        return repository.getAllGuidanceCO2();
    }

    public LiveData<List<Guidance>> getGuidanceHumidity() {
        return repository.getAllGuidanceHumidity();
    }

    public LiveData<List<Guidance>> getGuidanceTemperature() {
        return repository.getAllGuidanceTemperature();
    }

    public void insert(final Guidance guidance) {
        repository.insert(guidance);
    }

    public void update(final Guidance guidance) {
        repository.update(guidance);
    }

    public void delete(final Guidance guidance) {
        repository.delete(guidance);
    }
}
