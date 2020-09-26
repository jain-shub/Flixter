package Adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.media.VolumeShaper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixter.R;

import java.util.ArrayList;
import java.util.List;

import Models.Movies;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    Context context;
    List<Movies> moviesList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View movieView = inflater.inflate(R.layout.item_movie, parent, false);
        ViewHolder viewHolder = new ViewHolder(movieView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get the movie at passed in position
        Movies movie = moviesList.get(position);

        // Bind the movie data into VH
        holder.bind(movie);

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public MoviesAdapter(Context context, List<Movies> moviesList) {
        this.moviesList = moviesList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivPoster;
        public TextView tvTitle;
        public TextView tvOverview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = (ImageView) itemView.findViewById(R.id.ivPoster);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
        }


        public void bind(Movies movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            String imageUrl;

            // if phone is in landscape mode
            // backdrop path
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageUrl = movie.getBackdropPath();
            }

            // else poster path
            else {
                imageUrl = movie.getPosterPath();
            }

            Glide.with(context).load(imageUrl).into(ivPoster);
        }
    }
}
