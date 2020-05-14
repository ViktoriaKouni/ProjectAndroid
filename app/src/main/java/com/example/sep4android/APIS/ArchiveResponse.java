package com.example.sep4android.APIS;

import com.example.sep4android.Models.ArchiveRoom;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ArchiveResponse {


    @SerializedName("content")
    @Expose
    private ArrayList<ArchiveRoom> rooms;


    public ArrayList<ArchiveRoom> getAllArchives() {
        return rooms;
    }




}