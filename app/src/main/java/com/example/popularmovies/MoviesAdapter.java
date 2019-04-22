package com.example.popularmovies;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    ArrayList<movieInfo> movies;
    Context context;

    public MoviesAdapter(Context context, ArrayList<movieInfo> source) {
        this.context = context;
        this.movies = source;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if(movies != null)
        return movies.size();
        return 0;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        ImageView poster;
        int id;
        movieInfo movie;

        public MovieViewHolder(View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.poster_thumbnail);
            itemView.setOnClickListener(this);
        }

        public void bind (int index){
            Picasso.with(context).load(movies.get(index).poster).into(poster);
            movie= movies.get(index);
            id = movies.get(index).id;
        }

        @Override
        public void onClick(View v) {
            if(movie == null){
                Log.e("inside the listener","null movie");
                return;
            }
            Log.i("inside the listener",movie.toString());

            Intent intent= new Intent(context, viewMovieInfo.class);
            intent.putExtra("movieID",movie.id);
            intent.putExtra("movieName",movie.title);
            intent.putExtra("movieDate",movie.release_date);
            intent.putExtra("movieOverview",movie.overview);
            intent.putExtra("movieAvgRate",movie.vote_average);
            intent.putExtra("moviePoster",movie.poster);
            intent.putExtra("movieIsFavorite",movie.isfavorite);

            context.startActivity(intent);
        }
    }

    public void setMovies(ArrayList<movieInfo> movieList){
        movies=movieList;
        notifyDataSetChanged();
    }
}
