package com.example.popularmovies;

import org.json.JSONObject;

public class movieInfo {
    public String poster= "https://image.tmdb.org/t/p/w600_and_h900_bestv2/";
    public String title;
    public String overview;
    public String release_date;
    public double vote_average;
    public int id;

    movieInfo(JSONObject movie){
        try {
            this.poster += movie.getString("poster_path");
            this.title = movie.getString("title");
            this.overview = movie.getString("overview");
            this.release_date = movie.getString("release_date");
            this.vote_average = movie.getDouble("vote_average");
            this.id = movie.getInt("id");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return title + "\n id: " + id +"\n  poster:"+ poster;
    }
}
