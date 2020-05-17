package com.example.sep4android.Views;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.Adapters.RoomAdapter;
import com.example.sep4android.Fragments.CO2Fragment;
import com.example.sep4android.Models.ArchiveRoom;
import com.example.sep4android.Models.NotificationService;
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
        System.out.println(viewModel.getArchiveRooms());


    }

    public void setViewModel() {
        viewModel = new ViewModelProvider(this).get(ArchiveViewModel.class);

        viewModel.getArchiveRooms().observe(this, new Observer<List<ArchiveRoom>>() {

            @Override
            public void onChanged(List<ArchiveRoom> rooms) {
                adapter.setRooms(rooms);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onListItemClicked(int clickedItemIndex) {
        Intent intent = new Intent(this, ConditionActivity.class);
        intent.putExtra("number", clickedItemIndex+1);
        startActivity(intent);
    }

    @Override
    protected void onStop () {
        super.onStop() ;
        startService( new Intent( this, NotificationService. class )) ;
    }
}
