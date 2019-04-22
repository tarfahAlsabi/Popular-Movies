/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.popularmovies;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class NetworkUtils {

    final static String baseUrl= "https://api.themoviedb.org/3/movie/";
    final static String MOVIEDB_TOP_RATED_BASE_URL = "top_rated";
    final static String MOVIEDB_POPULAR_BASE_URL = "popular";
    final static String MOVIEDB_REVIEWS_BASE_URL = "/reviews";
    final static String MOVIEDB_VIDEOS_BASE_URL = "/videos";

    final static String API_KEY = "3c4c336dcaa78cd2e536a35da79d791e";
    final static String API_KEY_QUERY = "api_key";


    public static URL buildUrl(Context context, int movieID , String moviesType) {
        String MOVIEDB_BASE_URL = baseUrl;
        if(movieID == -1 ) {
            if (moviesType.equals("top_rated")) {
                MOVIEDB_BASE_URL = MOVIEDB_BASE_URL + MOVIEDB_TOP_RATED_BASE_URL;
            } else {
                MOVIEDB_BASE_URL = MOVIEDB_BASE_URL+  MOVIEDB_POPULAR_BASE_URL;
            }
        }else {
            if(moviesType.equals(context.getString(R.string.reviews))){
                MOVIEDB_BASE_URL = MOVIEDB_BASE_URL+  movieID +MOVIEDB_REVIEWS_BASE_URL;
            }else{
                MOVIEDB_BASE_URL =MOVIEDB_BASE_URL + movieID +MOVIEDB_VIDEOS_BASE_URL;
            }
        }

        Log.i("finalUrl",MOVIEDB_BASE_URL);
        Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_QUERY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        if(url == null){
            return null;
        }
        Log.i("url",""+ url.getPath());
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                String res =scanner.next() ;
//                Log.i("internet request",res);
                return res;
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}