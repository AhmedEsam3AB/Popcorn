package com.a3abcarinho.ahmed.popularmoviesstage1.Model;

/**
 * Created by ahmed on 19/12/17.
 */

public class MovieModel {
    private String title;
    private String poster;
    private String average;
    private String date;
    private String overview;
    private int id;

    //Used in main activity
    public MovieModel(String title, String poster, String average, String date, String overview, int id) {
        this.title = title;
        this.poster = poster;
        this.average = average;
        this.date = date;
        this.overview = overview;
        this.id = id;
    }

    //used with database

    public MovieModel(String title, String poster, int id,String date,String average,String overview) {
        this.title = title;
        this.poster = poster;
        this.date = date;
        this.average = average;
        this.overview = overview;
        this.id = id;
    }

    //Movie Values getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getAverage() {
        return average;
    }

    public String getDate() {
        return date;
    }

    public String getOverview() {
        return overview;
    }

}
