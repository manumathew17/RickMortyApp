package com.manu.mathew.rickandmorty.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.manu.mathew.rickandmorty.MainActivity;
import com.manu.mathew.rickandmorty.R;
import com.manu.mathew.rickandmorty.adapter.CharacterAdapter;
import com.manu.mathew.rickandmorty.object.Character;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CharacterActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public CharacterAdapter characterAdapter;
    public ArrayList<Character> characterArrayList;
    public static RequestQueue queue;
    public static final String requestTAG = "character";
    public static String episode = "Episode";
    TextView txt_episode;
    ImageButton back_btn;
    TextInputLayout txt_filter;
    ArrayList<Character> filterdlist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);
        txt_episode = (TextView) findViewById(R.id.episode);
        back_btn = (ImageButton) findViewById(R.id.back);
        txt_filter = (TextInputLayout) findViewById(R.id.edittxt_search);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        characterArrayList = new ArrayList<>();

        characterArrayList.clear();

        for (int i = 0; i < MainActivity.charcterUrl.size(); i++) {
            CharacterActivity.queue = Volley.newRequestQueue(MainActivity.mContext);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, MainActivity.charcterUrl.get(i), null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("response", response.toString());

                            try {
                                Character character = new Character(response.getInt("id"), response.getString("name"), response.getString("status"), response.getString("species"), response.getString("type"), response.getString("gender"), response.getString("image"), response.getString("url"), response.getString("created"));
                                characterArrayList.add(character);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        characterAdapter.notifyDataSetChanged();
                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("error", error.toString());

                }
            });

            CharacterActivity.queue.add(jsonObjectRequest);
            jsonObjectRequest.setTag(CharacterActivity.requestTAG);
        }

        txt_episode.setText(episode);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        characterAdapter = new CharacterAdapter(characterArrayList);
        recyclerView.setAdapter(characterAdapter);


        txt_filter.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                filter(s.toString());


            }
        });


    }

    public void filter(String text) {

        filterdlist.clear();

        for (Character items : this.characterArrayList) {

            if (items.name.toLowerCase().contains(text.toLowerCase())) {


                filterdlist.add(items);

            }
        }

        characterAdapter.filterList(filterdlist);

    }


    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenuInflater().inflate(R.menu.actions, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.male) {
                    filterdlist.clear();

                    for (Character items : characterArrayList) {

                        if (items.getGender().toLowerCase().contains("male")) {


                            filterdlist.add(items);
                        }
                    }

                    characterAdapter.filterList(filterdlist);

                } else if (item.getItemId() == R.id.female) {
                    filterdlist.clear();

                    for (Character items : characterArrayList) {

                        if (items.getGender().toLowerCase().contains("female")) {


                            filterdlist.add(items);
                        }
                    }

                    characterAdapter.filterList(filterdlist);

                } else if (item.getItemId() == R.id.unknown) {

                    filterdlist.clear();

                    for (Character items : characterArrayList) {

                        if (items.getGender().toLowerCase().contains("unknown")) {


                            filterdlist.add(items);
                        }
                    }

                    characterAdapter.filterList(filterdlist);

                } else {

                    characterAdapter.filterList(characterArrayList);
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