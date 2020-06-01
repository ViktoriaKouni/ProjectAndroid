package com.example.sep4android.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.Adapters.GuidanceAdapter;
import com.example.sep4android.Models.Guidance;
import com.example.sep4android.R;
import com.example.sep4android.ViewModels.GuidanceViewModel;
import com.example.sep4android.Views.GuidanceActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class GuidanceHumidityFragment extends Fragment implements GuidanceAdapter.OnListItemClickListener {
    private GuidanceViewModel guidanceViewModel;
    private RecyclerView guidanceRecyclerView;
    private EditText description;
    private FloatingActionButton addNewItem;
    private GuidanceAdapter guidanceAdapter;

    public GuidanceHumidityFragment(GuidanceActivity guidanceActivity, GuidanceViewModel guidanceViewModel) {
        this.guidanceViewModel = guidanceViewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_guidance_co2, container, false);
        guidanceRecyclerView = rootView.findViewById(R.id.guidance_list_co2);
        guidanceRecyclerView.hasFixedSize();
        guidanceRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        setHasOptionsMenu(true);


        guidanceAdapter = new GuidanceAdapter(this);
        guidanceRecyclerView.setAdapter(guidanceAdapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.add_guidance, null);
        description = view.findViewById(R.id.guidance_description_dialog);
        builder.setView(view);

        builder.setPositiveButton("Add New Guidance", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String txt = description.getText().toString();
                System.out.println("added");
                guidanceViewModel.insert(new Guidance("Humidity", txt));

            }
        });

        builder.setNegativeButton("Never mind", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        final AlertDialog ad = builder.create();
        addNewItem = rootView.findViewById(R.id.guidance_fab);
        addNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.show();

            }
        });

        guidanceViewModel.getGuidanceHumidity().observe(this.getActivity(), new Observer<List<Guidance>>() {
            @Override
            public void onChanged(List<Guidance> guidances) {
                guidanceAdapter.setGuidances(guidances);

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                guidanceViewModel.delete(guidanceAdapter.getGuidanceAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Guidance deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(guidanceRecyclerView);


        return rootView;
    }


    @Override
    public void onListItemClick(Guidance guidances) {

    }
}
