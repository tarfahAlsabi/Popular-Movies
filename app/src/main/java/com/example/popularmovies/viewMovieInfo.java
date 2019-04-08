package com.example.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class viewMovieInfo extends AppCompatActivity {
    TextView movieTitle , movieOverview, movieRealeaseDate , movieAvgVote;
    ImageView moviePoster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_movie_info);

        movieTitle=findViewById(R.id.tv_movie_title);
        movieOverview= findViewById(R.id.tv_movie_overview);
        movieRealeaseDate= findViewById(R.id.tv_movie_releaseDate);
        movieAvgVote= findViewById(R.id.tv_movie_avgVote);
        moviePoster = findViewById(R.id.movie_poster);


        Intent intent= getIntent();
        if(intent.hasExtra("movieName")){
            movieTitle.setText(intent.getStringExtra("movieName"));
            movieOverview.setText(intent.getStringExtra("movieOverview"));
            movieRealeaseDate.setText(intent.getStringExtra("movieDate"));
            movieAvgVote.setText(intent.getDoubleExtra("movieAvgRate",0.0) +"");
            String posterPath = intent.getStringExtra("moviePoster");
            Picasso.with(this).load(posterPath).into(moviePoster);
        }


    }
}
