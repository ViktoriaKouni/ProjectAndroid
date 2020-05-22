package com.example.sep4android.APIS;

import com.example.sep4android.Models.ArchiveRoom;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ArchiveAPI {
    @GET("/api/archive/latestValues")
    Call<List<ArchiveResponse>> getAllArchiveRooms();


    @GET("api/v2/archive/GetCo2/{roomNumber}")
    Call<ConditionsResponse> getRoomConditionLevel(@Path("roomNumber") int roomNumber);
}