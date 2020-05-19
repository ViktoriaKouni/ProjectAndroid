package com.example.sep4android.Models;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.NonNull;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleService;

import androidx.lifecycle.Observer;


import com.example.sep4android.R;
import com.example.sep4android.Repositories.ArchiveRepository;
import com.example.sep4android.Views.MainActivity;


import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends LifecycleService {
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    private  boolean state = false;
    Timer timer ;
    TimerTask timerTask ;
    int seconds = 360 ;
    private int id=0;
    private List<ArchiveRoom> archiveRooms;

    @Override
    public void onCreate() {
        super.onCreate();
        ArchiveRepository archive = ArchiveRepository.getInstance();
        archive.getArchiveRooms().observe(this, new Observer<List<ArchiveRoom>>() {
            @Override
            public void onChanged(List<ArchiveRoom> rooms) {
                archiveRooms = rooms;
            }
        });
    }

    public IBinder onBind (@NonNull Intent arg0) {
        super.onBind(arg0);
        return null;
    }
    @Override
    public int onStartCommand (Intent intent , int flags , int startId) {
        if(!state) {
            state = true;
            super.onStartCommand(intent, flags, startId);
            startTimer();
            return START_STICKY;
        }
        return START_STICKY;
    }
    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler() ;
    public void startTimer () {
        timer = new Timer() ;
        initializeTimerTask() ;
        timer.schedule( timerTask , 5000 , seconds * 1000 ) ; //
    }

    public void initializeTimerTask () {
        timerTask = new TimerTask() {
            public void run () {
                handler.post( new Runnable() {
                    public void run () {
                        for(int i=0;i<archiveRooms.size();i++) {
                            if (archiveRooms.get(i).getCO2().getValue()>7) {
                                createNotification(archiveRooms.get(i));
                            }
                        }
                    }
                }) ;
            }
        } ;
    }
    private void createNotification (ArchiveRoom archiveRoom) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE ) ;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext() , default_notification_channel_id ) ;
        mBuilder.setContentTitle( "Warning" ) ;
        mBuilder.setContentText( "ID "+id+" CO2 level in room "+archiveRoom.getRoomNumber()+" is undesired, current level: "+archiveRoom.getCO2().getValue() ) ;
        mBuilder.setTicker( "CO2 level in room "+archiveRoom.getRoomNumber()+" is undesired, current level: "+archiveRoom.getCO2().getValue() ) ;
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
        id++;
        mNotificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build()) ;
    }
}