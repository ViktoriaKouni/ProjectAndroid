package com.example.sep4android.Repositories;

import android.app.job.JobParameters;
import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.APIS.ArchiveAPI;
import com.example.sep4android.APIS.ArchiveResponse;
import com.example.sep4android.APIS.ServiceGenerator;
import com.example.sep4android.Models.ArchiveRoom;
import com.example.sep4android.Models.CO2;
import com.example.sep4android.Models.OptimalValues;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArchiveRepository
{
    private MutableLiveData<ArrayList<ArchiveRoom>> rooms= new MutableLiveData<>();
    private static ArchiveRepository instance;

    private ArchiveRepository()
    {
        //choose hardcoded data or api
        getRoomsTest(); //hardcoded
        //getRooms(); //api
        testChanges();
    }

    public LiveData<ArrayList<ArchiveRoom>> getArchiveRooms() {
        return rooms;
    }

    public static ArchiveRepository getInstance()
    {
        if (instance == null) {
            instance = new ArchiveRepository();
        }
        return instance;
    }

  public void getRooms()
  {
        ArchiveAPI archiveApi = ServiceGenerator.getArchiveApi();
        Call<List<ArchiveResponse>> call = archiveApi.getAllArchiveRooms();
        call.enqueue(new Callback<List<ArchiveResponse>>()
        {
            @Override
            public void onResponse(Call<List<ArchiveResponse>> call, Response<List<ArchiveResponse>> response)
            {
               if (response.code() == 200 )
               {
                   ArrayList<ArchiveRoom> roomList = new ArrayList<>();
                   for(int i = 0;i<response.body().size();i++)
                   {
                       ArchiveRoom local = new ArchiveRoom(response.body().get(i).getArchive().getRoomNumber(),
                                                            response.body().get(i).getArchive().getName(),
                                                            response.body().get(i).getCo2(),
                                                            response.body().get(i).getArchive().getOptimalValues());
                       roomList.add(local);
                   }
                   rooms.setValue(roomList);
               }
        }@Override
        public void onFailure(Call<List<ArchiveResponse>> call, Throwable t) {
            Log.i("Retrofit", "Something went wrong :("+ t.toString());
        }
        });
  }

    private void getRoomsTest( )
    {
        // testing data
        ArchiveRoom room1 = new ArchiveRoom(2,"Gicu",new CO2(5),new OptimalValues(7));
        ArchiveRoom room2 = new ArchiveRoom(7,"Viktoria",new CO2(9),new OptimalValues(5));
        ArchiveRoom room3 = new ArchiveRoom(3,"Lyubovi",new CO2(69),new OptimalValues(69));
        ArrayList<ArchiveRoom> archiveRooms = new ArrayList<>();
        archiveRooms.add(room1);
        archiveRooms.add(room2);
        archiveRooms.add(room3);
        rooms.setValue(archiveRooms);
        testChanges();
    }
    final Handler handler = new Handler();
    private void testChanges()
    {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArchiveRoom room1 = new ArchiveRoom(2,"Angel",new CO2(5),new OptimalValues(7));
                ArchiveRoom room2 = new ArchiveRoom(7,"Gay",new CO2(9),new OptimalValues(5));
                ArchiveRoom room3 = new ArchiveRoom(3,"True Story",new CO2(69),new OptimalValues(69));
                ArrayList<ArchiveRoom> archiveRooms = new ArrayList<>();
                archiveRooms.add(room1);
                archiveRooms.add(room2);
                archiveRooms.add(room3);
                rooms.setValue(archiveRooms);
            }
        }, 14000);
    }

}

