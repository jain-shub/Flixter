package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixter.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapter.MoviesAdapter;
import Models.Movies;
import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static final String movieApi = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String logTag = "MainActivity";

    List<Movies> moviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

//        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        RecyclerView rvMovies = binding.rvMovies;
        moviesList = new ArrayList<>();
        // Create the adapter
        final MoviesAdapter adapter= new MoviesAdapter(this, moviesList);
        // Set adapter on recycler view
        rvMovies.setAdapter(adapter);
        // Set Layout manager on the recycler view
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(movieApi, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject jsonObj = json.jsonObject;
                try {
                    JSONArray resultArr = jsonObj.getJSONArray("results");
                    Log.d(logTag, "Results: " + resultArr.toString());
                    moviesList.addAll(Movies.fromMoviesArray(resultArr));
                    adapter.notifyDataSetChanged();
                    Log.d(logTag, "Movies: " + moviesList.size());


                } catch (JSONException e) {
                    Log.d(logTag, e.getMessage());
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(logTag, "OnFailure");
            }
        });
    }
}