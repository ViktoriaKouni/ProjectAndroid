package com.example.sep4android.Repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.APIS.ArchiveAPI;
import com.example.sep4android.APIS.ArchiveResponse;
import com.example.sep4android.APIS.ServiceGenerator;
import com.example.sep4android.Models.ArchiveRoom;
import com.example.sep4android.Models.CO2;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArchiveRepository {


    private MutableLiveData<ArrayList<ArchiveRoom>> rooms= new MutableLiveData<>();
    private MutableLiveData<ArrayList<ArchiveRoom>> roomsTest= new MutableLiveData<>();
    private static ArchiveRepository instance;


    private ArchiveRepository() {
        CO2 co21 = new CO2(13);
        CO2 co22 = new CO2(5);
        CO2 co23 = new CO2(16);
        ArchiveRoom room1 = new ArchiveRoom(1,co21);
        ArchiveRoom room2 = new ArchiveRoom(2,co22);
        ArchiveRoom room3 = new ArchiveRoom(3,co23);
        ArrayList<ArchiveRoom> archiveRooms = new ArrayList<>();
        archiveRooms.add(room1);
        archiveRooms.add(room2);
        archiveRooms.add(room3);
        roomsTest.setValue(archiveRooms);
        getRooms();
    }

    public LiveData<ArrayList<ArchiveRoom>> getArchiveRooms() {
        return roomsTest;
    }

    public static ArchiveRepository getInstance() {
        if (instance == null) {
            instance = new ArchiveRepository();
        }
        return instance;
    }



  private void getRooms() {
        ArchiveAPI archiveApi = ServiceGenerator.getArchiveApi();
        Call<ArchiveResponse> call = archiveApi.getAllArchiveRooms();
        call.enqueue(new Callback<ArchiveResponse>()

        {
            @Override
            public void onResponse(Call<ArchiveResponse> call, Response<ArchiveResponse> response) {
               if (response.code() == 200) {
                   rooms.setValue(response.body().getAllArchives());
                }
        }@Override
        public void onFailure(Call<ArchiveResponse> call, Throwable t) {
            Log.i("Retrofit", "Something went wrong :(");
        }
        });
    }
}

