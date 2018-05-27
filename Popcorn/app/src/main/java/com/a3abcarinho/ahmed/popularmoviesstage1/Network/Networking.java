package com.a3abcarinho.ahmed.popularmoviesstage1.Network;

import android.net.Uri;

import com.a3abcarinho.ahmed.popularmoviesstage1.Activity.MainActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by ahmed on 20/12/17.
 */


public class Networking {
    //Api keys
    private static final String MovieBaseUrl = "https://api.themoviedb.org/3/";
    private static final String Type = "movie";
    private static final String PosterBaseUrl = "http://image.tmdb.org/t/p/";
    private static final String PosterSize = "w500";
    private static final String ApiKey = "api_key";
    private static final String Popular = "popular";
    private static final String TopRated = "top_rated";
    private static final String Page = "page";
    private static final String Videos = "videos";
    private static final String Reviews = "reviews";
    private static final String YoutubeBaseUrl = "https://www.youtube.com/watch/";
    private static final String YoutubeQuery = "v";

    public Networking() {
    }
    //getting image,sort by,video and review urls

    public static URL imageURL(String poster_path) {
        Uri uri = Uri.parse(PosterBaseUrl).buildUpon().appendPath(PosterSize).appendEncodedPath(poster_path).build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        }
        return url;
    }

    public static URL topRatedURL(String api) {
        Uri uri = Uri.parse(MovieBaseUrl).buildUpon().appendPath(Type).appendPath(TopRated).appendQueryParameter(ApiKey, api).build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        }
        return url;
    }

    public static URL popularURL(String api) {
        Uri uri = Uri.parse(MovieBaseUrl).buildUpon().appendPath(Type).appendPath(Popular).appendQueryParameter(ApiKey, api).build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        }
        return url;
    }

    public static URL reviewURL(int id, int page) {
        Uri uri = Uri.parse(MovieBaseUrl).buildUpon().appendPath(Type).appendPath(String.valueOf(id)).appendPath(Reviews).appendQueryParameter(ApiKey, MainActivity.API_KEY).appendQueryParameter(Page, String.valueOf(page)).build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        }
        return url;
    }

    public static URL youtubeURL(String key) {
        Uri uri = Uri.parse(YoutubeBaseUrl).buildUpon().appendQueryParameter(YoutubeQuery, key).build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        }
        return url;
    }

    public static URL videosURL(int id) {
        Uri uri = Uri.parse(MovieBaseUrl).buildUpon().appendPath(Type).appendPath(String.valueOf(id)).appendPath(Videos).appendQueryParameter(ApiKey, MainActivity.API_KEY).build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        }
        return url;
    }

    public static URL URL(String order_by, String api) {
        Uri uri = Uri.parse(MovieBaseUrl).buildUpon().appendPath(Type).appendPath(order_by).appendQueryParameter(ApiKey, api).build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        }
        return url;
    }


    public static String httpConnection(URL url) throws IOException {
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = new BufferedInputStream(httpConnection.getInputStream());
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");
            boolean success = scanner.hasNext();
            if (success) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            httpConnection.disconnect();
        }

    }


}





