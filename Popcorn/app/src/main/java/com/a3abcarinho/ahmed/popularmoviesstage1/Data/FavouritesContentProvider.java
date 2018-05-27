package com.a3abcarinho.ahmed.popularmoviesstage1.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by ahmed on 08/02/18.
 */

public class FavouritesContentProvider extends ContentProvider {
    private static final int FAVOURITES = 100;
    private static final int FAOURITES_WITH_ID = 101;
    private static final UriMatcher uriMatcher = mUriMatcher();
    private FavouritesDB favouritesDB;

    //uri matcher to access Database
    public static UriMatcher mUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(FavouriteContract.AUTHORITY, FavouriteContract.PATH_FAV, FAVOURITES);
        uriMatcher.addURI(FavouriteContract.AUTHORITY, FavouriteContract.PATH_FAV + "/#", FAOURITES_WITH_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        favouritesDB = new FavouritesDB(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        final SQLiteDatabase sqLiteDatabase = favouritesDB.getWritableDatabase();
        int matcher = uriMatcher.match(uri);
        Cursor returnCursor;
        switch (matcher) {
            case FAVOURITES:
                returnCursor = sqLiteDatabase.query(FavouriteContract.FavouritesEntry.FAVOURITES_TABLE, strings, s, strings1, null, null, s1);
                break;
            case FAOURITES_WITH_ID:
                returnCursor = sqLiteDatabase.query(FavouriteContract.FavouritesEntry.FAVOURITES_TABLE, strings, s, strings1, null, null, s1);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }
        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    //insert,delete and update a Movie in the favourites
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase sqLiteDatabase = favouritesDB.getWritableDatabase();
        int matcher = uriMatcher.match(uri);
        Uri returnUri;
        switch (matcher) {
            case FAVOURITES:
                long id = sqLiteDatabase.insertWithOnConflict(FavouriteContract.FavouritesEntry.FAVOURITES_TABLE, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(FavouriteContract.FavouritesEntry.FAV_Content_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase database = favouritesDB.getWritableDatabase();
        int matcher = uriMatcher.match(uri);
        int delete;
        switch (matcher) {
            case FAOURITES_WITH_ID:
                String id = uri.getPathSegments().get(1);
                delete = database.delete(FavouriteContract.FavouritesEntry.FAVOURITES_TABLE, "_id=?", new String[]{id});
                break;
            case FAVOURITES:
                delete = database.delete(FavouriteContract.FavouritesEntry.FAVOURITES_TABLE, null, null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri " + uri);
        }
        if (delete != 0) {
            getContext().getContentResolver().notifyChange(uri, null);

        }
        return delete;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
