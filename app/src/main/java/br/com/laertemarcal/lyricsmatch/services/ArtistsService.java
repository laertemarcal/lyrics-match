package br.com.laertemarcal.lyricsmatch.services;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.com.laertemarcal.lyricsmatch.R;
import br.com.laertemarcal.lyricsmatch.fragments.ArtistsFragment;
import br.com.laertemarcal.lyricsmatch.model.Artist;

/**
 * Created by Laerte on 02/06/2015.
 */
public class ArtistsService {

    private final String url = "https://musixmatchcom-musixmatch.p.mashape.com/wsr/1.1/artist.search?page=1&page_size=50&s_artist_rating=desc&q_artist=";
    private final ArtistsFragment handler;

    public ArtistsService(ArtistsFragment handler) {
        this.handler = handler;
    }

    public void sendRequest(String params) {
        handler.getSpinner().setVisibility(View.VISIBLE);

        try {
            params = URLEncoder.encode(params, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(handler.getActivity());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url + params, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                handler.getSpinner().setVisibility(View.GONE);
                ArrayList<Artist> artists = new ArrayList<>();
                try {
                    artists = new Artist().getArtists(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.setArtistsOnView(artists);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERRO", error.getMessage());
                handler.getSpinner().setVisibility(View.INVISIBLE);
            }
        }) {
            @Override
            public Map getHeaders() throws AuthFailureError {
                Map headers = new HashMap();
                headers.put("X-Mashape-Key", "GNAsMZZ7mXmshxaBfcMd2JDsvEQcp14v6vUjsnKSzuY5ovKZ7R");
                headers.put("Accept", "application/json");
                return headers;
            }
        };

        queue.add(jsonArrayRequest);
    }

}