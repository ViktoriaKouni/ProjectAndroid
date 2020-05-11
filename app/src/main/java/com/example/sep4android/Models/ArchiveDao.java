package com.example.sep4android.Models;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.sep4android.Models.NotificationService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class ArchiveDao {

    private MutableLiveData<List<ArchiveRoom>> archiveRooms;
    private static ArchiveDao instance;
    private ArchiveRoom warningRoom;

    public ArchiveRoom getWarningRoom()
    {
        return warningRoom;
    }
    private ArchiveDao()
    {
        CO2 warningLevel = new CO2(-99);
        warningRoom = new ArchiveRoom(1,warningLevel);

        archiveRooms = new MutableLiveData<>();
        List<ArchiveRoom> roomList = new ArrayList<>();
        archiveRooms.setValue(roomList);

 // Dummy data for local storage/ testing
        warningRoom.getCO2().setlevel(5);
        CO2 co2 = new CO2(5);
        ArchiveRoom archiveRoom = new ArchiveRoom(1,co2);
        List<ArchiveRoom> currentRooms = archiveRooms.getValue();
        currentRooms.add(archiveRoom);
        archiveRooms.setValue(currentRooms);
/*        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CO2 co21 = new CO2(7);
        ArchiveRoom archiveRoom1 = new ArchiveRoom(1,co21);
        List<ArchiveRoom> currentRooms1 = new ArrayList<>();
        currentRooms1.add(archiveRoom1);
        archiveRooms.setValue(currentRooms1);*/

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
