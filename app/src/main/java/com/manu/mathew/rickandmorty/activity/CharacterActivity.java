package com.manu.mathew.rickandmorty.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.manu.mathew.rickandmorty.R;
import com.manu.mathew.rickandmorty.adapter.CharacterAdapter;

public class CharacterActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public CharacterAdapter characterAdapter;
    public static RequestQueue queue;
    public static final String requestTAG = "character";
    public static String episode = "Episode";
    TextView txt_episode;
    ImageButton back_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);
        txt_episode = (TextView) findViewById(R.id.episode);
        back_btn = (ImageButton) findViewById(R.id.back);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txt_episode.setText(episode);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        characterAdapter = new CharacterAdapter();
        recyclerView.setAdapter(characterAdapter);





    }


    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenuInflater().inflate(R.menu.actions, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.male){

                }
                else if(item.getItemId()==R.id.female){

                }
                else if(item.getItemId()==R.id.unknown){

                }
                else{

                }


                return true;
            }
        });


       // popup.inflate(R.menu.actions);
        popup.show();
    }









    @Override
    public void onDestroy() {

        super.onDestroy();
        queue.cancelAll(requestTAG);
        Log.e("LL", "efew");

    }

}