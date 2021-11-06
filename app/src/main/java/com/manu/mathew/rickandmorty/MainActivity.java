package com.manu.mathew.rickandmorty;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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
    public static String url = "https://rickandmortyapi.com/api/episode/";
    private int pagesize = 0;
    private ProgressBar loadingPB;
    private NestedScrollView nestedSV;
    private static final String TAG_request="episodes";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        queue = Volley.newRequestQueue(this);
        episodesArrayList = new ArrayList<>();
        charcterUrl = new ArrayList<>();


        txt_filter = (TextInputLayout) findViewById(R.id.edittxt_search);
        loadingPB = findViewById(R.id.idPBLoading);
        nestedSV = findViewById(R.id.idNestedSV);

        //for filtering
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


        //to load more
        nestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    pagesize = pagesize + 10;
                    loadingPB.setVisibility(View.VISIBLE);
                    loadEpisodes(pagesize);
                }
            }
        });


        loadEpisodes(pagesize + 10);

        episodesRecycler = (RecyclerView) findViewById(R.id.recyclerview);
        episodesRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        episodeAdaptor = new EpisodeAdaptor(episodesArrayList);
        episodesRecycler.setAdapter(episodeAdaptor);

    }

    public void loadEpisodes(int size) {

        ArrayList<String> episodes = new ArrayList<>();

        for (int i = size - 10; i <= size; i++) {
            episodes.add(String.valueOf(i));
        }


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + episodes.toString(), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                       // Log.e("resp", response.toString());
                        loadingPB.setVisibility(View.GONE);
                        try {
                            if (response.length() <= 0) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "No more episodes to load", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return;
                            }

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
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
                                    //update recycler on response
                                    episodeAdaptor.notifyDataSetChanged();
                                }
                            });


                        } catch (JSONException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Something went wrong ERROR 001", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setTitle("Error");
                builder1.setMessage("Check your internet connection or " + error.toString());
                builder1.setCancelable(false);
                builder1.setPositiveButton(
                        "close",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                finish();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });

        jsonArrayRequest.setTag(TAG_request);
        queue.add(jsonArrayRequest);

    }


    public void filter(String text) {
        //filters episode and name
        filterdlist.clear();

        for (Episodes items : this.episodesArrayList) {

            if (items.name.toLowerCase().contains(text.toLowerCase()) || items.episode.toLowerCase().contains(text.toLowerCase())) {


                filterdlist.add(items);

            }
        }

        episodeAdaptor.filterList(filterdlist);

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //cancel volley request
        queue.cancelAll(TAG_request);

    }
}