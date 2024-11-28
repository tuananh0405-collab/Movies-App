package com.example.anhvt86_3.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava2.PagingRx;

import com.example.anhvt86_3.app.utils.Constants;
import com.example.anhvt86_3.data.datasource.local.AppDatabase;
import com.example.anhvt86_3.data.datasource.local.dao.MovieDAO;
import com.example.anhvt86_3.data.datasource.local.entity.MovieEntity;
import com.example.anhvt86_3.data.datasource.remote.MoviePagingSource;
import com.example.anhvt86_3.data.datasource.remote.MovieRetrofitAPI;
import com.example.anhvt86_3.data.datasource.remote.response.CreditsResponse;
import com.example.anhvt86_3.data.mapper.MovieMapper;
import com.example.anhvt86_3.domain.model.Movie;
import com.example.anhvt86_3.domain.model.Settings;
import com.example.anhvt86_3.domain.repository.IMovieRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import io.reactivex.Flowable;
import io.reactivex.Single;
import kotlinx.coroutines.CoroutineScope;

public class MovieRepositoryImpl implements IMovieRepository {

    private final PagingConfig pagingConfig = new PagingConfig(
            5,
            5,
            false,
            10,
            5 + 2 * 5
    );
    private MovieDAO mMovieDAO;
    private List<MovieEntity> favoriteMovies = new ArrayList<>();

    private final Provider<MoviePagingSource> movieRemoteDataSourceProvider;
    private final MovieRetrofitAPI retrofitAPI;

    @Inject
    public MovieRepositoryImpl(Provider<MoviePagingSource> movieRemoteDataSourceProvider, Application application, MovieRetrofitAPI retrofitAPI) {
        this.movieRemoteDataSourceProvider = movieRemoteDataSourceProvider;
        this.retrofitAPI = retrofitAPI;
        AppDatabase database = AppDatabase.getInstance(application);
        mMovieDAO = database.movieDAO();
        mMovieDAO.getAllMovies().observeForever(favMovies -> {
            if (favMovies != null) {
                favoriteMovies = favMovies;
            }
        });

    }

    @Override
    public Flowable<PagingData<Movie>> getMoviesFromAPI(CoroutineScope viewModelScope, Settings settings) {
        movieRemoteDataSourceProvider.get().setSettings(settings);
        Flowable<PagingData<Movie>> cachedInFlowable = PagingRx.cachedIn(
                PagingRx.getFlowable(new Pager<>(pagingConfig, movieRemoteDataSourceProvider::get)),
                viewModelScope
        );
        return cachedInFlowable;
    }

    @Override
    public void updateMovie(Movie movie) {
        if (movie.isFavorite()) {
            mMovieDAO.insert(new MovieEntity(movie.getId(), movie.getTitle(), movie.getReleaseDate(), movie.getRating(), movie.isAdult(), movie.getOverview(), movie.getPosterPath(), movie.isFavorite()));
        } else {
            mMovieDAO.delete(movie.getId());
        }
    }

    @Override
    public LiveData<List<Movie>> getMoviesFromDB() {
        return Transformations.map(mMovieDAO.getAllMovies(), movieEntities -> {
            List<Movie> movies = new ArrayList<>();
            for (MovieEntity entity : movieEntities) {
                movies.add(MovieMapper.FromMovieEntityToMovie(entity));
            }
            return movies;
        });
    }

    public void updateFavoriteStatus(List<Movie> movies) {
        for (Movie movie : movies) {
            for (MovieEntity favoriteMovie : favoriteMovies) {
                if (movie.getId() == favoriteMovie.getId()) {
                    movie.setFavorite(true);
                    break;
                }
            }
        }
    }


    @Override
    public Single<Movie> getMovieDetails(int movieId) {
        return retrofitAPI.getMovieDetails(movieId, Constants.API_KEY);
    }

    @Override
    public Single<CreditsResponse> getMovieCredits(int movieId) {
        return retrofitAPI.getMovieCredits(movieId, Constants.API_KEY);
    }

    @Override
    public Single<Movie> getMovieById(int movieId) {
//        return Transformations.map( mMovieDAO.getMovieById(movieId), MovieMapper::FromMovieEntityToMovie);
        return mMovieDAO.getMovieById(movieId).map(MovieMapper::FromMovieEntityToMovie);
    }
}
