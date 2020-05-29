package com.example.sep4android.Models;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.sep4android.APIS.ArchiveAPI;
import com.example.sep4android.APIS.ArchiveResponse;
import com.example.sep4android.APIS.ServiceGenerator;
import com.example.sep4android.R;
import com.example.sep4android.Repositories.ArchiveRepository;
import com.example.sep4android.Views.MainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationJobScheduler extends JobService {
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    private boolean jobCancelled = false;
    private List<ArchiveRoom> archiveRooms;
    ArchiveRepository archive;
    private int offsetValue =1;
    private boolean first = false;

    @Override
    public void onCreate()
    {
        archive = ArchiveRepository.getInstance();
    }

    @Override
    public boolean onStartJob(final JobParameters params) {
        if(first==false) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after delay for first run
                    first = true;
                    doWork(params);     //api
                }
            }, 30000);
            Log.i("Retrofit", " notification");
        }
        else
        {
            doWork(params);     //api
        }
        return true;
    }
    public void doWork(final JobParameters params)
    {
        ArchiveAPI archiveApi = ServiceGenerator.getArchiveApi();
        Call<List<ArchiveResponse>> call = archiveApi.getAllArchiveRoomsLatestValues();
        call.enqueue(new Callback<List<ArchiveResponse>>()
        {
            @Override
            public void onResponse(Call<List<ArchiveResponse>> call, Response<List<ArchiveResponse>> response) {
                if (response.code() == 200) {
                    ArrayList<ArchiveRoom> roomList = new ArrayList<>();
                    for(int i = 0;i<response.body().size();i++)
                    {
                        ArchiveRoom local = new ArchiveRoom(response.body().get(i).getArchive().getRoomNumber(),
                                response.body().get(i).getArchive().getName(),
                                new CO2(response.body().get(i).getCo2()),
                                new Temperature(response.body().get(i).getTemperature()),
                                new Humidity(response.body().get(i).getHumidity()),
                                response.body().get(i).getArchive().getOptimalValues());
                        roomList.add(local);
                    }
                    archiveRooms = roomList;
                    checkDataForNotification();
                    jobFinished(params,false);
                }
            }@Override
        public void onFailure(Call<List<ArchiveResponse>> call, Throwable t) {
            Log.i("Retrofit", "Something went wrong :(");
        }
        });
    }


    @Override
    public boolean onStopJob(JobParameters params) {
        jobCancelled = true;
        return true;
    }

    void checkDataForNotification()
    {
        for(int i=0;i<archiveRooms.size();i++) {
            double differenceCO2;
            double differenceTemperature;
            double differenceHumidity;
            differenceCO2 = archiveRooms.get(i).getCO2().getValue() - archiveRooms.get(i).getOptimalValues().getOptimalCO2();
            differenceTemperature = archiveRooms.get(i).getTemperature().getValue() - archiveRooms.get(i).getOptimalValues().getOptimalTemperature();
            differenceHumidity = archiveRooms.get(i).getHumidity().getValue() - archiveRooms.get(i).getOptimalValues().getOptimalHumidity();
            if (differenceCO2 != 0 || differenceTemperature != 0 || differenceHumidity != 0) {
                String notificationText;
                notificationText = "Following measurements are undesired in room " + archiveRooms.get(i).getRoomName() + ":";
                if (differenceCO2 >= offsetValue) {
                    notificationText += "\nCO2 level is higher by " + differenceCO2 + " currently being " + archiveRooms.get(i).getCO2().getValue();
                }
                if (differenceCO2 < 0 && differenceCO2 * (-1) >= offsetValue) {
                    notificationText += "\nCO2 level is lower by " + differenceCO2 * (-1) + " currently being " + archiveRooms.get(i).getCO2().getValue();
                }
                if (differenceTemperature >= offsetValue) {
                    notificationText += "\nTemperature level is higher by " + differenceTemperature + " currently being " + archiveRooms.get(i).getTemperature().getValue();
                }
                if (differenceTemperature < 0 && differenceTemperature * (-1) >= offsetValue) {
                    notificationText += "\nTemperature level is lower by " + differenceTemperature * (-1) + " currently being " + archiveRooms.get(i).getTemperature().getValue();
                }
                if (differenceHumidity >= offsetValue) {
                    notificationText += "\nHumidity level is higher by " + differenceHumidity + " currently being " + archiveRooms.get(i).getHumidity().getValue();
                }
                if (differenceHumidity < 0 && differenceHumidity * (-1) >= offsetValue) {
                    notificationText += "\nHumidity level is lower by " + differenceHumidity * (-1) + " currently being " + archiveRooms.get(i).getHumidity().getValue();
                }
                createNotification(notificationText);
            }
        }
    }

    private void createNotification (String notificationText) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE ) ;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext() , default_notification_channel_id ) ;
        mBuilder.setContentTitle( "Warning" ) ;
        mBuilder.setTicker( "Undesired measurements" ) ;
        mBuilder.setContentText(notificationText);
        mBuilder.setStyle(new NotificationCompat.BigTextStyle());
        mBuilder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
        mBuilder.setAutoCancel( true ) ;
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build()) ;
    }
}
