package com.a3abcarinho.ahmed.popularmoviesstage1.Data;

import android.net.Uri;
import android.provider.BaseColumns;



public class FavouriteContract {

    //Favourites Contract assignment
    public static final String PATH_FAV = "favourites";
    public static final String AUTHORITY = "com.a3abcarinho.ahmed.popularmoviesstage1";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    //Database columns assignment
    public static final class FavouritesEntry implements BaseColumns {
        public static final Uri FAV_Content_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAV).build();
        public static final String FAVOURITES_TABLE = "favourites";
        public static final String COLUMN_POSTER = "poster_path";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_AVERAGE = "average";
        public static final String COLUMN_OVERVIEW = "overview";


    }
}
