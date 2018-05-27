package com.a3abcarinho.ahmed.popularmoviesstage1.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a3abcarinho.ahmed.popularmoviesstage1.Model.ReviewModel;
import com.a3abcarinho.ahmed.popularmoviesstage1.R;

import java.util.List;

/**
 * Created by ahmed on 07/02/18.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private Context context;
    private List<ReviewModel> reviewList;

    public ReviewAdapter(Context context, List<ReviewModel> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        //set values to fields
        ReviewModel review = reviewList.get(position);
        holder.reviewer.setText(review.getReviewer());
        holder.review.setText(review.getReview());

    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        private TextView review;
        private TextView reviewer;

        public ReviewViewHolder(View view) {
            super(view);
            reviewer = view.findViewById(R.id.reviewer);
            review = view.findViewById(R.id.review);


        }
    }
}
