package com.a3abcarinho.ahmed.popularmoviesstage1.Model;

/**
 * Created by ahmed on 07/02/18.
 */

public class VideoModel {
    private String name;
    private String key;

    public VideoModel() {
    }
    //Setting Video model Method
    public VideoModel(String name, String key) {
        this.name = name;
        this.key = key;
    }

    //Getting Video Values methods

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }
}
