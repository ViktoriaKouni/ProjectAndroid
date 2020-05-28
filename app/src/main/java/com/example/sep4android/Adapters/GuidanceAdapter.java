package com.example.sep4android.Adapters;


import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

    public class GuidanceAdapter extends RecyclerView.Adapter<GuidanceAdapter.ViewHolder> {

        @NonNull
        @Override
        public GuidanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull GuidanceAdapter.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
            }

            @Override
            public void onClick(View v) {

            }
        }
    }