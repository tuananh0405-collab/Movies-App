package com.example.anhvt86_3.data.datasource.remote;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava2.RxPagingSource;

import com.example.anhvt86_3.data.datasource.local.entity.MovieEntity;
import com.example.anhvt86_3.data.datasource.remote.response.MovieResponse;
import com.example.anhvt86_3.data.repository.MovieRepositoryImpl;
import com.example.anhvt86_3.domain.model.Movie;
import com.example.anhvt86_3.domain.model.Settings;

import java.util.ArrayList;
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
    private List<MovieEntity> favoriteMovies;

    public void setFavoriteMovies(List<MovieEntity> favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    @Inject
    public MoviePagingSource(MovieRetrofitAPI movieRetrofitAPI, MovieRepositoryImpl movieRepository) {
        this.movieRetrofitAPI = movieRetrofitAPI;
        this.movieRepository = movieRepository;
    }
    MovieResponse response;
    @NonNull
    @Override
    public Single<LoadResult<Integer, Movie>> loadSingle(@NonNull LoadParams<Integer> loadParams) {
        Log.e("Load", loadParams.getLoadSize() +"");
        int page = loadParams.getKey() != null ? loadParams.getKey() : 1;
        List<Single<MovieResponse>> apiCalls = new ArrayList<>();
        apiCalls.add(movieRetrofitAPI.getMovies2(settings.getCategorySetting(), page).subscribeOn(Schedulers.io()));
        Log.e("fav", favoriteMovies.size() + "");
        return Single.zip(apiCalls, objects -> {
            mMovieList = new ArrayList<>();
            for (Object obj : objects) {
                if (obj instanceof MovieResponse) {
                     response = (MovieResponse) obj;
                    if (response.getResults() != null) {
                        mMovieList.addAll(response.getResults());
                    }
                } else {
                    // Handle unexpected object type
                    throw new IllegalStateException("Unexpected type in Single.zip result");
                }
            }
            if (favoriteMovies != null) {
                for (Movie movie : mMovieList) {
                    for (MovieEntity favoriteMovie : favoriteMovies) {
                        if (movie.getId() == favoriteMovie.getId()) {
                            movie.setFavorite(true);
                            break;
                        }
                    }
                }

            }
            if (settings.getMovieRating() > 0) {
                mMovieList = mMovieList.stream()
                        .filter(movie -> movie.getRating() >= settings.getMovieRating())
                        .collect(Collectors.toList());
            }

            if (settings.getReleaseYear() != null && !settings.getReleaseYear().isEmpty()) {
                Log.e("Setting", "loadSingle: " + settings.getReleaseYear());
                mMovieList = mMovieList.stream()
                        .filter(movie -> movie.getReleaseDate().startsWith(settings.getReleaseYear()))
                        .collect(Collectors.toList());
            }

            if (settings.getSortSetting()!=null&&"rating".equals(settings.getSortSetting())) {
                mMovieList.sort(Comparator.comparingDouble(Movie::getRating).reversed());
            } else if ("release_date".equals(settings.getSortSetting())) {
                Collections.sort(mMovieList, Comparator.comparing(Movie::getReleaseDate).reversed());
            }

//            return toLoadResult(mMovieList, page, mMovieList.size());
            if (mMovieList.size() < loadParams.getLoadSize()) {
                // Trả về LoadResult với kết quả ít hơn, nhưng vẫn cho phép tiếp tục load
                return new LoadResult.Page<>(
                        mMovieList,
                        page == 1 ? null : page - 1,
                        page + 1
                );
            } else {
                // Trả về kết quả bình thường nếu có đủ dữ liệu
                return new LoadResult.Page<>(
                        mMovieList,
                        page == 1 ? null : page - 1,
                        page < response.getTotalPages() ? page + 1 : null
                );
            }


        });
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

    private LoadResult<Integer, Movie> toLoadResult(List<Movie> results, Integer page,
                                                    int totalPages) {
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
