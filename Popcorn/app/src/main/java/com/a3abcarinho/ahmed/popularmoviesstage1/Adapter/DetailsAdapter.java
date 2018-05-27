package com.a3abcarinho.ahmed.popularmoviesstage1.Adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a3abcarinho.ahmed.popularmoviesstage1.Model.ReviewModel;
import com.a3abcarinho.ahmed.popularmoviesstage1.Model.VideoModel;
import com.a3abcarinho.ahmed.popularmoviesstage1.Network.Networking;
import com.a3abcarinho.ahmed.popularmoviesstage1.R;

import java.util.List;

import static android.R.attr.id;

/**
 * Created by ahmed on 08/02/18.
 */

public class DetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements VideoAdapter.CutstomItemCickListener {
    //View type keys
    private final static int VIDEOS = 0;
    private final static int REVIEWS = 1;
    private Context context;
    private List<Object> objectList;

    public DetailsAdapter(Context context, List<Object> objectList) {
        this.context = context;
        this.objectList = objectList;

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIDEOS;
        } else if (position == 1) {
            return REVIEWS;
        } else {
            return -1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case VIDEOS:
                view = LayoutInflater.from(context).inflate(R.layout.video, parent, false);
                return new VideosViewHolder(view);
            case REVIEWS:
                view = LayoutInflater.from(context).inflate(R.layout.review, parent, false);
                return new ReviewsViewHolder(view);
            default:
                throw new IllegalArgumentException("Invalid view type, value of " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case REVIEWS:
                reviews((ReviewsViewHolder) holder);
                break;
            case VIDEOS:
                videos((VideosViewHolder) holder);
        }

    }
    //setting videos recyclerview

    public void videos(VideosViewHolder videosViewHolder) {
        VideoAdapter videoAdapter = new VideoAdapter(context, (List<VideoModel>) objectList.get(0), this);
        videosViewHolder.videosRV.setAdapter(videoAdapter);
        videosViewHolder.videosRV.setHasFixedSize(true);
        videosViewHolder.videosRV.setFocusable(false);
        videosViewHolder.videosRV.setNestedScrollingEnabled(false);
        videosViewHolder.videosRV.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    //setting reviews recyclerview

    public void reviews(ReviewsViewHolder reviewsViewHolder) {
        ReviewAdapter reviewAdapter = new ReviewAdapter(context, (List<ReviewModel>) objectList.get(1));
        reviewsViewHolder.reviewsRV.setAdapter(reviewAdapter);
        reviewsViewHolder.reviewsRV.setHasFixedSize(true);
        reviewsViewHolder.reviewsRV.setFocusable(false);
        reviewsViewHolder.reviewsRV.setNestedScrollingEnabled(false);
        reviewsViewHolder.reviewsRV.setLayoutManager(new LinearLayoutManager(context));
    }


    @Override
    public int getItemCount() {
        return objectList == null ? 0 : objectList.size();
    }

    //start video intent when clicked
    @Override
    public void onItemClick(int position, List<VideoModel> videos) {
        VideoModel video = videos.get(position);
        String youtubeURL = String.valueOf(Networking.youtubeURL(video.getKey()));
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + video.getKey()));
        Intent vIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeURL + id));
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException Anfe) {
            context.startActivity(vIntent);
        }

    }

    class VideosViewHolder extends RecyclerView.ViewHolder {
        RecyclerView videosRV;

        public VideosViewHolder(View itemView) {
            super(itemView);
            videosRV = itemView.findViewById(R.id.videosRV);
        }
    }

    class ReviewsViewHolder extends RecyclerView.ViewHolder {
        RecyclerView reviewsRV;

        public ReviewsViewHolder(View itemView) {
            super(itemView);
            reviewsRV = itemView.findViewById(R.id.reviewsRV);
        }
    }


}
