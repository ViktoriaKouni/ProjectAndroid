package com.example.sep4android.Repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.APIS.ArchiveAPI;
import com.example.sep4android.APIS.ConditionsResponse;
import com.example.sep4android.APIS.ServiceGenerator;
import com.example.sep4android.Models.Archive;
import com.example.sep4android.Models.ArchiveRoom;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArchiveRepository {



    private static ArchiveRepository instance;
    private MutableLiveData<Archive> archives;
    private MutableLiveData<ArchiveRoom> archive;


    private ArchiveRepository() {
        archives = new MutableLiveData<>();
    }

    public static synchronized ArchiveRepository getInstance() {
        if (instance == null) {
            instance = new ArchiveRepository();
        }
        return instance;
    }



    public LiveData<Archive> getAllArchiveRooms() {
        return archives;
    }


    public void getCo2Level(int roomNumber) {
        ArchiveAPI archiveApi = ServiceGenerator.getArchiveApi();
        Call<ConditionsResponse> call = archiveApi.getCo2Level(roomNumber);
        call.enqueue(new Callback<ConditionsResponse>()
        {
            @Override
            public void onResponse(Call<ConditionsResponse> call, Response<ConditionsResponse> response) {
                if (response.code() == 200) {
                    archive.setValue(response.body().getCo2Level());
                }
        }@Override
        public void onFailure(Call<ConditionsResponse> call, Throwable t) {
            Log.i("Retrofit", "Something went wrong :(");
        }
        });
    }
}

