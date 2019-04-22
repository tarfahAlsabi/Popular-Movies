package com.example.popularmovies.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MoviesDAO {

    @Insert
     void insertMovie(MovieModel movie);

    @Insert
     void insertMultipleMovies(List<MovieModel> movies);


     @Query("DELETE FROM MovieModel WHERE movieId = :id")
     void deleteMovie(int id);


    @Query("Select * from MovieModel")
    List<MovieModel> getAllMovies();

    @Query("Select * from MovieModel where movieId = :id")
    MovieModel getMovie(int id);
}
