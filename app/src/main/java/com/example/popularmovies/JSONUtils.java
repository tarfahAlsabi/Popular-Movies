package com.example.popularmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtils {
final static String RESULTS = "results";
   static ArrayList<movieInfo> getMovies (String moviesJSON){
       ArrayList<movieInfo> moviesList = new ArrayList<>();
       if(moviesJSON.isEmpty())
        return null;
       try{
           JSONObject request = new JSONObject(moviesJSON);
           JSONArray moviesArray = request.getJSONArray(RESULTS);
           Log.i("JSON",moviesArray.toString());
           for (int i= 0 ; i < moviesArray.length(); i++ ){
               JSONObject result = moviesArray.getJSONObject(i);
               moviesList.add(new movieInfo(result));
           }
       }catch (JSONException e){
           e.printStackTrace();
       }
        return moviesList;
    }
}
