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
        episodesArrayList=new ArrayList<>();

        charcterUrl = new ArrayList<>();
        txt_filter=(TextInputLayout) findViewById(R.id.edittxt_search);

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


       // charcterUrl.addAll(Arrays.asList(new String[]{"https://rickandmortyapi.com/api/character/8", "https://rickandmortyapi.com/api/character/14", "https://rickandmortyapi.com/api/character/15", "https://rickandmortyapi.com/api/character/18", "https://rickandmortyapi.com/api/character/21", "https://rickandmortyapi.com/api/character/22", "https://rickandmortyapi.com/api/character/27", "https://rickandmortyapi.com/api/character/42", "https://rickandmortyapi.com/api/character/43", "https://rickandmortyapi.com/api/character/44", "https://rickandmortyapi.com/api/character/48", "https://rickandmortyapi.com/api/character/53", "https://rickandmortyapi.com/api/character/56", "https://rickandmortyapi.com/api/character/61", "https://rickandmortyapi.com/api/character/69", "https://rickandmortyapi.com/api/character/72", "https://rickandmortyapi.com/api/character/73", "https://rickandmortyapi.com/api/character/74", "https://rickandmortyapi.com/api/character/77", "https://rickandmortyapi.com/api/character/78", "https://rickandmortyapi.com/api/character/85", "https://rickandmortyapi.com/api/character/86", "https://rickandmortyapi.com/api/character/95", "https://rickandmortyapi.com/api/character/118", "https://rickandmortyapi.com/api/character/119", "https://rickandmortyapi.com/api/character/123", "https://rickandmortyapi.com/api/character/135", "https://rickandmortyapi.com/api/character/143", "https://rickandmortyapi.com/api/character/152", "https://rickandmortyapi.com/api/character/164", "https://rickandmortyapi.com/api/character/165", "https://rickandmortyapi.com/api/character/187", "https://rickandmortyapi.com/api/character/200", "https://rickandmortyapi.com/api/character/206", "https://rickandmortyapi.com/api/character/209", "https://rickandmortyapi.com/api/character/220", "https://rickandmortyapi.com/api/character/229", "https://rickandmortyapi.com/api/character/231", "https://rickandmortyapi.com/api/character/235", "https://rickandmortyapi.com/api/character/267", "https://rickandmortyapi.com/api/character/278", "https://rickandmortyapi.com/api/character/281", "https://rickandmortyapi.com/api/character/283", "https://rickandmortyapi.com/api/character/284", "https://rickandmortyapi.com/api/character/285", "https://rickandmortyapi.com/api/character/286", "https://rickandmortyapi.com/api/character/287", "https://rickandmortyapi.com/api/character/288", "https://rickandmortyapi.com/api/character/289", "https://rickandmortyapi.com/api/character/291", "https://rickandmortyapi.com/api/character/295", "https://rickandmortyapi.com/api/character/298", "https://rickandmortyapi.com/api/character/299", "https://rickandmortyapi.com/api/character/322", "https://rickandmortyapi.com/api/character/325", "https://rickandmortyapi.com/api/character/328", "https://rickandmortyapi.com/api/character/330", "https://rickandmortyapi.com/api/character/345", "https://rickandmortyapi.com/api/character/359", "https://rickandmortyapi.com/api/character/366", "https://rickandmortyapi.com/api/character/378", "https://rickandmortyapi.com/api/character/385", "https://rickandmortyapi.com/api/character/392", "https://rickandmortyapi.com/api/character/461", "https://rickandmortyapi.com/api/character/462", "https://rickandmortyapi.com/api/character/463", "https://rickandmortyapi.com/api/character/464", "https://rickandmortyapi.com/api/character/465", "https://rickandmortyapi.com/api/character/466", "https://rickandmortyapi.com/api/character/472", "https://rickandmortyapi.com/api/character/473", "https://rickandmortyapi.com/api/character/474", "https://rickandmortyapi.com/api/character/475", "https://rickandmortyapi.com/api/character/476", "https://rickandmortyapi.com/api/character/477", "https://rickandmortyapi.com/api/character/478", "https://rickandmortyapi.com/api/character/479", "https://rickandmortyapi.com/api/character/480", "https://rickandmortyapi.com/api/character/481", "https://rickandmortyapi.com/api/character/482", "https://rickandmortyapi.com/api/character/483", "https://rickandmortyapi.com/api/character/484", "https://rickandmortyapi.com/api/character/485", "https://rickandmortyapi.com/api/character/486", "https://rickandmortyapi.com/api/character/487", "https://rickandmortyapi.com/api/character/488", "https://rickandmortyapi.com/api/character/489", "https://rickandmortyapi.com/api/character/2", "https://rickandmortyapi.com/api/character/1", "https://rickandmortyapi.com/api/character/801", "https://rickandmortyapi.com/api/character/802", "https://rickandmortyapi.com/api/character/803", "https://rickandmortyapi.com/api/character/804", "https://rickandmortyapi.com/api/character/805", "https://rickandmortyapi.com/api/character/806", "https://rickandmortyapi.com/api/character/810", "https://rickandmortyapi.com/api/character/811", "https://rickandmortyapi.com/api/character/812", "https://rickandmortyapi.com/api/character/819", "https://rickandmortyapi.com/api/character/820", "https://rickandmortyapi.com/api/character/818"}));


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
                                    episodeAdaptor=new EpisodeAdaptor(episodesArrayList);
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