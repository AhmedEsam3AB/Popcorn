package com.a3abcarinho.ahmed.popularmoviesstage1.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.a3abcarinho.ahmed.popularmoviesstage1.Model.MovieModel;
import com.a3abcarinho.ahmed.popularmoviesstage1.Network.Networking;
import com.a3abcarinho.ahmed.popularmoviesstage1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ahmed on 07/02/18.
 */

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder> {
    private Context context;
    private List<MovieModel> movieList;
    private CustomClickListener customClickListener;
    //Used with Animation
    private int lastPosition = -1;


    public FavouritesAdapter(CustomClickListener customCliclListener, Context context, List<MovieModel> movieList) {
        this.customClickListener = customCliclListener;
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public FavouritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.favourites, parent, false);
        return new FavouritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavouritesViewHolder holder, int position) {
        //set fields values
        MovieModel movie = movieList.get(position);
        String url = String.valueOf((Networking.imageURL(movie.getPoster())));
        Picasso.with(context).load(url).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(holder.favPoster);
        String name = String.valueOf(movie.getTitle());
        String average = String.valueOf(movie.getAverage());
        holder.favName.setText(name);
        holder.favAverage.setText(average);
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
        return movieList.size();
    }

    public interface CustomClickListener {
        void onCustomClickListener(int position, List<MovieModel> movies);
    }

    public class FavouritesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RecyclerView favouritesRV;
        ImageView favPoster;
        ImageView mark;
        TextView favName;
        TextView favAverage;

        public FavouritesViewHolder(View view) {
            super(view);
            favouritesRV = itemView.findViewById(R.id.mfavourite);
            favPoster = itemView.findViewById(R.id.mainActivityPoster);
            mark = itemView.findViewById(R.id.FavsMark);
            favName = itemView.findViewById(R.id.favouriteName);
            favAverage = itemView.findViewById(R.id.FavAverage);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            customClickListener.onCustomClickListener(position, movieList);

        }
    }
}
