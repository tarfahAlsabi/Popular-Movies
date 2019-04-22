package com.example.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class trailerAdapter extends RecyclerView.Adapter<trailerAdapter.trailerholder> {
    ArrayList<trailer> trailers;
    Context context;
    public trailerAdapter(Context context, ArrayList trailers) {
            this.trailers = trailers;
            this.context = context;
    }

    @NonNull
    @Override
    public trailerholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.trailer_item, viewGroup, false);
        return new trailerholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull trailerholder trailerholder, int i) {
        trailerholder.bind(i);

    }

    @Override
    public int getItemCount() {
        return trailers != null ? trailers.size() : 0;
    }

    public void setTrailers(ArrayList trailers){
            this.trailers = trailers;
            notifyDataSetChanged();
    }




    class trailerholder extends RecyclerView.ViewHolder  implements View.OnClickListener {
         TextView name , site;
        trailer trailer;

         private trailerholder(View view) {
             super(view);
             name = view.findViewById(R.id.trailerName);
             site = view.findViewById(R.id.trailer_site);
             view.setOnClickListener(this);
         }

        @Override
        public void onClick(View v) {
             Log.i("trailerOnClick",trailer.site);
             Uri trailerUrl= Uri.parse(trailer.site);
             Intent intent = new Intent(Intent.ACTION_VIEW, trailerUrl);
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                   context.startActivity(intent);
                }
        }

        private void bind(int i) {
             if( trailers == null || i > trailers.size())
             return;
             trailer = trailers.get(i);
             name.setText(trailer.name);
             site.setText(trailer.site);

        }
    }
}

