package com.manu.mathew.rickandmorty.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.manu.mathew.rickandmorty.MainActivity;
import com.manu.mathew.rickandmorty.R;

import org.json.JSONException;
import org.json.JSONObject;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {
    RequestQueue queue;


    @NonNull
    @Override
    public CharacterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_episodes, parent, false);
        CharacterAdapter.ViewHolder evh = new CharacterAdapter.ViewHolder(view);

        return evh;

    }


    @Override
    public void onBindViewHolder(@NonNull CharacterAdapter.ViewHolder holder, int position) {
        queue = Volley.newRequestQueue(MainActivity.mContext);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, MainActivity.charcterUrl.get(position), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("response", response.toString());
                        try {
                            holder.textView.setText(response.getString("name"));
                            holder.txt_description.setText(response.getString("status"));

                            holder.txt_gender.setText("Gender : " + response.getString("gender"));
                            holder.txt_created.setText("Created : " + response.get("created"));
                            Glide.with(holder.imageView.getContext())
                                    .load(response.getString("image"))
                                    .into(holder.imageView);

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


    @Override
    public int getItemCount() {
        return MainActivity.charcterUrl.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView, txt_description, txt_gender, txt_created;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.charactername);
            imageView = itemView.findViewById(R.id.card_imageview);
            txt_description = itemView.findViewById(R.id.description);
            txt_gender = itemView.findViewById(R.id.gender);
            txt_created = itemView.findViewById(R.id.createdon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.e("click", "click");

        }
    }
}
