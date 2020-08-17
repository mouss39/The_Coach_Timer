package com.example.thecoachtimer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thecoachtimer.data.models.Player;
import com.example.thecoachtimer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.PlayerListHolder> {
    private List<Player> players = new ArrayList<>();
    private RecyclerViewClickListener listener;

    @NonNull
    @Override
    public PlayerListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlayerListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.player_list_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerListHolder holder, int position) {
        final Player currentPlayer = players.get(position);

        holder.textViewPlayerName.setText(currentPlayer.getName().getFirst() + " " + currentPlayer.getName().getLast());
        Picasso.get().load(currentPlayer.getPicture().getLarge()).into(holder.imageViewPlayer);


    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public void updatePlayers(List<Player> players, RecyclerViewClickListener listener ) {
        this.players = players;
        this.listener=listener;
        notifyDataSetChanged();
    }

    class PlayerListHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        private final TextView textViewPlayerName;
        private final ImageView imageViewPlayer;

        public PlayerListHolder(View itemView) {
            super(itemView);
            textViewPlayerName = itemView.findViewById(R.id.text_view_playerName);
            imageViewPlayer = itemView.findViewById(R.id.image_view_playerImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        listener.onClick(v, getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener {

        void onClick(View v,int position);
    }





}
