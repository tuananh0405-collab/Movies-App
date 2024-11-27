package com.example.anhvt86_3.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anhvt86_3.R;
import com.example.anhvt86_3.databinding.ItemListBinding;
import com.example.anhvt86_3.domain.model.Movie;
import com.squareup.picasso.Picasso;


public class FavoriteAdapter extends ListAdapter<Movie, FavoriteAdapter.MovieListViewHolder> {
    public static final String BASE_IMG_URL = "https://image.tmdb.org/t/p/original";
    private View.OnClickListener onFavClickListener;

    public void setOnFavClickListener(View.OnClickListener onFavClickListener) {
        this.onFavClickListener = onFavClickListener;
    }


    public FavoriteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Movie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(Movie oldItem, Movie newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Movie oldItem, Movie newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public MovieListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListBinding binding = ItemListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MovieListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListViewHolder holder, int position) {
        Movie movie = getItem(position);
        holder.itemBinding.setMovie(movie);
        Picasso.get().load(BASE_IMG_URL.concat(movie.getPosterPath()))
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.err_image)
                .into(holder.itemBinding.moviePoster);

        holder.itemBinding.favouriteStar.setTag(movie);
        holder.itemBinding.favouriteStar.setOnClickListener(onFavClickListener);
    }

    public static class MovieListViewHolder extends RecyclerView.ViewHolder {
        ItemListBinding itemBinding;

        public MovieListViewHolder(@NonNull ItemListBinding itemView) {
            super(itemView.getRoot());
            itemBinding = itemView;
        }
    }
}
