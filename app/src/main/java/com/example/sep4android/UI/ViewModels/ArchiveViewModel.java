package com.example.sep4android.UI.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4android.DATA.Models.ArchiveRoomIndentification;
import com.example.sep4android.DATA.Repositories.ArchiveRepository;

import java.util.ArrayList;

public class ArchiveViewModel extends AndroidViewModel {

    private ArchiveRepository repository;


    public ArchiveViewModel(@NonNull Application application) {
        super(application);
        repository = ArchiveRepository.getInstance();
    }

    public LiveData<ArrayList<ArchiveRoomIndentification>> getArchiveRooms() {
        return repository.getArchiveRooms();
    }

    public void getArchiveRoom(int roomNumber) {
        repository.getArchiveRoom(roomNumber);
    }

}
