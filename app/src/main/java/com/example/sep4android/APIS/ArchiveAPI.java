package com.example.sep4android.APIS;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ArchiveAPI {
    @GET("/api/archive/GetArchiveRooms/")
    Call<ArchiveResponse> getAllArchiveRooms();


    @GET("api/v2/archive/GetCo2/{roomNumber}")
    Call<ConditionsResponse> getCo2Level(@Path("roomNumber") int roomNumber);
}

