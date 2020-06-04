package com.example.sep4android.Views;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.Adapters.RoomAdapter;
import com.example.sep4android.Models.ArchiveRoomIndentification;
import com.example.sep4android.Models.NotificationJobScheduler;
import com.example.sep4android.Models.UpdateService;
import com.example.sep4android.R;
import com.example.sep4android.ViewModels.ArchiveViewModel;

import java.util.List;

public class MainActivity  extends AppCompatActivity implements RoomAdapter.OnListItemClickedListener {

    private RoomAdapter adapter;
    private ArchiveViewModel viewModel;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViewModel();
        recyclerView = findViewById(R.id.rooms);
        adapter = new RoomAdapter( this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    public void setViewModel() {
        viewModel = new ViewModelProvider(this).get(ArchiveViewModel.class);

        viewModel.getArchiveRooms().observe(this, new Observer<List<ArchiveRoomIndentification>>() {

            @Override
            public void onChanged(List<ArchiveRoomIndentification> rooms) {
                adapter.setRooms(rooms);
            }
        });
    }

    @Override
    public void onListItemClicked(int roomNumber) {
        viewModel.getArchiveRoom(roomNumber);
        Intent intent = new Intent(this, ConditionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        scheduleNotifications();
        scheduleUpdates();
    }

    private void scheduleNotifications() {
        ComponentName name = new ComponentName(this, NotificationJobScheduler.class);
        JobInfo info = new JobInfo.Builder(1,name)
                .setPersisted(true)
                .setPeriodic(15*60*1000)
                .build();
        JobScheduler job = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        job.schedule(info);
        Log.i("Retrofit", "Start notification scheduler");
    }

    private void scheduleUpdates()
    {
        startService( new Intent( this, UpdateService. class )) ;
    }

}
