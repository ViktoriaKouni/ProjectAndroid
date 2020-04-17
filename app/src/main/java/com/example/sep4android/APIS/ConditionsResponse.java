
package com.example.sep4android.APIS;

        import com.example.sep4android.Models.ArchiveRoom;
import com.example.sep4android.Models.CO2;

public class ConditionsResponse {

    private int roomNumber;
    private CO2 co2Level;


    public ArchiveRoom getCo2Level() {
        return new ArchiveRoom(roomNumber,co2Level);
    }
}
