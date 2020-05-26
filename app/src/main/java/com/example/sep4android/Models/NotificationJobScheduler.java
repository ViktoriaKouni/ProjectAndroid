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
                    //choose hardcoded data or api
                    //doWorkTest(params); //hardcoded
                    doWork(params);     //api
                }
            }, 30000);
            Log.i("Retrofit", " notification");
        }
        else
        {
            //choose hardcoded data or api
            //doWorkTest(params); //hardcoded
            doWork(params);     //api
        }
        return true;
    }
    public void doWork(final JobParameters params)
    {
        ArchiveAPI archiveApi = ServiceGenerator.getArchiveApi();
        Call<List<ArchiveResponse>> call = archiveApi.getAllArchiveRooms();
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
                                response.body().get(i).getCo2(),
                                response.body().get(i).getArchive().getOptimalValues());
                        roomList.add(local);
                    }
                    archiveRooms = roomList;
                    for(int i=0;i<archiveRooms.size();i++)
                    {
                        //current 5   optimal 3
                        double difference;
                        difference = archiveRooms.get(i).getCO2().getValue()-archiveRooms.get(i).getOptimalValues().getOptimalCO2();
                        if (difference>=offsetValue)
                        {
                            createNotification(archiveRooms.get(i),difference,true);
                            Log.i("Retrofit", "Create notification");
                        }
                        if (difference<0 && difference*(-1)>=offsetValue)
                        {
                            createNotification(archiveRooms.get(i),difference*(-1),false);
                            Log.i("Retrofit", "Create notification");
                        }
                    }
                    jobFinished(params,false);
                }
            }@Override
        public void onFailure(Call<List<ArchiveResponse>> call, Throwable t) {
            Log.i("Retrofit", "Something went wrong :(");
        }
        });
    }

    public void doWorkTest(JobParameters params)
    {
        // testing data
        ArchiveRoom room1 = new ArchiveRoom(2,"Gicu",new CO2(5),new OptimalValues(7));
        ArchiveRoom room2 = new ArchiveRoom(7,"Viktoria",new CO2(9),new OptimalValues(5));
        ArchiveRoom room3 = new ArchiveRoom(3,"Lyubovi",new CO2(69),new OptimalValues(69));
        ArrayList<ArchiveRoom> archiveRooms = new ArrayList<>();
        archiveRooms.add(room1);
        archiveRooms.add(room2);
        archiveRooms.add(room3);
        for(int i=0;i<archiveRooms.size();i++)
            {
                //current 5   optimal 3
                 double difference;
                 difference = archiveRooms.get(i).getCO2().getValue()-archiveRooms.get(i).getOptimalValues().getOptimalCO2();
                if (difference>=offsetValue)
                {
                    createNotification(archiveRooms.get(i),difference,true);
                    Log.i("Retrofit", "Create notification");
                }
                if (difference<0 && difference*(-1)>=offsetValue)
                {
                    createNotification(archiveRooms.get(i),difference*(-1),false);
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

    private void createNotification (ArchiveRoom archiveRoom,double difference,boolean higher) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE ) ;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext() , default_notification_channel_id ) ;
        mBuilder.setContentTitle( "Warning" ) ;
        if(higher == true)
        {
            mBuilder.setContentText( "CO2 level in room "+archiveRoom.getRoomName()+" is undesired, being higher by "+ difference + " current level: "+archiveRoom.getCO2().getValue() ) ;
            mBuilder.setTicker( "CO2 level in room "+archiveRoom.getRoomName()+" is undesired, being higher by "+ difference + " current level: "+archiveRoom.getCO2().getValue() ) ;
        }
        else if(!higher)
        {
            mBuilder.setContentText( "CO2 level in room "+archiveRoom.getRoomName()+" is undesired, being lower by "+ difference+" current level: "+archiveRoom.getCO2().getValue() ) ;
            mBuilder.setTicker( "CO2 level in room "+archiveRoom.getRoomName()+" is undesired, being lower by "+ difference+" current level: "+archiveRoom.getCO2().getValue() ) ;
        }
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
