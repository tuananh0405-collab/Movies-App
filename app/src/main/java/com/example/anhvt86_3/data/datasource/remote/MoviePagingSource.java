package com.example.anhvt86_3.data.datasource.remote;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava2.RxPagingSource;

import com.example.anhvt86_3.data.repository.MovieRepositoryImpl;
import com.example.anhvt86_3.domain.model.Movie;
import com.example.anhvt86_3.domain.model.Settings;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class MoviePagingSource extends RxPagingSource<Integer, Movie> {
    private List<Movie> mMovieList;
    private final MovieRetrofitAPI movieRetrofitAPI;
    private final MovieRepositoryImpl movieRepository;
    private Settings settings;

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    @Inject
    public MoviePagingSource(MovieRetrofitAPI movieRetrofitAPI, MovieRepositoryImpl movieRepository) {
        this.movieRetrofitAPI = movieRetrofitAPI;
        this.movieRepository = movieRepository;
    }

    @NonNull
    @Override
    public Single<LoadResult<Integer, Movie>> loadSingle(@NonNull LoadParams<Integer> loadParams) {
        int page = loadParams.getKey() != null ? loadParams.getKey() : 1;

        return movieRetrofitAPI.getMovies2(settings.getCategorySetting(), page)
                .subscribeOn(Schedulers.io())
                .map(response -> {
                    mMovieList = response.getResults();

                    movieRepository.updateFavoriteStatus(mMovieList);

                    if (settings.getMovieRating() > 0) {
                        mMovieList = mMovieList.stream()
                                .filter(movie -> movie.getRating() >= settings.getMovieRating())
                                .collect(Collectors.toList());
                    }

                    if (settings.getReleaseYear() != null && !settings.getReleaseYear().isEmpty()) {
//                        mMovieList = mMovieList.stream()
//                                .filter(movie -> movie.getReleaseDate().startsWith(settings.getReleaseYear()))
//                                .collect(Collectors.toList());
                    }

                    if ("rating".equals(settings.getSortSetting())) {
                        Collections.sort(mMovieList, Comparator.comparingDouble(Movie::getRating).reversed());
                    } else if ("release_date".equals(settings.getSortSetting())) {
                        Collections.sort(mMovieList, Comparator.comparing(Movie::getReleaseDate).reversed());
                    }

                    return toLoadResult(mMovieList, page, response.getTotalPages());
                })
                .onErrorReturn(LoadResult.Error::new);

    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Movie> pagingState) {
        Integer anchorPosition = pagingState.getAnchorPosition();

        if (anchorPosition != null) {
            return (anchorPosition / 20) + 1;
        }
        return null;
    }

    private LoadResult<Integer, Movie> toLoadResult(List<Movie> results, Integer page, int totalPages) {
        return new LoadResult.Page<>(
                results,
                page == 1 ? null : page - 1,
                page < totalPages ? page + 1 : null
        );
    }

//    private LoadResult<Integer, Movie> toLoadResult(List<Movie> results, Integer page, int totalPages) {
//        return new LoadResult.Page<>(
//                results,
//                page == 1 ? null : page - settings.getPagesPerLoadingSetting(),
//                page < totalPages ? page + settings.getPagesPerLoadingSetting() : null
//        );
//    }
//}
}
