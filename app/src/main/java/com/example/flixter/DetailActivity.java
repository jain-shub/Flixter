package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixter.databinding.ActivityDetailBinding;
import com.example.flixter.databinding.ActivityMainBinding;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import Models.Movies;
import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity {

    private ActivityDetailBinding binding;

    private static final String YOUTUBE_API_KEY = "AIzaSyCX7Wn6_oIc-Q4XbmW5n5tzc2XBbsPbOSE";
    private static final String VIDEO_KEY = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String logTag = "DetailActivity";

    TextView tvTitle;
    TextView tvOverview;
    RatingBar ratingBar;
    YouTubePlayerView youtubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
//        setContentView(R.layout.activity_detail);
//        tvTitle = findViewById(R.id.tvTitle);
//        tvOverview = findViewById(R.id.tvOverview);
//        ratingBar = findViewById(R.id.ratingBar);
//        youtubePlayerView = findViewById(R.id.player);
        tvTitle = binding.tvTitle;
        tvOverview = binding.tvOverview;
        ratingBar = binding.ratingBar;
        youtubePlayerView = binding.player;

        final Movies movie = (Movies) Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        tvTitle.setText(movie.getTitle());
        ratingBar.setRating((float) movie.getRatings());
        tvOverview.setText(movie.getOverview());

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(VIDEO_KEY,movie.getMovieId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    if (results.length()==0){
                        return;
                    }
                    String youtubeKey = results.getJSONObject(0).getString("key");
                    double rating = movie.getRatings();
                    Log.d(logTag, youtubeKey);

                    intializeYoutube(youtubeKey, rating);

                } catch (JSONException e) {
                    Log.d(logTag, e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(logTag, "Failed to create instance");
            }
        });
    }

    private void intializeYoutube(final String youtubeKey, final double rating) {
        youtubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d(logTag, "Youtube video playing");
                if(rating < 6){
                    youTubePlayer.cueVideo(youtubeKey);
                }
                else{
                    youTubePlayer.loadVideo(youtubeKey);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }
}