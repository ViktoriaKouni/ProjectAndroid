package com.example.sep4android.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.R;
import com.example.sep4android.ViewModels.GuidanceViewModel;
import com.example.sep4android.Views.GuidanceActivity;

public class GuidanceCO2Fragment extends Fragment {
    GuidanceViewModel guidanceViewModel;
    RecyclerView guidanceRecyclerView;
    //GuidanceAdapter guidanceAdapter;

    public GuidanceCO2Fragment(GuidanceActivity guidanceActivity, GuidanceViewModel guidanceViewModel) {
        this.guidanceViewModel = guidanceViewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_guidance_co2, container, false);
        guidanceRecyclerView = rootView.findViewById(R.id.guidance_list_co2);
        guidanceRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        setHasOptionsMenu(true);
       // guidanceAdapter = new GuidanceAdapter(this);
        //guidanceRecyclerView.setAdapter(guidanceAdapter);

        return rootView;
    }
}
