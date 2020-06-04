package com.example.sep4android.UI.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.DATA.Models.Guidance;
import com.example.sep4android.R;

import java.util.List;

public class GuidanceAdapter extends RecyclerView.Adapter<GuidanceAdapter.ViewHolder> {

    public interface OnListItemClickListener {
        void onListItemClick(Guidance guidances);
    }

    private List<Guidance> guidances;
    final private OnListItemClickListener mOnListItemClickListener;

    public GuidanceAdapter(OnListItemClickListener listener) {
        mOnListItemClickListener = listener;
    }

    @NonNull
    @Override
    public GuidanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.guidance_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuidanceAdapter.ViewHolder holder, int position) {
        if (guidances != null) {
            Guidance guidancePosition = guidances.get(position);
            holder.guidanceDescription.setText(guidancePosition.getDescription());
        }
    }

    public void setGuidances(List<Guidance> guidances) {
        this.guidances = guidances;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (guidances == null) {
            return 0;
        }
        return guidances.size();
    }

    public Guidance getGuidanceAt(int position) {
        return guidances.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView guidanceDescription;


        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            guidanceDescription = itemView.findViewById(R.id.guidance_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            mOnListItemClickListener.onListItemClick(guidances.get(getAdapterPosition()));
        }
    }
}