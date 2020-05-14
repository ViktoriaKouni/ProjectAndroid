package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.sep4android.Repositories.ArchiveRepository;

public class CO2ViewModel extends AndroidViewModel {

    private ArchiveRepository repository;

    public CO2ViewModel(@NonNull Application application)
    {
        super(application);
        repository = ArchiveRepository.getInstance(); }

}
