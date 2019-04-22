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
           for (int i= 0 ; i < moviesArray.length(); i++ ){
               JSONObject result = moviesArray.getJSONObject(i);
               moviesList.add(new movieInfo(result));
           }
       }catch (JSONException e){
           e.printStackTrace();
       }
        return moviesList;
    }

    static ArrayList<trailer> getTraillers(String videoJson){
        ArrayList<trailer> trailers = new ArrayList<>();
        if(videoJson == null  || videoJson.isEmpty()){
            return null;
        }
        try{
            JSONObject request = new JSONObject(videoJson);
            JSONArray videosArray = request.getJSONArray(RESULTS);
            for(int i=0 ; i < videoJson.length() ; i++){
                JSONObject video = videosArray.getJSONObject(i);
                trailer trailer = new trailer(video);
                if(trailer.id != null){
                    trailers.add(trailer);
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return trailers;

    }

    static ArrayList<review> getReviews(String reviewsJson){
       ArrayList<review> reviewList = new ArrayList<>();

       if(reviewsJson == null || reviewsJson.isEmpty()){
           return null;
       }
       try {
           JSONObject request = new JSONObject(reviewsJson);
           JSONArray reviewsArray = request.getJSONArray(RESULTS);
           for (int i=0; i< reviewsArray.length();i++){
               JSONObject result = reviewsArray.getJSONObject(i);
               review review = new review(result);
               reviewList.add(review);
           }
       }catch (JSONException e){
           e.printStackTrace();
       }
       return reviewList;
    }
}
