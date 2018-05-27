package com.a3abcarinho.ahmed.popularmoviesstage1.Model;

/**
 * Created by ahmed on 07/02/18.
 */

public class ReviewModel {
    private String review;
    private String reviewer;
    private String url;

    //Setting review model method

    public ReviewModel(String review, String reviewer, String url) {
        this.review = review;
        this.reviewer = reviewer;
        this.url = url;
    }

    //Getting review values methods

    public String getReview() {
        return review;
    }

    public String getReviewer() {
        return reviewer;
    }

    public String getUrl() {
        return url;
    }

}
