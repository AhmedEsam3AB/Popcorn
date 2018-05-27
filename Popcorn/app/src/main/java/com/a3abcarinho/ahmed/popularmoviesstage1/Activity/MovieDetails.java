package com.a3abcarinho.ahmed.popularmoviesstage1.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a3abcarinho.ahmed.popularmoviesstage1.Adapter.DetailsAdapter;
import com.a3abcarinho.ahmed.popularmoviesstage1.Network.DataLoader;
import com.a3abcarinho.ahmed.popularmoviesstage1.Data.FavouriteContract;
import com.a3abcarinho.ahmed.popularmoviesstage1.Data.FavouritesDataLoader;
import com.a3abcarinho.ahmed.popularmoviesstage1.Network.Networking;
import com.a3abcarinho.ahmed.popularmoviesstage1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.provider.BaseColumns._ID;


public class MovieDetails extends AppCompatActivity {
    private static Bundle bundleRV;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView mTitle;
    ImageView mPoster;
    TextView mAverage;
    TextView mDate;
    TextView mOverview;
    DetailsAdapter adapter;
    RecyclerView recyclerView;
    FloatingActionButton fab;
    String title;
    String poster;
    String average;
    String date;
    String overview;
    boolean connected;
    private int id;
    private LoaderManager.LoaderCallbacks<List<Object>> objectCallback = new LoaderManager.LoaderCallbacks<List<Object>>() {
        @Override
        public Loader<List<Object>> onCreateLoader(int id, Bundle args) {
            Intent intent = getIntent();
            int movieId = intent.getExtras().getInt("id");
            return new DataLoader(MovieDetails.this, movieId);

        }

        @Override
        public void onLoadFinished(android.support.v4.content.Loader<List<Object>> loader, List<Object> data) {
            if (data != null) {
                adapter = new DetailsAdapter(MovieDetails.this, data);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MovieDetails.this));

            }
        }

        @Override
        public void onLoaderReset(android.support.v4.content.Loader<List<Object>> loader) {

        }
    };
    private LoaderManager.LoaderCallbacks<Boolean> colorchk = new LoaderManager.LoaderCallbacks<Boolean>() {
        int mId;

        @Override
        public Loader<Boolean> onCreateLoader(int id, Bundle args) {
            Intent intent = getIntent();
            mId = intent.getExtras().getInt("id");
            return new FavouritesDataLoader.favouriteDataLoader(MovieDetails.this, String.valueOf(mId));
        }

        @Override
        public void onLoadFinished(Loader<Boolean> loader, Boolean data) {

            if (!data) {
                setFabWhite();
            } else {
                setFabBlack();
            }

        }

        @Override
        public void onLoaderReset(Loader<Boolean> loader) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        //Check if connected
        if (connected) {
            if (bundleRV != null) {
                Parcelable listState = bundleRV.getParcelable("key");
                if (recyclerView.getLayoutManager() != null) {
                    recyclerView.getLayoutManager().onRestoreInstanceState(listState);
                }

            }

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (connected) {
            bundleRV = new Bundle();
            if (recyclerView.getLayoutManager().onSaveInstanceState() != null) {
                Parcelable listState = recyclerView.getLayoutManager().onSaveInstanceState();
                bundleRV.putParcelable("key", listState);

            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        connected = networkInfo != null && networkInfo.isConnectedOrConnecting();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        //Find and set values to activity fields
        setContentView(R.layout.movie_details);
        mPoster = (ImageView) findViewById(R.id.poster);
        mAverage = (TextView) findViewById(R.id.voteAverage);
        mDate = (TextView) findViewById(R.id.releaseDate);
        mOverview = (TextView) findViewById(R.id.overView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        fab = (FloatingActionButton) findViewById(R.id.fav);
        getSupportLoaderManager().initLoader(0, null, objectCallback);
        getSupportLoaderManager().initLoader(4, null, colorchk);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFavourite(String.valueOf(id));
            }
        });
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            title = intent.getExtras().getString("title");
            poster = intent.getExtras().getString("poster");
            average = intent.getExtras().getString("average");
            date = intent.getExtras().getString("date");
            overview = intent.getExtras().getString("overview");
            id = intent.getExtras().getInt("id");
            Picasso.with(this).load(String.valueOf(Networking.imageURL(poster))).into(mPoster);
            mAverage.setText(average);
            mDate.setText(date);
            mOverview.setText(overview);
            CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
            collapsingToolbarLayout.setTitle(title);



        } else {
            Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
        }

    }

    public void insert() {
        if (title.length() == 0) {
            return;
        }
        //Insert a novie to Favourites Database
        ContentValues values = new ContentValues();
        values.put(_ID, id);
        values.put(FavouriteContract.FavouritesEntry.COLUMN_TITLE, title);
        values.put(FavouriteContract.FavouritesEntry.COLUMN_POSTER, poster);
        values.put(FavouriteContract.FavouritesEntry.COLUMN_DATE, date);
        values.put(FavouriteContract.FavouritesEntry.COLUMN_AVERAGE, average);
        values.put(FavouriteContract.FavouritesEntry.COLUMN_OVERVIEW, overview);
        Uri uri = getContentResolver().insert(FavouriteContract.FavouritesEntry.FAV_Content_URI, values);
        if (uri != null) {
            getContentResolver().notifyChange(uri, null);
            setFabBlack();
        }
    }

    //Delete movie from the database

    private void delete() {
        Uri uri = FavouriteContract.FavouritesEntry.FAV_Content_URI;
        Uri mUri = uri.buildUpon().appendPath(String.valueOf(id)).build();
        int favD = getContentResolver().delete(mUri, null, null);
        if (favD > 0) {
            getContentResolver().notifyChange(mUri, null);
            setFabWhite();
        }


    }

    public void setFavouriePrefs(String key, int value) {
        sharedPreferences = getSharedPreferences("fav", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();

    }

    public int getFavouritePrefs(String key, int value) {
        sharedPreferences = getSharedPreferences("fav", MODE_PRIVATE);
        return sharedPreferences.getInt(key, value);
    }

    public void setFavourite(String id) {
        int preferences = getFavouritePrefs(String.valueOf(id), 1);
        if (preferences == 1) {
            setFavouriePrefs(String.valueOf(id), 0);
            setFabWhite();
            insert();
        } else if (preferences == 0) {
            setFavouriePrefs(String.valueOf(id), 1);
            setFabBlack();
            delete();
        }
    }

    public void setFabBlack() {
        fab.setImageResource(R.drawable.fav);
    }

    public void setFabWhite() {
        fab.setImageResource(R.drawable.nfav);
    }

}


