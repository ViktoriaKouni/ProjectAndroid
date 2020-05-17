package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4android.Models.ArchiveRoom;
import com.example.sep4android.Repositories.ArchiveRepository;

import java.util.ArrayList;

public class ConditionsViewModel extends AndroidViewModel {
    private ArchiveRepository repository;

    public ConditionsViewModel(@NonNull Application application) {
        super(application);
        repository = ArchiveRepository.getInstance();
    }

    public int getArchiveRoomCO2Level(int roomNumber){
        return repository.getArchiveRoomCO2Level(roomNumber);
    }
}
