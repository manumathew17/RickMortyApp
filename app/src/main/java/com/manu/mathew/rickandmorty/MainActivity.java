package com.manu.mathew.rickandmorty;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

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
import com.manu.mathew.rickandmorty.adapter.EpisodeAdaptor;
import com.manu.mathew.rickandmorty.object.Episodes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public RecyclerView episodesRecycler;
    public ArrayList<Episodes> episodesArrayList;
    public static List<String> charcterUrl;
    public static Context mContext;
    public EpisodeAdaptor episodeAdaptor;
    TextInputLayout txt_filter;
    ArrayList<Episodes> filterdlist = new ArrayList<>();
    RequestQueue queue;
    public static String url = "https://rickandmortyapi.com/api/episode";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();

        queue = Volley.newRequestQueue(this);
        episodesArrayList = new ArrayList<>();

        charcterUrl = new ArrayList<>();
        txt_filter = (TextInputLayout) findViewById(R.id.edittxt_search);

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


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = (JSONArray) response.get("results");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                List<String> characters = new ArrayList<>();

                                for (int j = 0; j < jsonObject.getJSONArray("characters").length(); j++) {
                                    characters.add(jsonObject.getJSONArray("characters").getString(j));
                                }

                                Episodes episodes = new Episodes(jsonObject.getInt("id"), jsonObject.getString("name"), jsonObject.getString("air_date"), jsonObject.getString("episode"), characters, jsonObject.getString("url"), jsonObject.getString("created").toString());
                                episodesArrayList.add(episodes);

                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    episodesRecycler = (RecyclerView) findViewById(R.id.recyclerview);
                                    episodesRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                    episodeAdaptor = new EpisodeAdaptor(episodesArrayList);
                                    episodesRecycler.setAdapter(episodeAdaptor);
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


        queue.add(jsonObjectRequest);


    }


    public void filter(String text) {

        filterdlist.clear();

        for (Episodes items : this.episodesArrayList) {

            if (items.name.toLowerCase().contains(text.toLowerCase())) {


                filterdlist.add(items);

            }
        }

        episodeAdaptor.filterList(filterdlist);

    }

}