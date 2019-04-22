package com.example.popularmovies;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class movieInfo {
    public String poster= "https://image.tmdb.org/t/p/w600_and_h900_bestv2/";
    public String title;
    public String overview;
    public String release_date;
    public double vote_average;
    public int id;
    public boolean isfavorite = false;

    public movieInfo(JSONObject movie){
        if(movie == null)
            {
                return;
            }
        try {
            this.title = movie.getString("title");
            this.overview = movie.getString("overview");
            this.release_date = movie.getString("release_date");
            this.vote_average = movie.getDouble("vote_average");
            this.id = movie.getInt("id");
            if(movie.has("isfavorite")){
                this.isfavorite = movie.getBoolean("isfavorite");
                this.poster = movie.getString("poster_path");
            }else{
                this.isfavorite = false;
                this.poster += movie.getString("poster_path");
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    movieInfo(int id, String title, String overview, String release_date , String poster , double vote_average , boolean isfavorite){
        this.poster = poster;
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.id = id;
        this.isfavorite = isfavorite;
    }
    @Override
    public String toString() {
        Map moviemap = new HashMap();
        moviemap.put("id",id);
        moviemap.put("title",title);
        moviemap.put("poster_path",poster);
        moviemap.put("overview",overview);
        moviemap.put("release_date",release_date);
        moviemap.put("vote_average",vote_average);
        moviemap.put("isfavorite",isfavorite);
        JSONObject movieObject = new JSONObject(moviemap);
        return movieObject.toString();
    }
}
