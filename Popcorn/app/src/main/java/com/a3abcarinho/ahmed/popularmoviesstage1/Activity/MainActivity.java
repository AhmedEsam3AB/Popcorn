package com.a3abcarinho.ahmed.popularmoviesstage1.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.a3abcarinho.ahmed.popularmoviesstage1.Adapter.MovieAdapter;
import com.a3abcarinho.ahmed.popularmoviesstage1.Model.MovieModel;
import com.a3abcarinho.ahmed.popularmoviesstage1.Network.Networking;
import com.a3abcarinho.ahmed.popularmoviesstage1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //API Keys
    public static final String API_KEY = "INSERT YOUR API HERE";
    private static final String SORT_BY = "sort_by";
    private static final String ORDER = "order";
    private static final String POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";
    private static final String LIST_STATE_KEY = "key_position";
    RecyclerView recyclerView;
    LinearLayout linearLayout;
    MovieAdapter adapter;
    List<MovieModel> movieList = new ArrayList<>();
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Parcelable listState = null;
    boolean connected;
    LinearLayout movieslinearlayout;


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        //Check if the device is connected
        if (connected) {
            if (recyclerView.getLayoutManager().onSaveInstanceState() != null) {
                listState = recyclerView.getLayoutManager().onSaveInstanceState();
            }
            outState.putParcelable(LIST_STATE_KEY, listState);
        }
        super.onSaveInstanceState(outState);


    }


    @Override
    protected void onPause() {

        if (connected) {
            if (recyclerView.getLayoutManager().onSaveInstanceState() != null) {
                listState = recyclerView.getLayoutManager().onSaveInstanceState();
            }
        }


        super.onPause();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            listState = savedInstanceState.getParcelable(LIST_STATE_KEY);
            super.onRestoreInstanceState(savedInstanceState);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        connected = networkInfo != null && networkInfo.isConnectedOrConnecting();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.small);

        getSupportActionBar().setDisplayUseLogoEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.movie);
        adapter = new MovieAdapter(movieList, MainActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setVisibility(View.VISIBLE);

        //Sort by preferences


        if (!getSortPreferences(ORDER, POPULAR).equals(POPULAR)) {
            mAsyncTask topTask = new mAsyncTask(getSortPreferences(ORDER, TOP_RATED), this);
            topTask.execute(API_KEY);

        } else {
            mAsyncTask popTask = new mAsyncTask(POPULAR, this);
            popTask.execute(API_KEY);


        }
    }

    public String getSortPreferences(String order, String val) {
        preferences = getSharedPreferences(SORT_BY, MODE_PRIVATE);
        return preferences.getString(order, val);

    }

    public void setSortPreferences(String order, String value) {
        preferences = getSharedPreferences(SORT_BY, MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(order, value);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override

    //Choose sort by way or starting favourites activity

    public boolean onOptionsItemSelected(MenuItem item) {
        int sortID = item.getItemId();
        switch (sortID) {
            case R.id.popular:
                if (getSortPreferences(ORDER, POPULAR).equals(TOP_RATED)) {
                    mAsyncTask popularTask = new mAsyncTask(POPULAR, this);
                    popularTask.execute(API_KEY);
                    finish();
                    startActivity(getIntent());
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    setSortPreferences(ORDER, POPULAR);
                }
                setSortPreferences(ORDER, POPULAR);
                break;

            case R.id.rated:
                if (getSortPreferences(ORDER, TOP_RATED).equals(POPULAR)) {
                    mAsyncTask ratedTask = new mAsyncTask(TOP_RATED, this);
                    ratedTask.execute(API_KEY);
                    this.finish();
                    startActivity(getIntent());
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                }
                if (getSortPreferences(ORDER, POPULAR).equals(POPULAR)) {
                    this.finish();
                    startActivity(getIntent());
                    setSortPreferences(ORDER, TOP_RATED);
                }
                setSortPreferences(ORDER, TOP_RATED);
                break;
            case R.id.favourite:
                Intent intent = new Intent(this, Favourites.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    class mAsyncTask extends AsyncTask<String, Void, String> {
        private static final String RESULTS = "results";
        private static final String TITLE = "original_title";
        private static final String POSTER = "poster_path";
        private static final String AVERAGE = "vote_average";
        private static final String DATE = "release_date";
        private static final String ID = "id";
        private static final String OVERVIEW = "overview";
        String orderBy;
        Context context;

        public mAsyncTask(String orderBy, Context context) {
            this.orderBy = orderBy;
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            String TMDB_API = params[0];
            String sort_by = orderBy;
            URL url = Networking.URL(sort_by, TMDB_API);
            try {
                String data = Networking.httpConnection(url);
                return data;
            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result == null) {
                // UX case of is connected or not

                linearLayout = (LinearLayout) findViewById(R.id.noInternet);
                movieslinearlayout = (LinearLayout) findViewById(R.id.MoviesLinearLayout);
                movieslinearlayout.setVisibility(LinearLayout.GONE);
                linearLayout.setVisibility(LinearLayout.VISIBLE);
            } else {


                super.onPostExecute(result);
                JSONObject root = null;
                try {
                    root = new JSONObject(result);
                    JSONArray results = root.getJSONArray(RESULTS);
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject jsonObject = results.getJSONObject(i);
                        String title = jsonObject.getString(TITLE);
                        int id = jsonObject.getInt(ID);
                        String poster = jsonObject.getString(POSTER);
                        String date = jsonObject.getString(DATE);
                        String average = jsonObject.getString(AVERAGE);
                        String overview = jsonObject.getString(OVERVIEW);
                        movieList.add(new MovieModel(title, poster, average, date, overview, id));

                        //check orientation state of device
                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                        } else {
                            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
                            recyclerView.setPadding(70, 0, 0, 0);


                        }
                        if (listState != null) {
                            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
                        }


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}

