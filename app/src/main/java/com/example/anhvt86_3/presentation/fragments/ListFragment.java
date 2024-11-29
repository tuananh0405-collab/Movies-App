package com.example.anhvt86_3.presentation.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.paging.LoadState;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.anhvt86_3.R;
import com.example.anhvt86_3.app.di.MyApplication;
import com.example.anhvt86_3.databinding.FragmentListBinding;
import com.example.anhvt86_3.domain.model.Movie;
import com.example.anhvt86_3.presentation.adapters.MovieAdapter;
import com.example.anhvt86_3.presentation.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class ListFragment extends Fragment {

    private FragmentListBinding binding;
    private boolean mIsGrid;
    private MovieAdapter adapter;
    private PagingData<Movie> movies;
    @Inject
    MovieViewModel movieViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((MyApplication) requireContext().getApplicationContext()).appComponent.inject(this);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsGrid = Boolean.TRUE.equals(movieViewModel.getIsGrid().getValue());

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);

        setupRecyclerView(mIsGrid);

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            movieViewModel.getMovieList().observe(getViewLifecycleOwner(), movies -> {
                this.movies = movies;
                adapter.submitData(getLifecycle(), movies);
                binding.swipeRefreshLayout.setRefreshing(false);
            });
        });


        movieViewModel.getMovieList().observe(getViewLifecycleOwner(), movies -> {
            this.movies = movies;
            adapter.submitData(getLifecycle(), movies);
            binding.pbLoading.setVisibility(View.GONE);
        });


        movieViewModel.getIsGrid().observe(getViewLifecycleOwner(), isGrid -> {
            setupRecyclerView(isGrid);
            if (movies != null) {
                adapter.submitData(getLifecycle(), movies);
            }
        });

        movieViewModel.getSettings().observe(getViewLifecycleOwner(), settings -> {
            if (settings != null) {
                Log.d("TAG", "onCreateView: " + settings.toString());
                if (movies != null) {
                    adapter.submitData(getLifecycle(), movies);
                    List<Movie> filteredList = new ArrayList<>();

                }
            }
        });


        adapter.setOnFavClickListener(v -> {
            Movie movie = (Movie) v.getTag();
            movie.setFavorite(!movie.isFavorite());
            movieViewModel.updateMovie(movie);
//            int position = adapter.getCurrentList().indexOf(movie);
//            if (position != -1) {
//                adapter.notifyItemChanged(position);
//            }
        });

        adapter.setOnItemClickListener(view -> {
            Movie movie = (Movie) view.getTag();
            Bundle bundle = new Bundle();
            bundle.putInt("movieId", movie.getId());
//            bundle.putBoolean("isFavorite", movie.isFavorite());
            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.detailFragment, bundle);
        });

        movieViewModel.getNotifyMovieId().observe(getViewLifecycleOwner(), movieId -> {
            if (movieId != -1) {
                Bundle bundle = new Bundle();
                bundle.putInt("movieId", movieId);
                NavController navController = Navigation.findNavController(requireView());
                navController.navigate(R.id.detailFragment, bundle);
                movieViewModel.setNotifyMovieId(-1);

            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_list, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.action_switch_view) {
                    handleSwitchView(menuItem);
                    return true;
                } else if (menuItem.getItemId() == R.id.popularMovies) {
                    handleOptionsMenu("popular");
                    return true;
                } else if (menuItem.getItemId() == R.id.topRatedMovies) {
                    handleOptionsMenu("top_rated");
                    return true;
                } else if (menuItem.getItemId() == R.id.upcomingMovies) {
                    handleOptionsMenu("upcoming");
                    return true;
                } else if (menuItem.getItemId() == R.id.nowPlayingMovies) {
                    handleOptionsMenu("now_playing");
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private void handleOptionsMenu(String newCategory) {
        String sort = movieViewModel.getSettings().getValue().getSortSetting();
        int pagesPerLoading = movieViewModel.getSettings().getValue().getPagesPerLoadingSetting();
        int movieRating = movieViewModel.getSettings().getValue().getMovieRating();
        String releaseYear = movieViewModel.getSettings().getValue().getReleaseYear();

        movieViewModel.updateSettings(newCategory, sort, pagesPerLoading, movieRating, releaseYear);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("com.example.anhvt86_3_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("filter_movie_category", newCategory);
        editor.apply();
    }

    private void handleSwitchView(MenuItem menuItem) {
        int currentPosition = ((LinearLayoutManager) binding.rcvMovie.getLayoutManager()).findFirstVisibleItemPosition();

        mIsGrid = !mIsGrid;

        RecyclerView.LayoutManager layoutManager;
        if (!mIsGrid) {
            layoutManager = new LinearLayoutManager(requireContext());
        } else {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return adapter.getItemViewType(position) == MovieAdapter.TYPE_PROGRESS ? 2 : 1;
                }
            });
            layoutManager = gridLayoutManager;
        }
        adapter.setGrid(mIsGrid);

        binding.rcvMovie.setLayoutManager(layoutManager);
        binding.rcvMovie.setAdapter(adapter);
        binding.rcvMovie.scrollToPosition(currentPosition);

        menuItem.setTitle(!mIsGrid ? "Switch to Grid View" : "Switch to List View");
        menuItem.setIcon(!mIsGrid ? R.drawable.ic_list : R.drawable.ic_grid);
    }


    public void setupRecyclerView(boolean isGrid) {
        adapter = new MovieAdapter(isGrid, movieViewModel);
        LinearLayoutManager layoutManager;
        if (isGrid) {
            layoutManager = new GridLayoutManager(getContext(), 2);
        } else {
            layoutManager = new LinearLayoutManager(getContext());
        }

        adapter.addLoadStateListener(loadState -> {
            binding.rcvMovie.post(() -> {
                adapter.setLoading(loadState.getAppend() instanceof LoadState.Loading);
            });
            return null;
        });

        binding.rcvMovie.setLayoutManager(layoutManager);
        binding.rcvMovie.setAdapter(adapter);

    }
}