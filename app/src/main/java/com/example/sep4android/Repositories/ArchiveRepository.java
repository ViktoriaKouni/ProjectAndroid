package com.example.sep4android.Repositories;

import android.app.Application;
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

public class ArchiveRepository {


    private MutableLiveData<ArrayList<ArchiveRoom>> rooms= new MutableLiveData<>();
    private static ArchiveRepository instance;


    private ArchiveRepository(Application application) {
        getRooms();
    }

    public LiveData<ArrayList<ArchiveRoom>> getArchiveRooms() {
        return rooms;
    }

    public static ArchiveRepository getInstance(Application application) {
        if (instance == null) {
            instance = new ArchiveRepository(application);
        }
        return instance;
    }


  public void getRooms() {
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

