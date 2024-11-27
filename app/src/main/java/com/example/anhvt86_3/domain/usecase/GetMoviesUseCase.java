package com.example.anhvt86_3.domain.usecase;

import androidx.lifecycle.LiveData;
import androidx.paging.PagingData;

import com.example.anhvt86_3.domain.model.Movie;
import com.example.anhvt86_3.domain.model.Settings;
import com.example.anhvt86_3.domain.repository.IMovieRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import kotlinx.coroutines.CoroutineScope;

public class GetMoviesUseCase {
    private final IMovieRepository movieRepository;

    @Inject
    public GetMoviesUseCase(IMovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Flowable<PagingData<Movie>> getMoviesFromAPI(CoroutineScope viewModelScope, Settings settings) {
        return movieRepository.getMoviesFromAPI(viewModelScope, settings);
    }

    public void updateMovie(Movie movie) {
        movieRepository.updateMovie(movie);
    }

    public LiveData<List<Movie>> getFavMovies() {
        return movieRepository.getMoviesFromDB();
    }

}
