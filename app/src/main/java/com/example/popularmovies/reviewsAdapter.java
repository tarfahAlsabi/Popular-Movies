package com.example.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class reviewsAdapter extends RecyclerView.Adapter<reviewsAdapter.reviewholder> {
    ArrayList<review> reviews;
    Context context;
    public reviewsAdapter(Context context, ArrayList reviews) {
        this.reviews = reviews;
        this.context = context;
    }

    @NonNull
    @Override
    public reviewsAdapter.reviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.review_item, viewGroup, false);
        return new reviewsAdapter.reviewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull reviewsAdapter.reviewholder holder , int i) {
        holder.bind(i);

    }

    @Override
    public int getItemCount() {
        return reviews != null ? reviews.size() : 0;
    }
    public void setReviews(ArrayList reviews){
        this.reviews = reviews;
        notifyDataSetChanged();
    }
    class reviewholder extends RecyclerView.ViewHolder {
        TextView author , content;
        review review;
        public reviewholder(View view) {
            super(view);
            author = view.findViewById(R.id.review_author);
            content = view.findViewById(R.id.review_content);
        }

        public void bind(int i) {
            if( reviews == null || i > reviews.size())
                return;
            review = reviews.get(i);
            author.setText(review.author);
            content.setText(review.content);

        }
    }
}
