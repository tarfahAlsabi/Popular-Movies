package com.example.popularmovies;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.popularmovies.Settings.SettingsActivity;
import com.example.popularmovies.data.Database;
import com.example.popularmovies.data.MovieModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView noInternet;
    RecyclerView recyclerView;
    MoviesAdapter adapter;
    ProgressBar progressBar;
    final int NUMBER_OF_COLUMNS = 2;
    Context context = this;
    ArrayList<movieInfo> moviesList= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("oncreate","class created");
        setContentView(R.layout.activity_main);
        noInternet=findViewById(R.id.no_internet_tv);
        progressBar=findViewById(R.id.progress_bar);
        recyclerView= findViewById(R.id.recycler_view);
        adapter = new MoviesAdapter(context, moviesList);
        recyclerView.setAdapter(adapter);

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        String sortByValue = sharedPreferences.getString(getString(R.string.sort_by_key),"top_rated");


        GridLayoutManager layoutManager = new GridLayoutManager(this, NUMBER_OF_COLUMNS);
        recyclerView.setLayoutManager(layoutManager);

        new movieFetch().execute(sortByValue);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        String sortByValue = sharedPreferences.getString(getString(R.string.sort_by_key),"top_rated");
        new movieFetch().execute(sortByValue);

    }

    class movieFetch extends AsyncTask<String ,Void,ArrayList<movieInfo>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<movieInfo> doInBackground(String... strings) {
            String sortType = strings[0];
            if(sortType.isEmpty()){
                return null;
            }
            if(sortType.equals(getString(R.string.favorite))){
                Database database= Database.getDatabase(context);
                ArrayList<movieInfo> movieInfos = new ArrayList<>();
                List<MovieModel> movieModels= database.moviesDAO().getAllMovies();
                for (MovieModel movie: movieModels) {
                    movieInfos.add(movie.getMovie());
                }
                return movieInfos;
            }
            URL requestURL = NetworkUtils.buildUrl(context,-1,sortType);
            try{
                String requestResult = NetworkUtils.getResponseFromHttpUrl(requestURL);
                if(requestResult == null || requestResult.isEmpty()){
                    noInternet.setVisibility(View.VISIBLE);
                    return null;
                }
                return JSONUtils.getMovies(requestResult);

           }catch (IOException e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<movieInfo> movieInfos) {
            super.onPostExecute(movieInfos);
            if(moviesList != null || !moviesList.isEmpty())
                moviesList.clear();
            if(movieInfos == null ){
                noInternet.setVisibility(View.VISIBLE);
            }
            else {
                moviesList.addAll(movieInfos);

                adapter.setMovies(movieInfos);

                Log.i("onPost", moviesList.size() + "");
                adapter.notifyDataSetChanged();
                noInternet.setVisibility(View.INVISIBLE);
            }
            progressBar.setVisibility(View.INVISIBLE);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings_menu_item) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
