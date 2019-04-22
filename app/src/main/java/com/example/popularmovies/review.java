package com.example.popularmovies;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class review {

    String id , author ,content , url;

    public review(JSONObject review){
        try {
            id = review.getString("id");
            author = review.getString("author");
            content = review.getString("content");
            url = review.getString("url");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public String toString() {
        return id + "\n" + author + "\n" + content ;
    }
}
