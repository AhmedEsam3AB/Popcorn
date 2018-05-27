package com.a3abcarinho.ahmed.popularmoviesstage1.Adapter;

import android.animation.Animator;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a3abcarinho.ahmed.popularmoviesstage1.Model.VideoModel;
import com.a3abcarinho.ahmed.popularmoviesstage1.R;

import java.util.List;

/**
 * Created by ahmed on 07/02/18.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private final CutstomItemCickListener cutstomItemCickListener;
    private Context context;
    private List<VideoModel> videoList;

    public VideoAdapter(Context context, List<VideoModel> videoList, CutstomItemCickListener cutstomItemCickListener) {
        this.context = context;
        this.videoList = videoList;
        this.cutstomItemCickListener = cutstomItemCickListener;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Video item Create
        View view = LayoutInflater.from(context).inflate(R.layout.video_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        VideoModel video = videoList.get(position);
        holder.name.setText(video.getName());
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public interface CutstomItemCickListener {
        void onItemClick(int position, List<VideoModel> videos);
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name;
         int click;

        public VideoViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.videoName);
            itemView.setOnClickListener(this);
            if (click == 0)
                click = itemView.getContext().getResources().getColor(R.color.colorPrimary);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onClick(View view) {
            //Animation when Clicked
            int finalRadius = (int) Math.hypot(view.getWidth() / 2, view.getHeight() / 2);
                Animator anim = ViewAnimationUtils.createCircularReveal(view, (int) view.getWidth() / 2, (int) view.getHeight() / 2, 0, finalRadius);
                view.setBackgroundColor(click);
                anim.start();
            int position = getAdapterPosition();
            List<VideoModel> videos = videoList;
            cutstomItemCickListener.onItemClick(position, videos);


        }
    }
}
