package com.example.sep4android.DATA.Models;

public class ArchiveRoomIndentification {
    private String roomName;
    private int roomNumber;

    public ArchiveRoomIndentification(String roomName, int roomNumber) {
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
