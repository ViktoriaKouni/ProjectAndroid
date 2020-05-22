package com.example.sep4android.Models;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
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

    @Override
    public void onCreate()
    {
        archive = ArchiveRepository.getInstance();
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i("Retrofit", " notification");

        //choose hardcoded data or api
        doWorkTest(params); //hardcoded
        //doWork(params); //api

        return true;
    }
    public void doWork(final JobParameters params)
    {
        ArchiveAPI archiveApi = ServiceGenerator.getArchiveApi();
        Call<ArchiveResponse> call = archiveApi.getAllArchiveRooms();
        call.enqueue(new Callback<ArchiveResponse>()
        {
            @Override
            public void onResponse(Call<ArchiveResponse> call, Response<ArchiveResponse> response) {
                if (response.code() == 200) {
                    archiveRooms =response.body().getAllArchives();
                    for(int i=0;i<archiveRooms.size();i++)
                    {
                        if (archiveRooms.get(i).getCO2()>7)
                        {
                            createNotification(archiveRooms.get(i));
                            Log.i("Retrofit", "Create notification");
                        }
                    }
                    jobFinished(params,false);
                }
            }@Override
        public void onFailure(Call<ArchiveResponse> call, Throwable t) {
            Log.i("Retrofit", "Something went wrong :(");
        }
        });
    }

    public void doWorkTest(JobParameters params)
    {
        // testing data
        ArchiveRoom room1 = new ArchiveRoom(1,13);
        ArchiveRoom room2 = new ArchiveRoom(2,5);
        ArchiveRoom room3 = new ArchiveRoom(3,16);
        ArrayList<ArchiveRoom> archiveRooms = new ArrayList<>();
        archiveRooms.add(room1);
        archiveRooms.add(room2);
        archiveRooms.add(room3);
        for(int i=0;i<archiveRooms.size();i++)
            {
                if (archiveRooms.get(i).getCO2()>7)
                {
                    createNotification(archiveRooms.get(i));
                    Log.i("Retrofit", "Create notification");
                }
            }
        jobFinished(params,false);
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        jobCancelled = true;
        return true;
    }

    private void createNotification (ArchiveRoom archiveRoom) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE ) ;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext() , default_notification_channel_id ) ;
        mBuilder.setContentTitle( "Warning" ) ;
        mBuilder.setContentText( "CO2 level in room "+archiveRoom.getRoomNumber()+" is undesired, current level: "+archiveRoom.getCO2() ) ;
        mBuilder.setTicker( "CO2 level in room "+archiveRoom.getRoomNumber()+" is undesired, current level: "+archiveRoom.getCO2() ) ;
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
