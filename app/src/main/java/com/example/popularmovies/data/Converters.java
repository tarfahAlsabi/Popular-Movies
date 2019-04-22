package com.example.popularmovies.data;

import android.arch.persistence.room.TypeConverter;

import com.example.popularmovies.movieInfo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class Converters {

    @TypeConverter
    public String MoveiToJson(movieInfo movie){
           return movie.toString();
        }

    @TypeConverter
    public movieInfo JsonToMovie(String movieJson ){
        try {
         JSONObject jsonObject = new JSONObject(movieJson);
         return  new movieInfo(jsonObject);

        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
        }
}
