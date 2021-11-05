package com.manu.mathew.rickandmorty.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.manu.mathew.rickandmorty.R;
import com.manu.mathew.rickandmorty.adapter.CharacterAdapter;
import com.manu.mathew.rickandmorty.adapter.EpisodeAdaptor;

import android.os.Bundle;

public class CharacterActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public CharacterAdapter characterAdapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        characterAdapter=new CharacterAdapter();
        recyclerView.setAdapter(characterAdapter);




    }
}