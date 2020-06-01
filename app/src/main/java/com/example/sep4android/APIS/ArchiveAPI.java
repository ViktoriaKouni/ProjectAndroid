package com.example.sep4android.APIS;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ArchiveAPI {
    @GET("/api/archive")
    Call<List<RoomResponse>> getAllArchiveRooms();

    @GET("/api/archive/latestValues")
    Call<List<ArchiveResponse>> getAllArchiveRoomsLatestValues();

    @GET("/api/archive/{roomNumber}/latestValuesByArchiveId")
    Call<ArchiveResponse> getArchiveRoomLatestValues(@Path("roomNumber") int roomNumber);


    @GET("/api/archive/{archive_id}/temperature/dateInterval")
    Call<List<ConditionsResponse>> getTemperatureForDateInterval(@Path("archive_id") int roomNumber, @Query("startDate") String startDate, @Query("endDate") String endDate);

    @GET("/api/archive/{archive_id}/humidity/dateInterval")
    Call<List<ConditionsResponse>> getHumidityForDateInterval(@Path("archive_id") int roomNumber, @Query("startDate") String startDate, @Query("endDate") String endDate);

    @GET("/api/archive/{archive_id}/carbondioxide/dateInterval")
    Call<List<ConditionsResponse>> getCO2ForDateInterval(@Path("archive_id") int roomNumber, @Query("startDate") String startDate, @Query("endDate") String endDate);


    @GET("/api/archive/{archive_id}/averageTemperature/dateInterval")
    Call<Double> getAverageTemperatureForDateInterval(@Path("archive_id") int roomNumber, @Query("startDate") String startDate, @Query("endDate") String endDate);

    @GET("/api/archive/{archive_id}/averageHumidity/dateInterval")
    Call<Double> getAverageHumidityForDateInterval(@Path("archive_id") int roomNumber, @Query("startDate") String startDate, @Query("endDate") String endDate);

    @GET("/api/archive/{archive_id}/averageCarbondioxide/dateInterval")
    Call<Double> getAverageCO2ForDateInterval(@Path("archive_id") int roomNumber, @Query("startDate") String startDate, @Query("endDate") String endDate);
}