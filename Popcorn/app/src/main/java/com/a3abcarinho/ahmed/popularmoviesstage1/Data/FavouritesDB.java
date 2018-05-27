package com.a3abcarinho.ahmed.popularmoviesstage1.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.a3abcarinho.ahmed.popularmoviesstage1.Data.FavouriteContract.FavouritesEntry.COLUMN_AVERAGE;
import static com.a3abcarinho.ahmed.popularmoviesstage1.Data.FavouriteContract.FavouritesEntry.COLUMN_DATE;
import static com.a3abcarinho.ahmed.popularmoviesstage1.Data.FavouriteContract.FavouritesEntry.COLUMN_OVERVIEW;
import static com.a3abcarinho.ahmed.popularmoviesstage1.Data.FavouriteContract.FavouritesEntry.COLUMN_POSTER;
import static com.a3abcarinho.ahmed.popularmoviesstage1.Data.FavouriteContract.FavouritesEntry.COLUMN_TITLE;
import static com.a3abcarinho.ahmed.popularmoviesstage1.Data.FavouriteContract.FavouritesEntry.FAVOURITES_TABLE;

/**
 * Created by ahmed on 10/01/18.
 */

public class FavouritesDB extends SQLiteOpenHelper{
    //Database declaration
    private static final String DATABASE_NAME = "favouritesDB";
    private static final int DATABASE_VERSION = 1;


    public FavouritesDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String CREATE_TABLE = "CREATE TABLE " + FAVOURITES_TABLE + " (" + _ID + " INTEGER PRIMARY KEY, " + COLUMN_TITLE + " TEXT NOT NULL, " + COLUMN_DATE + " TEXT, " + COLUMN_AVERAGE + " TEXT, " + COLUMN_OVERVIEW + " TEXT, " + COLUMN_POSTER + " TEXT " + ");";
        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FAVOURITES_TABLE);
        onCreate(sqLiteDatabase);

    }
}
