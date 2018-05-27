package com.a3abcarinho.ahmed.popularmoviesstage1.Data;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;

import static android.provider.BaseColumns._ID;

public class FavouritesDataLoader extends AsyncTaskLoader<Cursor> {

    public FavouritesDataLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public void deliverResult(Cursor data) {
        super.deliverResult(data);
    }

    @Override
    public Cursor loadInBackground() {
        try {
            return getContext().getContentResolver().query(FavouriteContract.FavouritesEntry.FAV_Content_URI, null, null, null, null);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    //One favourite DataLoader
    public static class favouriteDataLoader extends AsyncTaskLoader<Boolean> {
        private String nId;

        public favouriteDataLoader(Context context, String id) {
            super(context);
            this.nId = id;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

        @Override
        public Boolean loadInBackground() {
            Cursor cursor;
            Uri uri = FavouriteContract.FavouritesEntry.FAV_Content_URI;
            try {
                cursor = getContext().getContentResolver().query(uri, null, _ID + " = " + nId, null, null);
                cursor.close();
                if (cursor.getCount() > 0) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public void deliverResult(Boolean data) {
            super.deliverResult(data);
        }
    }
}
