package com.a3abcarinho.ahmed.popularmoviesstage1.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.AsyncTaskLoader;

import com.a3abcarinho.ahmed.popularmoviesstage1.Model.ReviewModel;
import com.a3abcarinho.ahmed.popularmoviesstage1.Model.VideoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmed on 08/02/18.
 */

public class DataLoader extends AsyncTaskLoader<List<Object>> {

    //Api Keys
    private static final String VIDEONAME = "name";
    private static final String VIDEOKEY = "key";
    private static final String RESULTS = "results";
    private static final String REVIEWER = "author";
    private static final String REVIEW = "content";
    private static final String REVIEWURL = "url";
    private int id;
    private URL vidURL;
    private URL revURL;

    public DataLoader(Context context, int id) {
        super(context);
        this.id = id;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Object> loadInBackground() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean connected = false;
        if (networkInfo != null) {
            connected = networkInfo != null & networkInfo.isConnectedOrConnecting();
        }
        List<Object> objects = new ArrayList<>();
        //Review and Video Loading while connected
        if (connected) {
            vidURL = Networking.videosURL(id);
            revURL = Networking.reviewURL(id, 1);
            try {
                String videoResp = Networking.httpConnection(vidURL);
                String reviewResp = Networking.httpConnection(revURL);
                JSONObject video = new JSONObject(videoResp);
                JSONArray videoResults = video.getJSONArray(RESULTS);
                List<VideoModel> videos = new ArrayList<>();
                for (int i = 0; i < videoResults.length(); i++) {
                    JSONObject object = videoResults.getJSONObject(i);
                    String name = object.getString(VIDEONAME);
                    String key = object.getString(VIDEOKEY);
                    videos.add(new VideoModel(name, key));
                }
                JSONObject review = new JSONObject(reviewResp);
                JSONArray reviewResults = review.getJSONArray(RESULTS);
                List<ReviewModel> reviews = new ArrayList<>();
                for (int i = 0; i < reviewResults.length(); i++) {
                    JSONObject object = reviewResults.getJSONObject(i);
                    String reviewer = object.getString(REVIEWER);
                    String reviewcon = object.getString(REVIEW);
                    String url = object.getString(REVIEWURL);
                    reviews.add(new ReviewModel(reviewcon, reviewer, url));
                }
                objects.add(videos);
                objects.add(reviews);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            return null;
        }
        return objects;
    }

    @Override
    public void deliverResult(List<Object> data) {
        super.deliverResult(data);
    }
}
