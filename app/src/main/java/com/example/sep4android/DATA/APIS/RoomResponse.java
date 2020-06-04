package com.example.sep4android.DATA.APIS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoomResponse {
    @SerializedName("name")
    @Expose
    private String roomName;
    @SerializedName("id")
    @Expose
    private int roomNumber;

    public RoomResponse(String roomName, int roomNumber) {
        this.roomName = roomName;
        this.roomNumber = roomNumber;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }
}

