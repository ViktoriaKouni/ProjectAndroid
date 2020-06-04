package com.example.sep4android.Models;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.example.sep4android.Repositories.ArchiveRepository;

import java.util.Timer;
import java.util.TimerTask;

public class UpdateService extends IntentService {
    private Timer timer;
    private TimerTask timerTask;
    private int seconds = 300;
    private int delay = 300;
    private ArchiveRepository archive;


    public UpdateService() {
        super("UpdateService");
        setIntentRedelivery(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        archive = ArchiveRepository.getInstance();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        startTimer();
    }

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();

    public void startTimer() {
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, delay * 1000, seconds * 1000); //
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        archive.getRooms();
                        archive.RoomUpdate();
                    }
                });
            }
        };
    }
}