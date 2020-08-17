package com.example.thecoachtimer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thecoachtimer.R;
import com.example.thecoachtimer.models.RoomPlayer;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LapAdapter extends RecyclerView.Adapter<LapAdapter.LapHolder> {
    private List<RoomPlayer> laps = new ArrayList<>();


    @NonNull
    @Override
    public LapHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lap_list_view, parent, false);
        return new LapHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LapHolder holder, int position) {
        RoomPlayer currentLap = laps.get(position);
        holder.textViewLapTime.setText(currentLap.getLapTime());
        holder.textViewSpeed.setText(Double.toString(currentLap.getSpeed()));


    }

    @Override
    public int getItemCount() {
        return laps.size();
    }

    public void setSessionLaps(List<RoomPlayer> laps) {
        this.laps = laps;
        notifyDataSetChanged();
    }

    static class LapHolder extends RecyclerView.ViewHolder {
        private final TextView textViewLapTime;
        private final TextView textViewSpeed;
        public LapHolder(View itemView) {
            super(itemView);
            textViewLapTime = itemView.findViewById(R.id.text_view_lapTime);
            textViewSpeed = itemView.findViewById(R.id.text_view_lapSpeed);
        }
    }
}
