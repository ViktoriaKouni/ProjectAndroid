package com.example.sep4android.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sep4android.Models.ArchiveRoom;
import com.example.sep4android.Repositories.ArchiveRepository;

import java.util.List;

public class CO2ViewModel extends ViewModel {

    private ArchiveRepository repository;

    public CO2ViewModel()
    {
        repository = ArchiveRepository.getInstance();
    }

    public LiveData<List<ArchiveRoom>> getArchiveRooms()
    {
        return repository.getArchiveRooms();
    }
}
