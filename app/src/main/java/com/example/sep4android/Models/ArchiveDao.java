package com.example.sep4android.Models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class ArchiveDao {

    private MutableLiveData<List<ArchiveRoom>> archiveRooms;
    private static ArchiveDao instance;

    private ArchiveDao()
    {
        archiveRooms = new MutableLiveData<>();
        List<ArchiveRoom> roomList = new ArrayList<>();
        archiveRooms.setValue(roomList);

 // Dummy data for local storage/ testing
        CO2 co2 = new CO2(5);
        ArchiveRoom archiveRoom = new ArchiveRoom(1,co2);

    }

    public static ArchiveDao getInstance()
    {
        if(instance==null)
        {
            instance = new ArchiveDao();
        }
        return instance;
    }

    public LiveData<List<ArchiveRoom>> getArchiveRooms() {return archiveRooms;}

   /* public LiveData<ArchiveRoom> getArchiveRoom(int number)
    {
        for(int i=0;i<archiveRooms.getValue().size();i++)
        {
            if (archiveRooms.getValue().get(i).getRoomNumber()==number)
            {
                return archiveRooms.getValue().get(i);
            }
        }
    }*/

}
