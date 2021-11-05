package com.manu.mathew.rickandmorty.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.manu.mathew.rickandmorty.MainActivity;
import com.manu.mathew.rickandmorty.R;
import com.manu.mathew.rickandmorty.activity.CharacterActivity;
import com.manu.mathew.rickandmorty.object.Episodes;

import java.util.ArrayList;

public class EpisodeAdaptor extends RecyclerView.Adapter<EpisodeAdaptor.ViewHolder>{
    ArrayList<Episodes> episodesArrayList;


    public EpisodeAdaptor(ArrayList<Episodes> episodes) {
        this.episodesArrayList=episodes;
    }

    @NonNull
    @Override
    public EpisodeAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_episode, parent, false);
        EpisodeAdaptor.ViewHolder evh = new EpisodeAdaptor.ViewHolder(view);

        return evh;
    }


    @Override
    public void onBindViewHolder(@NonNull EpisodeAdaptor.ViewHolder holder, int position) {

        holder.txt_name.setText(episodesArrayList.get(position).name);
        holder.txt_episode.setText("Episode : "+episodesArrayList.get(position).getEpisode());
       // holder.txt_createdon.setText(episodesArrayList.get(position).getCreated());
        holder.txt_airdate.setText("On air : "+episodesArrayList.get(position).getAir_date());


    }


    @Override
    public int getItemCount() {
        return episodesArrayList.size();
    }

    public void filterList(ArrayList<Episodes> filterdlist) {

        this.episodesArrayList = filterdlist;
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txt_name, txt_episode, txt_airdate,txt_createdon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name=itemView.findViewById(R.id.name);
            txt_airdate=itemView.findViewById(R.id.airdate);
           // txt_createdon=itemView.findViewById(R.id.createdon);
            txt_episode=itemView.findViewById(R.id.episode);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            MainActivity.charcterUrl.clear();

            MainActivity.charcterUrl=episodesArrayList.get(getAdapterPosition()).characters;



            Intent intent = new Intent(MainActivity.mContext, CharacterActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MainActivity.mContext.startActivity(intent);

        }
    }
}
