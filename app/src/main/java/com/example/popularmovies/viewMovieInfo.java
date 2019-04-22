package com.example.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.data.Database;
import com.example.popularmovies.data.MovieModel;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class viewMovieInfo extends AppCompatActivity {
    TextView movieTitle, movieOverview, movieRealeaseDate, movieAvgVote;
    ImageView moviePoster;
    movieInfo movie;
    int movieId;
    Context context= this;
    ArrayList<trailer> trailerList;
    ArrayList<review> reviewsList;
    RecyclerView trailersRV;
    RecyclerView reviewsRV;
    trailerAdapter trailerAdapter;
    Button favoriteButton;
    reviewsAdapter reviewsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_movie_info);
        movieTitle = findViewById(R.id.tv_movie_title);
        movieOverview = findViewById(R.id.tv_movie_overview);
        movieRealeaseDate = findViewById(R.id.tv_movie_releaseDate);
        movieAvgVote = findViewById(R.id.tv_movie_avgVote);
        moviePoster = findViewById(R.id.movie_poster);
        reviewsRV = findViewById(R.id.recyclerView_reviews);
        favoriteButton = findViewById(R.id.favorite_button);


        trailersRV = findViewById(R.id.recyclerView_traillers);
        trailerAdapter = new trailerAdapter(this,trailerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        trailersRV.setLayoutManager(layoutManager);
        trailersRV.setAdapter(trailerAdapter);


        reviewsRV= findViewById(R.id.recyclerView_reviews);
        reviewsAdapter = new reviewsAdapter(this,reviewsList);
        LinearLayoutManager reviewsLayoutManager = new LinearLayoutManager(this);
        reviewsRV.setLayoutManager(reviewsLayoutManager);
        reviewsRV.setAdapter(reviewsAdapter);

        Intent intent = getIntent();
        if (intent.hasExtra("movieName")) {
            String title , overview , realeaseDate, posterPath;
            double avreage_vote;
            boolean isFavorite;

            movieId = intent.getIntExtra("movieID", -1);
            title = intent.getStringExtra("movieName");
            overview = intent.getStringExtra("movieOverview");
            realeaseDate = intent.getStringExtra("movieDate");
            avreage_vote = intent.getDoubleExtra("movieAvgRate", 0.0);
            posterPath = intent.getStringExtra("moviePoster");
            isFavorite = intent.getBooleanExtra("movieIsFavorite",false);

            movie = new movieInfo(movieId,title,overview,realeaseDate,posterPath,avreage_vote,isFavorite);

            movieTitle.setText(movie.title);
            movieOverview.setText(movie.overview);
            movieRealeaseDate.setText(movie.release_date);
            movieAvgVote.setText(String.format("%1.1f",movie.vote_average) );
            Picasso.with(this).load(movie.poster).into(moviePoster);

            setButton(movie.isfavorite);

            new Fetchtrailors().execute(movieId);
            new FetchReviews().execute(movieId);
        }


    }

    private void setButton(boolean isfavorite) {
        if(isfavorite){
            favoriteButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            favoriteButton.setText(R.string.unFavoriteMark);
        }else{
            favoriteButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            favoriteButton.setText(R.string.favoriteMark);
        }
    }

    public void toggleFavorite(View view){
        movie.isfavorite= movie.isfavorite? false : true;
        setButton(movie.isfavorite);
        new databaseHandler().execute(movie.isfavorite);

    }
    class databaseHandler extends  AsyncTask<Boolean,Void,Void>{
        Database database = Database.getDatabase(context);

        @Override
        protected Void doInBackground(Boolean... booleans) {
            boolean isFavorite = booleans[0];
            if(isFavorite){
                database.moviesDAO().insertMovie(new MovieModel(movie));
            }else{
                database.moviesDAO().deleteMovie(movieId);
            }
            return null;
        }
    }
    class Fetchtrailors extends AsyncTask <Integer ,Void,ArrayList<trailer>>

    {
        @Override
        protected ArrayList<trailer> doInBackground(Integer... ints) {
            int id = ints[0];
            String trailer = getString(R.string.videos);
            URL url = NetworkUtils.buildUrl(context,id,trailer);
            try {
                String result = NetworkUtils.getResponseFromHttpUrl(url);
                return JSONUtils.getTraillers(result);
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<trailer> trailers) {
            super.onPostExecute(trailers);
            if(trailerList == null ){
                trailerList= new ArrayList<>();
            }
            trailerList.clear();
            if(trailers != null ){
                trailerList.addAll(trailers);
                trailerAdapter.setTrailers(trailers);
                Log.i("trailers",""+ trailers.size());
            }
        }
    }

        public void trailerView(View view){
        TextView traileSite = view.findViewById(R.id.trailer_site);
        if(traileSite != null ) {
            String uriString = (String) traileSite.getText();
            Log.i("inside trailer view", uriString);
            if (uriString != null) {
                Uri trailerUrl = Uri.parse(uriString);
                Intent intent = new Intent(Intent.ACTION_VIEW, trailerUrl);
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                }
            }
        }else{
            Log.i("inside trailer view",view.toString());
        }
        }
    class FetchReviews extends AsyncTask <Integer ,Void,ArrayList<review>>

    {
        @Override
        protected ArrayList<review> doInBackground(Integer... ints) {
            int id = ints[0];
            String reviews = getString(R.string.reviews);
            URL url = NetworkUtils.buildUrl(context,id,reviews);
            try {
                String result = NetworkUtils.getResponseFromHttpUrl(url);
                return JSONUtils.getReviews(result);
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<review> reviews) {
            super.onPostExecute(reviews);
            if(reviewsList == null ){
                reviewsList= new ArrayList<>();
            }
            reviewsList.clear();
            if(reviews != null ){
                reviewsList.addAll(reviews);
                reviewsAdapter.setReviews(reviews);
                Log.i("reviewsNum: ",reviewsList.size()+"");
            }
        }
    }


}
