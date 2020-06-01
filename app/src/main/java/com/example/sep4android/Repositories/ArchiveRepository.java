package com.example.sep4android.Repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.APIS.ArchiveAPI;
import com.example.sep4android.APIS.ArchiveResponse;
import com.example.sep4android.APIS.ConditionsResponse;
import com.example.sep4android.APIS.RoomResponse;
import com.example.sep4android.APIS.ServiceGenerator;
import com.example.sep4android.Models.ArchiveRoom;
import com.example.sep4android.Models.ArchiveRoomIndentification;
import com.example.sep4android.Models.CO2;
import com.example.sep4android.Models.Condition;
import com.example.sep4android.Models.Humidity;
import com.example.sep4android.Models.Temperature;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArchiveRepository
{
    private MutableLiveData<ArchiveRoom> room= new MutableLiveData<>();
    private MutableLiveData<ArrayList<ArchiveRoomIndentification>> rooms= new MutableLiveData<>();
    private MutableLiveData<ArrayList<Condition>> CO2Conditions= new MutableLiveData<>() ;
    private MutableLiveData<ArrayList<Condition>> temperatureConditions= new MutableLiveData<>();
    private MutableLiveData<ArrayList<Condition>> humidityConditions= new MutableLiveData<>();
    private MutableLiveData<Double> CO2Average= new MutableLiveData<>();
    private MutableLiveData<Double> temperatureAverage= new MutableLiveData<>();
    private MutableLiveData<Double> humidityAverage= new MutableLiveData<>();
    private static ArchiveRepository instance;
    private int roomNumberForUpdate;

    private ArchiveRepository()
    {
        getRooms(); //api
    }

    public LiveData<ArchiveRoom> getArchiveRoomLatestValue() { return room; }
    public LiveData<ArrayList<ArchiveRoomIndentification>> getArchiveRooms() {
        return rooms;
    }
    public LiveData<ArrayList<Condition>> getCO2ForDateInterval() {
        return CO2Conditions;
    }
    public LiveData<ArrayList<Condition>> getTemperatureForDateInterval() { return temperatureConditions; }
    public LiveData<ArrayList<Condition>> getHumidityForDateInterval() { return humidityConditions; }
    public LiveData<Double> getAverageCO2(){return CO2Average;}
    public LiveData<Double> getAverageTemperature(){return temperatureAverage;}
    public LiveData<Double> getAverageHumidity(){return humidityAverage;}


    public static ArchiveRepository getInstance()
    {
        if (instance == null) {
            instance = new ArchiveRepository();
        }
        return instance;
    }

  public void getRooms()
  {
        ArchiveAPI archiveApi = ServiceGenerator.getArchiveApi();
        Call<List<RoomResponse>> call = archiveApi.getAllArchiveRooms();
        call.enqueue(new Callback<List<RoomResponse>>()
        {
            @Override
            public void onResponse(Call<List<RoomResponse>> call, Response<List<RoomResponse>> response)
            {
               if (response.code() == 200 )
               {
                   ArrayList<ArchiveRoomIndentification> roomList = new ArrayList<>();
                   for(int i = 0;i<response.body().size();i++)
                   {
                       ArchiveRoomIndentification local;
                       local = new ArchiveRoomIndentification(response.body().get(i).getRoomName(),response.body().get(i).getRoomNumber());
                       roomList.add(local);
                   }
                   rooms.setValue(roomList);
               }
        }@Override
        public void onFailure(Call<List<RoomResponse>> call, Throwable t) {
            Log.i("Retrofit", "Something went wrong :("+ t.toString());
        }
        });
  }
    public void RoomUpdate()
    {
        getArchiveRoom(roomNumberForUpdate);
    }
    public void getArchiveRoom(int roomNumber)
    {
        roomNumberForUpdate =roomNumber;
        Log.i("Retrofit", "Something went wrong :(");
        ArchiveAPI archiveApi = ServiceGenerator.getArchiveApi();
        Call<ArchiveResponse> call = archiveApi.getArchiveRoomLatestValues(roomNumber);
        call.enqueue(new Callback<ArchiveResponse>()
        {
            @Override
            public void onResponse(Call<ArchiveResponse> call, Response<ArchiveResponse> response)
            {
                if (response.code() == 200 )
                {
                    ArchiveRoom local = new ArchiveRoom(response.body().getArchive().getRoomNumber(),
                            response.body().getArchive().getName(),
                            new CO2(response.body().getCo2()),
                            new Temperature(response.body().getTemperature()),
                            new Humidity(response.body().getHumidity()),
                            response.body().getArchive().getOptimalValues());
                    room.setValue(local);
                }
            }@Override
        public void onFailure(Call<ArchiveResponse> call, Throwable t) {
            Log.i("Retrofit", "Something went wrong :("+ t.toString());
        }
        });
    }

    public void getConditionForDateInterval(final String condition, String startDate, String endDate)
    {
        ArchiveAPI archiveApi = ServiceGenerator.getArchiveApi();
        Call<List<ConditionsResponse>> call = null;
        if(condition == "CO2")
        {
            call = archiveApi.getCO2ForDateInterval(roomNumberForUpdate,startDate,endDate);
        }
        if(condition == "Temperature")
        {
            call = archiveApi.getTemperatureForDateInterval(roomNumberForUpdate,startDate,endDate);
        }
        if(condition == "Humidity")
        {
            call = archiveApi.getHumidityForDateInterval(roomNumberForUpdate,startDate,endDate);
        }
        call.enqueue(new Callback<List<ConditionsResponse>>()
        {
            @Override
            public void onResponse(Call<List<ConditionsResponse>> call, Response<List<ConditionsResponse>> response)
            {
                if (response.code() == 200 )
                {
                    ArrayList<Condition> conditions = new ArrayList<>();
                    for(int i = 0;i<response.body().size();i++)
                    {
                        Condition local;
                        local = new Condition(response.body().get(i).getValue(),response.body().get(i).getDate());
                        conditions.add(local);
                    }
                    if(condition == "CO2")
                    {
                        CO2Conditions.setValue(conditions);
                    }
                    if(condition == "Temperature")
                    {
                        temperatureConditions.setValue(conditions);
                    }
                    if(condition == "Humidity")
                    {
                        humidityConditions.setValue(conditions);
                    }
                }
            }@Override
        public void onFailure(Call<List<ConditionsResponse>> call, Throwable t) {
            Log.i("Retrofit", "Something went wrong :("+ t.toString());
        }
        });
    }

    public void getConditionAverageForDateInterval(final String condition, String startDate, String endDate)
    {
        ArchiveAPI archiveApi = ServiceGenerator.getArchiveApi();
        Call<Double> call = null;
        if(condition == "CO2")
        {
            call = archiveApi.getAverageCO2ForDateInterval(roomNumberForUpdate,startDate,endDate);
        }
        if(condition == "Temperature")
        {
            call = archiveApi.getAverageTemperatureForDateInterval(roomNumberForUpdate,startDate,endDate);
        }
        if(condition == "Humidity")
        {
            call = archiveApi.getAverageHumidityForDateInterval(roomNumberForUpdate,startDate,endDate);
        }
        call.enqueue(new Callback<Double>()
        {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response)
            {
                if (response.code() == 200 )
                {
                    if(condition == "CO2")
                    {
                        CO2Average.setValue(response.body().doubleValue());
                    }
                    if(condition == "Temperature")
                    {
                        temperatureAverage.setValue(response.body().doubleValue());
                    }
                    if(condition == "Humidity")
                    {
                        humidityAverage.setValue(response.body().doubleValue());
                    }
                }
            }@Override
        public void onFailure(Call<Double> call, Throwable t) {
            Log.i("Retrofit", "Something went wrong :("+ t.toString());
        }
        });
    }

