package com.example.anhvt86_3.presentation.fragments;

import android.content.Context;
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
import androidx.paging.LoadState;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


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
//    private boolean isGrid = false;
private PagingData<Movie> movies;
//
    @Inject
MovieViewModel movieViewModel;
//
//    @Inject
//    FavoriteMovieViewModel favoriteMovieViewModel;
//
//
//
@Override
public void onAttach(@NonNull Context context) {
    super.onAttach(context);
//        ((MainActivity) requireActivity()).getAppComponent().inject(this);
    ((MyApplication) requireContext().getApplicationContext()).appComponent.inject(this);

}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        DaggerAppComponent.create().inject(this);
//        mViewModel = new ViewModelProvider(requireActivity()).get(MovieListViewModel.class);
        mIsGrid = Boolean.TRUE.equals(movieViewModel.getIsGrid().getValue());
        // Lắng nghe sự thay đổi cài đặt

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);

        setupRecyclerView(mIsGrid);

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
//                mViewModel.updateSettings(settings.getCategorySetting(), settings.getSortSetting(), settings.getPagesPerLoadingSetting());
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
            int position = adapter.getCurrentList().indexOf(movie);
            if (position != -1) {
                adapter.notifyItemChanged(position);
            }
        });


        return binding.getRoot();
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        ((MainActivity) requireActivity()).getAppComponent().inject(this);
//
//        adapter = new MovieAdapter(this::onItemClick,requireContext(), isGrid, favoriteMovieViewModel);
//        binding.rcvMovie.setAdapter(adapter);
//        setupRecyclerView();
//        observeMovies();
//
//        adapter.addLoadStateListener(loadState -> {
//            if (loadState.getAppend() instanceof LoadState.Loading) {
//                adapter.setNetworkState(new NetworkState(NetworkState.Status.LOADING, "Loading"));
//            } else if (loadState.getAppend() instanceof LoadState.Error) {
//                adapter.setNetworkState(new NetworkState(NetworkState.Status.LOADED, "Error"));
//                Toast.makeText(getContext(), "Error loading more data", Toast.LENGTH_SHORT).show();
//            } else {
//                adapter.setNetworkState(new NetworkState(NetworkState.Status.LOADED, "Success"));
//            }
//            return null;
//        });
//        requireActivity().addMenuProvider(new MenuProvider() {
//            @Override
//            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
//                menuInflater.inflate(R.menu.menu_list, menu);
//            }
//
//            @Override
//            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
//                if (menuItem.getItemId() == R.id.action_switch_view) {
//                    handleSwitchView(menuItem); // Call the method to switch views
//                    return true;
//                }
//                return false;
//            }
//        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
//    }
//    private void handleSwitchView(MenuItem menuItem) {
//        int currentPosition = ((LinearLayoutManager) binding.rcvMovie.getLayoutManager()).findFirstVisibleItemPosition();
//
//        isGrid = !isGrid;
//
//        RecyclerView.LayoutManager layoutManager;
//        if (!isGrid) {
//            layoutManager = new LinearLayoutManager(requireContext());
//        } else {
//            GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
//            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                @Override
//                public int getSpanSize(int position) {
//                    return adapter.getItemViewType(position) == MovieAdapter.TYPE_PROGRESS ? 2 : 1;
//                }
//            });
//            layoutManager = gridLayoutManager;
//        }
//        adapter.setGrid(isGrid);
//
//        binding.rcvMovie.setLayoutManager(layoutManager);
//        binding.rcvMovie.setAdapter(adapter);
//        binding.rcvMovie.scrollToPosition(currentPosition);
//
//        menuItem.setTitle(!isGrid ? "Switch to Grid View" : "Switch to List View");
//        menuItem.setIcon(!isGrid ? R.drawable.ic_list : R.drawable.ic_grid);
//    }
//
//    public void setupRecyclerView() {
//        binding.rcvMovie.setLayoutManager(isGrid
//                ? new GridLayoutManager(requireContext(), 2)
//                : new LinearLayoutManager(requireContext()));
//        binding.rcvMovie.setAdapter(adapter);
//    }
//
//    private void observeMovies() {
//        movieViewModel.getMovies().observe(getViewLifecycleOwner(), pagingData -> {
//            if (pagingData != null) {
//                adapter.submitData(getLifecycle(), pagingData);
//            } else {
//                Toast.makeText(getContext(), "Failed loading", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void onItemClick(Movie movie) {
//        Bundle bundle = new Bundle();
//        bundle.putInt("movieId", movie.getId());
//        NavController navController = Navigation.findNavController(requireView());
//        navController.navigate(R.id.detailFragment, bundle);
//    }

    public void setupRecyclerView(boolean isGrid) {
        adapter = new MovieAdapter(isGrid);
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