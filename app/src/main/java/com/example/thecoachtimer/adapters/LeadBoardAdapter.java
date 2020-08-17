package com.example.thecoachtimer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thecoachtimer.models.SessionFinalStats;
import com.example.thecoachtimer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LeadBoardAdapter extends RecyclerView.Adapter<LeadBoardAdapter.LeadBoardHolder> {

    private List<SessionFinalStats> listOfPlayers = new ArrayList<>();

    @NonNull
    @Override
    public LeadBoardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leader_board_list, parent, false);
        return new LeadBoardHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull LeadBoardHolder holder, int position) {
        SessionFinalStats currentStat = listOfPlayers.get(position);
        holder.textViewFirstName.setText(currentStat.getFirstName());
        holder.textViewLastName.setText(currentStat.getLastName());
        holder.textViewPeakSpeed.setText(Double.toString(currentStat.getPeakSpeed()));
        holder.textViewNumberOfLaps.setText(Integer.toString(currentStat.getNumberOfLaps()));
        Picasso.get().load(currentStat.getMediumImage()).into(holder.playerImageView);

    }

    @Override
    public int getItemCount() {
        return listOfPlayers.size();
    }
    public void setSessionLaps(List<SessionFinalStats> sessionStats) {
        this.listOfPlayers = sessionStats;
        notifyDataSetChanged();
    }
    public List<SessionFinalStats> getSessionLaps() {
        return this.listOfPlayers;
    }


    static class LeadBoardHolder extends RecyclerView.ViewHolder {
        private final TextView textViewFirstName;
        private final TextView textViewLastName;
        private final TextView textViewNumberOfLaps;
        private final TextView textViewPeakSpeed;
        private final ImageView playerImageView;

        public LeadBoardHolder(View itemView) {
            super(itemView);
            textViewFirstName = itemView.findViewById(R.id.text_view_firstName);
            textViewLastName = itemView.findViewById(R.id.text_view_lastName);
            textViewNumberOfLaps=itemView.findViewById(R.id.text_view_numberOfLaps);
            textViewPeakSpeed=itemView.findViewById(R.id.text_view_peakSpeed);
            playerImageView=itemView.findViewById(R.id.imageViewPlayerLeaderBoard);

        }
    }
}
