package com.example.anhvt86_3.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anhvt86_3.R;
import com.example.anhvt86_3.databinding.ItemGridBinding;
import com.example.anhvt86_3.databinding.ItemListBinding;
import com.example.anhvt86_3.domain.model.Movie;
import com.example.anhvt86_3.presentation.viewmodel.MovieViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MovieAdapter extends PagingDataAdapter<Movie, RecyclerView.ViewHolder> {

    public static final int TYPE_PROGRESS = 0;
    private static final int TYPE_ITEM = 1;

    public static final String BASE_IMG_URL = "https://image.tmdb.org/t/p/original";
    private boolean isGrid = false;
    private boolean isLoading = false;

    private View.OnClickListener onFavClickListener;
    private View.OnClickListener onItemClickListener;
    private MovieViewModel movieViewModel;

    public void setOnFavClickListener(View.OnClickListener onFavClickListener) {
        this.onFavClickListener = onFavClickListener;
    }

    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MovieAdapter(boolean isGrid, MovieViewModel movieViewModel) {
        super(DIFF_CALLBACK);
        this.isGrid = isGrid;
        this.movieViewModel = movieViewModel;
    }

    public void setGrid(boolean isGrid) {
        this.isGrid = isGrid;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TYPE_PROGRESS) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        } else {
            if (isGrid) {
                ItemGridBinding binding = ItemGridBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new MovieGridViewHolder(binding);
            } else {
                ItemListBinding binding = ItemListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new MovieListViewHolder(binding);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == TYPE_PROGRESS) {
            ((LoadingViewHolder) holder).showLoading();
            return;
        }


        Movie movie = getItem(position);
        if (isGrid) {
            MovieGridViewHolder viewHolder = (MovieGridViewHolder) holder;
            viewHolder.itemViewBinding.setMovie(movie);
            Picasso.get().load(BASE_IMG_URL.concat(movie.getPosterPath()))
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.err_image)
                    .into(viewHolder.itemViewBinding.moviePoster);

            viewHolder.itemViewBinding.getRoot().setTag(movie);
            viewHolder.itemViewBinding.getRoot().setOnClickListener(onItemClickListener);

        } else {
            MovieListViewHolder viewHolder = (MovieListViewHolder) holder;
            viewHolder.itemBinding.setMovie(movie);
            Picasso.get().load(BASE_IMG_URL.concat(movie.getPosterPath()))
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.err_image)
                    .into(viewHolder.itemBinding.moviePoster);

            viewHolder.itemBinding.favouriteStar.setTag(movie);
            viewHolder.itemBinding.favouriteStar.setOnClickListener(view -> {
                onFavClickListener.onClick(view);
                viewHolder.itemBinding.favouriteStar.setImageResource(movie.isFavorite() ? R.drawable.ic_like : R.drawable.ic_dislike);
            });
            viewHolder.itemBinding.getRoot().setTag(movie);
            viewHolder.itemBinding.getRoot().setOnClickListener(onItemClickListener);

            movieViewModel.getFavoriteIconLiveData().observe((LifecycleOwner) viewHolder.itemBinding.getRoot().getContext(), favStatusMap -> {
                if (favStatusMap.get(movie.getId()) != null)
                viewHolder.itemBinding.favouriteStar.setImageResource(favStatusMap.get(movie.getId()) ? R.drawable.ic_like : R.drawable.ic_dislike);
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_PROGRESS;
        } else {
            return TYPE_ITEM;
        }
    }

    public static class MovieListViewHolder extends RecyclerView.ViewHolder {
        ItemListBinding itemBinding;

        public MovieListViewHolder(@NonNull ItemListBinding itemView) {
            super(itemView.getRoot());
            itemBinding = itemView;
        }
    }

    public static class MovieGridViewHolder extends RecyclerView.ViewHolder {
        ItemGridBinding itemViewBinding;

        public MovieGridViewHolder(@NonNull ItemGridBinding itemView) {
            super(itemView.getRoot());
            itemViewBinding = itemView;
        }
    }

    public static DiffUtil.ItemCallback<Movie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.equals(newItem);
        }
    };

    public List<Movie> getCurrentList() {
        return snapshot().getItems();
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.loading_pb);
        }

        public void showLoading() {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
        notifyItemChanged(getItemCount());
    }


}
