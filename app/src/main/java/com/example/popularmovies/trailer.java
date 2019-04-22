package com.example.popularmovies;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class trailer {
    String id ,key , name ,site ,type;

    public trailer (JSONObject trailler){
        try {
            type = trailler.getString("type");
            if(!type.equals("Trailer")){
                return;
            }
            id   = trailler.getString("id");
            key  = trailler.getString("key");
            name = trailler.getString("name");
            site = "https://www.youtube.com/watch?v="+ key;
        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    @NonNull
    @Override
    public String toString() {
        return id + "\n key: "+key + "\n site: "+ site+ "\n name: "+name;
    }
}
