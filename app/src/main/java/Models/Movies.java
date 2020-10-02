package Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movies {
    private String posterPath;
    private String backdropPath;
    private String title;
    private String overview;
    private double ratings;
    private int movieId;

    // required for Parcel usage
    public Movies() {
    }

    ;

    public Movies(JSONObject moviesObj) throws JSONException {
        this.posterPath = moviesObj.getString("poster_path");
        this.backdropPath = moviesObj.getString("backdrop_path");
        this.title = moviesObj.getString("title");
        this.overview = moviesObj.getString("overview");
        this.ratings = moviesObj.getDouble("vote_average");
        this.movieId = moviesObj.getInt("id");
    }

    public static List<Movies> fromMoviesArray(JSONArray moviesArr) throws JSONException {
        List<Movies> moviesList = new ArrayList<>();
        for (int i = 0; i < moviesArr.length(); i++) {
            moviesList.add(new Movies(moviesArr.getJSONObject(i)));
        }
        return moviesList;
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w780/%s", backdropPath);
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getRatings() {
        return ratings;
    }

    public int getMovieId() {
        return movieId;
    }
}
