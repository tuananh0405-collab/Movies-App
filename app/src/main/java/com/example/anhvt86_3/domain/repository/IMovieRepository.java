package com.example.anhvt86_3.domain.repository;

import androidx.lifecycle.LiveData;
import androidx.paging.PagingData;

import com.example.anhvt86_3.data.datasource.remote.response.CreditsResponse;
import com.example.anhvt86_3.domain.model.Movie;
import com.example.anhvt86_3.domain.model.Settings;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import kotlinx.coroutines.CoroutineScope;

public interface IMovieRepository {
    Flowable<PagingData<Movie>> getMoviesFromAPI(CoroutineScope viewModelScope, Settings settings);

    void updateMovie(Movie movie);

    LiveData<List<Movie>> getMoviesFromDB();

    Single<Movie> getMovieDetails(int movieId);

    Single<CreditsResponse> getMovieCredits(int movieId);

    Single<Movie> getMovieById(int movieId);
}
