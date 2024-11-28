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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.example.anhvt86_3.R;
import com.example.anhvt86_3.app.di.MyApplication;
import com.example.anhvt86_3.databinding.FragmentFavoriteBinding;
import com.example.anhvt86_3.domain.model.Movie;
import com.example.anhvt86_3.presentation.adapters.FavoriteAdapter;
import com.example.anhvt86_3.presentation.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
//import com.example.anhvt86_2.presentation.adapter.FavoriteMoviesAdapter;


public class FavoriteFragment extends Fragment {

    private FragmentFavoriteBinding binding;
    @Inject
    MovieViewModel movieViewModel;
    private FavoriteAdapter adapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((MyApplication) requireContext().getApplicationContext()).appComponent.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        adapter = new FavoriteAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            movieViewModel.getFavoriteMovies().observe(getViewLifecycleOwner(), movies -> {
                adapter.submitList(movies);
                binding.swipeRefreshLayout.setRefreshing(false);
            });
        });


        movieViewModel.getFavoriteMovies().observe(getViewLifecycleOwner(), movies -> {
            Log.d("TAG", "onCreateView: " + movies.size());
            adapter.submitList(movies);
        });

        adapter.setOnFavClickListener(v -> {
            Movie movie = (Movie) v.getTag();
            movie.setFavorite(!movie.isFavorite());
            movieViewModel.updateMovie(movie);
            int pos = adapter.getCurrentList().indexOf(movie);
            if (pos != -1) {
                adapter.notifyItemChanged(pos);
            }
        });
        adapter.setOnItemClickListener(v -> {
            Movie movie = (Movie) v.getTag();
            Bundle bundle = new Bundle();
            bundle.putInt("movieId", movie.getId());
//            bundle.putBoolean("isFavorite", true);
            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.detailFragment, bundle);
        });


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_fav, menu);
                MenuItem searchItem = menu.findItem(R.id.action_search);
                SearchView searchView = (SearchView) searchItem.getActionView();
                searchView.setQueryHint("Search Favorites");

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        filterFavoriteMovies(query);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        filterFavoriteMovies(newText);
                        return false;
                    }
                });
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private void filterFavoriteMovies(String query) {
        movieViewModel.getFavoriteMovies().observe(getViewLifecycleOwner(), movieListFavor -> {
            if (movieListFavor != null) {
                List<Movie> filteredList = new ArrayList<>();
                for (Movie movie : movieListFavor) {
                    if (movie.getTitle().toLowerCase().contains(query.toLowerCase())) {
                        filteredList.add(movie);
                    }
                }
                adapter.submitList(filteredList);
            }
        });
    }

}