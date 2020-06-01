package com.example.sep4android.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.Models.ArchiveRoomIndentification;
import com.example.sep4android.R;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    private OnListItemClickedListener onListItemClickedListener;
    private List<ArchiveRoomIndentification> rooms;


    public RoomAdapter(OnListItemClickedListener listener) {
        onListItemClickedListener = listener;
    }

    @NonNull
    @Override
    public RoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.room_item, parent, false);
        return new ViewHolder(view, onListItemClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomAdapter.ViewHolder holder, int position) {
        if(rooms != null) {
            ArchiveRoomIndentification roomPosition = rooms.get(position);
            holder.roomName.setText(String.valueOf(roomPosition.getRoomName()));
        }
    }

    public void setRooms(List<ArchiveRoomIndentification> rooms) {
        if (this.rooms ==null)
        {
            this.rooms = rooms;
            notifyDataSetChanged();
        }
        else
        for(int i = 0;i<rooms.size();i++)
        {
                if(!this.rooms.get(i).equals(rooms.get(i)))
                {
                    this.rooms = rooms;
                    notifyDataSetChanged();
                    break;
                }
        }
    }

    @Override
    public int getItemCount() {
        if(rooms != null) {
            return rooms.size();
        }
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView roomName;
        RelativeLayout parentLayout;
        OnListItemClickedListener onListItemClickedListener;

        public ViewHolder(@NonNull View itemView, OnListItemClickedListener listener) {
            super(itemView);
            roomName = itemView.findViewById(R.id.roomName);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            onListItemClickedListener = listener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onListItemClickedListener.onListItemClicked(rooms.get(getAdapterPosition()).getRoomNumber());
        }

    }
    public interface OnListItemClickedListener {
        void onListItemClicked(int roomNumber);
    }
}
