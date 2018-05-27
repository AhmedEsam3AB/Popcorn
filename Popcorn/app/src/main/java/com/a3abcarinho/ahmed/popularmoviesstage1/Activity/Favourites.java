package com.a3abcarinho.ahmed.popularmoviesstage1.Activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.a3abcarinho.ahmed.popularmoviesstage1.Adapter.FavouritesAdapter;
import com.a3abcarinho.ahmed.popularmoviesstage1.Data.FavouriteContract;
import com.a3abcarinho.ahmed.popularmoviesstage1.Data.FavouritesDataLoader;
import com.a3abcarinho.ahmed.popularmoviesstage1.Model.MovieModel;
import com.a3abcarinho.ahmed.popularmoviesstage1.R;

import java.util.ArrayList;
import java.util.List;

public class Favourites extends AppCompatActivity implements FavouritesAdapter.CustomClickListener {
    RecyclerView recyclerView;
    LinearLayout linearLayout;
    ImageView poster;
    LinearLayout nofavsLinearlayout;

    FavouritesAdapter adapter;
    private LoaderManager.LoaderCallbacks<Cursor> loader = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
            return new FavouritesDataLoader(Favourites.this);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            List<MovieModel> movies = new ArrayList<>();
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // Get columns of Favourite movies
                    int idX = cursor.getColumnIndex(FavouriteContract.FavouritesEntry._ID);
                    int titleX = cursor.getColumnIndex(FavouriteContract.FavouritesEntry.COLUMN_TITLE);
                    int posterX = cursor.getColumnIndex(FavouriteContract.FavouritesEntry.COLUMN_POSTER);
                    int dateX = cursor.getColumnIndex(FavouriteContract.FavouritesEntry.COLUMN_DATE);
                    int averageX = cursor.getColumnIndex(FavouriteContract.FavouritesEntry.COLUMN_AVERAGE);
                    int overviewX = cursor.getColumnIndex(FavouriteContract.FavouritesEntry.COLUMN_OVERVIEW);

                    //Getting values of Movie from Database
                    int id = cursor.getInt(idX);
                    String title = cursor.getString(titleX);
                    String date = cursor.getString(dateX);
                    String average = cursor.getString(averageX);
                    String overview = cursor.getString(overviewX);
                    String poster = cursor.getString(posterX);
                    MovieModel movie = new MovieModel(title, poster, id,date,average,overview);
                    movies.add(movie);
                    adapter = new FavouritesAdapter(Favourites.this, Favourites.this, movies);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setHasFixedSize(true);

                }
                cursor.close();

            }
            if (movies.isEmpty()){


                //UX case if the Database is empty
                    linearLayout = (LinearLayout) findViewById(R.id.noFavs);
                    nofavsLinearlayout = (LinearLayout) findViewById(R.id.favsLinearLayout);
                    linearLayout.setVisibility(LinearLayout.VISIBLE);
                    nofavsLinearlayout.setVisibility(LinearLayout.GONE);

            }



        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourites);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.favo);

        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //check orientation state of device
        recyclerView = (RecyclerView) findViewById(R.id.mfavourite);
        getSupportLoaderManager().initLoader(1, null, loader);
        recyclerView.setLayoutManager(new GridLayoutManager(Favourites.this, 2));


    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(1, null, loader);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCustomClickListener(int position, List<MovieModel> movies) {
        favouritesIntent(position, movies);


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void favouritesIntent(int position, List<MovieModel> movies) {
        poster = (ImageView) findViewById(R.id.mainActivityPoster);
        MovieModel movie = movies.get(position);
        //Favourite Movie Intent
        Intent intent = new Intent(this, MovieDetails.class);
        intent.putExtra("id", movie.getId());
        intent.putExtra("title", movie.getTitle());
        intent.putExtra("date", movie.getDate());
        intent.putExtra("average", movie.getAverage());
        intent.putExtra("overview", movie.getOverview());
        intent.putExtra("poster", movie.getPoster());
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this,poster,poster.getTransitionName()).toBundle();
        startActivity(intent,bundle);
    }
}