/*    public void getRooms()
    {
        ArchiveAPI archiveApi = ServiceGenerator.getArchiveApi();
        Call<List<ArchiveResponse>> call = archiveApi.getAllArchiveRoomsLatestValues();
        call.enqueue(new Callback<List<ArchiveResponse>>()
        {
            @Override
            public void onResponse(Call<List<ArchiveResponse>> call, Response<List<ArchiveResponse>> response)
            {
                if (response.code() == 200 )
                {
                    ArrayList<ArchiveRoom> roomList = new ArrayList<>();
                    for(int i = 0;i<response.body().size();i++)
                    {
                        ArchiveRoom local = new ArchiveRoom(response.body().get(i).getArchive().getRoomNumber(),
                                response.body().get(i).getArchive().getName(),
                                response.body().get(i).getCo2(),
                                response.body().get(i).getTemperature(),
                                response.body().get(i).getHumidity(),
                                response.body().get(i).getArchive().getOptimalValues());
                        roomList.add(local);
                    }
                    rooms.setValue(roomList);
                }
            }@Override
        public void onFailure(Call<List<ArchiveResponse>> call, Throwable t) {
            Log.i("Retrofit", "Something went wrong :("+ t.toString());
        }
        });
    }*/

/*    private void getRoomsTest( )
    {
        // testing data
        ArchiveRoom room1 = new ArchiveRoom(2,"Gicu",new CO2(5),new Temperature(9), new Humidity(13),new OptimalValues(7,4,3));
        ArchiveRoom room2 = new ArchiveRoom(7,"Viktoria",new CO2(9),new Temperature(4), new Humidity(16),new OptimalValues(5,6,7));
        ArchiveRoom room3 = new ArchiveRoom(3,"Lyubovi",new CO2(69),new Temperature(3), new Humidity(66),new OptimalValues(69,33,44));
        ArrayList<ArchiveRoom> archiveRooms = new ArrayList<>();
        archiveRooms.add(room1);
        archiveRooms.add(room2);
        archiveRooms.add(room3);
        rooms.setValue(archiveRooms);
        testChanges();
    }
    final Handler handler = new Handler();
    private void testChanges()
    {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArchiveRoom room1 = new ArchiveRoom(2,"Angel",new CO2(5),new Temperature(4),new Humidity(55),new OptimalValues(7,3,4));
                ArchiveRoom room2 = new ArchiveRoom(7,"Gay",new CO2(9),new Temperature(7),new Humidity(34),new OptimalValues(5,3,8));
                ArchiveRoom room3 = new ArchiveRoom(3,"True Story",new CO2(69),new Temperature(13),new Humidity(17),new OptimalValues(69,33,44));
                ArrayList<ArchiveRoom> archiveRooms = new ArrayList<>();
                archiveRooms.add(room1);
                archiveRooms.add(room2);
                archiveRooms.add(room3);
                rooms.setValue(archiveRooms);
            }
        }, 14000);
    }*/

}

