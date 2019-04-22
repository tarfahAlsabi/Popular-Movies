package com.example.popularmovies.data;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.popularmovies.movieInfo;


@Entity
public class MovieModel implements Parcelable {

    @PrimaryKey
    private int movieId;
    @TypeConverters(Converters.class)
    private movieInfo movie;

    public MovieModel(movieInfo movie){
        this.movie = movie;
        this.movieId = movie.id;
    }

    public int getMovieId() {
        return movieId;
    }

    public movieInfo getMovie() {
        return movie;
    }

    public void setMovie(movieInfo movie) {
        this.movie = movie;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    protected MovieModel(Parcel in) {
    movie = (movieInfo) in.readValue(movieInfo.class.getClassLoader());

    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(movie);
    }


}
