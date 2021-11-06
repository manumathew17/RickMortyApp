package com.manu.mathew.rickandmorty.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.manu.mathew.rickandmorty.MainActivity;
import com.manu.mathew.rickandmorty.R;
import com.manu.mathew.rickandmorty.activity.CharacterActivity;
import com.manu.mathew.rickandmorty.object.Character;

import java.util.ArrayList;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {
    public ArrayList<Character> characterArrayList;

    public CharacterAdapter(ArrayList<Character> characters) {
        this.characterArrayList = characters;
    }

    @NonNull
    @Override
    public CharacterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_character, parent, false);
        CharacterAdapter.ViewHolder evh = new CharacterAdapter.ViewHolder(view);

        return evh;

    }


    @Override
    public void onBindViewHolder(@NonNull CharacterAdapter.ViewHolder holder, int position) {
        CharacterActivity.queue = Volley.newRequestQueue(MainActivity.mContext);


        holder.textView.setText(characterArrayList.get(position).getName());
        String status = "none";
        status = characterArrayList.get(position).getStatus();
        if (status.toLowerCase().contains("alive")) {
            holder.txt_description.setText(characterArrayList.get(position).getStatus() + " - " + characterArrayList.get(position).getSpecies());
            holder.txt_description.getCompoundDrawablesRelative()[0].setTint(Color.GREEN);
        } else if (status.toLowerCase().contains("dead")) {
            holder.txt_description.setText(characterArrayList.get(position).getStatus() + " - " + characterArrayList.get(position).getSpecies());
            holder.txt_description.getCompoundDrawablesRelative()[0].setTint(Color.RED);
        }
        else{
            holder.txt_description.setText(characterArrayList.get(position).getStatus() + " - " + characterArrayList.get(position).getSpecies());
            holder.txt_description.getCompoundDrawablesRelative()[0].setTint(Color.DKGRAY);
        }


        holder.txt_gender.setText("Gender : " + characterArrayList.get(position).getGender());
        //holder.txt_created.setText("Created : " + response.get("created"));

        try {
            Glide.with(holder.imageView.getContext())
                    .load(characterArrayList.get(position).getImage())
                    .into(holder.imageView);
        } catch (Exception e) {

        }


    }


    @Override
    public int getItemCount() {
        return characterArrayList.size();
    }

    public void filterList(ArrayList<Character> filterdlist) {
        this.characterArrayList = filterdlist;
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView, txt_description, txt_gender;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.charactername);
            imageView = itemView.findViewById(R.id.card_imageview);
            txt_description = itemView.findViewById(R.id.description);
            txt_gender = itemView.findViewById(R.id.gender);
            //txt_created = itemView.findViewById(R.id.createdon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.e("click", "click");

        }
    }
}
