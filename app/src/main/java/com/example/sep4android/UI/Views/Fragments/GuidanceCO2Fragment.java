package com.example.sep4android.UI.Views.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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

import com.example.sep4android.DATA.Models.Guidance;
import com.example.sep4android.R;
import com.example.sep4android.UI.Adapters.GuidanceAdapter;
import com.example.sep4android.UI.ViewModels.GuidanceViewModel;
import com.example.sep4android.UI.Views.Activities.GuidanceActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class GuidanceCO2Fragment extends Fragment implements GuidanceAdapter.OnListItemClickListener {
    private GuidanceViewModel guidanceViewModel;
    private RecyclerView guidanceRecyclerView;
    private EditText description;
    private FloatingActionButton addNewItem;
    private GuidanceAdapter guidanceAdapter;

    public GuidanceCO2Fragment(GuidanceActivity guidanceActivity, GuidanceViewModel guidanceViewModel) {
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


        defaultData();
        guidanceAdapter = new GuidanceAdapter(this);
        guidanceRecyclerView.setAdapter(guidanceAdapter);

        addNewItem = rootView.findViewById(R.id.guidance_fab);
        addNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGuidance();
            }
        });

        guidanceViewModel.getGuidanceCO2().observe(this.getActivity(), new Observer<List<Guidance>>() {
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

    private void defaultData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!sharedPreferences.contains("data")) {

            guidanceViewModel.insert(new Guidance("CO2", getResources().getString(R.string.co2_guidance)));
            guidanceViewModel.insert(new Guidance("CO2", getResources().getString(R.string.co2_guidance2)));
            guidanceViewModel.insert(new Guidance("Humidity", getResources().getString(R.string.humidity_guidance)));
            guidanceViewModel.insert(new Guidance("Humidity", getResources().getString(R.string.humidity_guidance2)));
            guidanceViewModel.insert(new Guidance("Humidity", getResources().getString(R.string.humidity_guidance3)));
            guidanceViewModel.insert(new Guidance("Temperature", getResources().getString(R.string.temperature_guidance)));
            guidanceViewModel.insert(new Guidance("Temperature", getResources().getString(R.string.temperature_guidance2)));


            editor.putBoolean("data", true).apply();
        }
    }

    private void saveGuidance() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.add_guidance, null);
        description = view.findViewById(R.id.guidance_description_dialog);
        builder.setView(view);

        builder.setPositiveButton("Add New Guidance", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String txt = description.getText().toString();
                System.out.println("added");
                guidanceViewModel.insert(new Guidance("CO2", txt));

            }
        });

        builder.setNegativeButton("Never mind", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        final AlertDialog ad = builder.create();
        ad.show();
    }

    @Override
    public void onListItemClick(final Guidance guidances) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.add_guidance, null);
        description = view.findViewById(R.id.guidance_description_dialog);
        builder.setView(view);

        builder.setPositiveButton("Edit Guidance", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int i = guidances.getId();
                String txt = description.getText().toString();
                System.out.println("edited");
                Guidance newGuidance = new Guidance("CO2", txt);
                newGuidance.setId(i);
                guidanceViewModel.update(newGuidance);
            }
        });

        builder.setNegativeButton("Never mind", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        final AlertDialog ad = builder.create();
        ad.show();
    }
}

