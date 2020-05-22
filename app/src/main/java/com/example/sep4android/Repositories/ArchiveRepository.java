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

import java.util.ArrayList;

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
        //getRoomsTest(); //hardcoded
        getRooms(); //api
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
        Call<ArchiveResponse> call = archiveApi.getAllArchiveRooms();
        call.enqueue(new Callback<ArchiveResponse>()
        {
            @Override
            public void onResponse(Call<ArchiveResponse> call, Response<ArchiveResponse> response)
            {
               if (response.code() == 200 && !rooms.equals(response.body().getAllArchives()))
               {
                   rooms.setValue(response.body().getAllArchives());
               }
        }@Override
        public void onFailure(Call<ArchiveResponse> call, Throwable t) {
            Log.i("Retrofit", "Something went wrong :(");
        }
        });
  }

    private void getRoomsTest( )
    {
        // testing data
        ArchiveRoom room1 = new ArchiveRoom(1,13);
        ArchiveRoom room2 = new ArchiveRoom(2,5);
        ArchiveRoom room3 = new ArchiveRoom(3,16);
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
                ArchiveRoom room1 = new ArchiveRoom(2,13);
                ArchiveRoom room2 = new ArchiveRoom(7,5);
                ArchiveRoom room3 = new ArchiveRoom(3,16);
                ArrayList<ArchiveRoom> archiveRooms = new ArrayList<>();
                archiveRooms.add(room1);
                archiveRooms.add(room2);
                archiveRooms.add(room3);
                rooms.setValue(archiveRooms);
            }
        }, 5000);
    }

}

