package com.a3abcarinho.ahmed.popularmoviesstage1.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.a3abcarinho.ahmed.popularmoviesstage1.Activity.MainActivity;
import com.a3abcarinho.ahmed.popularmoviesstage1.Model.MovieModel;
import com.a3abcarinho.ahmed.popularmoviesstage1.Activity.MovieDetails;
import com.a3abcarinho.ahmed.popularmoviesstage1.Network.Networking;
import com.a3abcarinho.ahmed.popularmoviesstage1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ahmed on 19/12/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.AdapterClassViewHolder> {
    List<MovieModel> movies;
    Context context;
    //used with animation
    private int lastPosition = -1;


    public MovieAdapter(List<MovieModel> movies, Context context) {
        this.movies = movies;
        this.context = context;

    }


    @Override
    public AdapterClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main, parent, false);
        return new AdapterClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterClassViewHolder holder, int position) {
        //set values to the fields
        MovieModel movie = movies.get(position);
        String posterUrl = String.valueOf((Networking.imageURL(movie.getPoster())));
        Picasso.with(context).load(posterUrl).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(holder.poster);
        String title = String.valueOf(movie.getTitle());
        holder.name.setText(title);
        String vote = String.valueOf(movie.getAverage());
        holder.avg.setText(vote);
        holder.mark.setImageResource(R.drawable.mark);
        setAnimation(holder.itemView, position);


    }

    //set animation method

    private void setAnimation(View viewToAnimate, int position)
    {
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position; }   }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class AdapterClassViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        ImageView mark;
        TextView name;
        TextView avg;

        public AdapterClassViewHolder(View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.mainActivityPoster);
            name = itemView.findViewById(R.id.mainname);
            avg = itemView.findViewById(R.id.vote);
            mark = itemView.findViewById(R.id.mark);

            itemView.setOnClickListener(new View.OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    MovieModel movie = movies.get(position);
                    //sending values via intent
                    Intent intent = new Intent(context, MovieDetails.class);
                    intent.putExtra("id", movie.getId());
                    intent.putExtra("title", movie.getTitle());
                    intent.putExtra("poster", movie.getPoster());
                    intent.putExtra("average", movie.getAverage());
                    intent.putExtra("date", movie.getDate());
                    intent.putExtra("overview", movie.getOverview());
                    Bundle bundle = ActivityOptions.makeSceneTransitionAnimation((Activity) context,poster,poster.getTransitionName()).toBundle();
                    context.startActivity(intent,bundle);


                }
            });

        }
    }
}
