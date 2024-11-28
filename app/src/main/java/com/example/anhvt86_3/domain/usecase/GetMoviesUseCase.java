package com.example.anhvt86_3.domain.usecase;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagingData;

import com.example.anhvt86_3.data.datasource.remote.response.CreditsResponse;
import com.example.anhvt86_3.domain.model.Movie;
import com.example.anhvt86_3.domain.model.Settings;
import com.example.anhvt86_3.domain.repository.IMovieRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
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

    @SuppressLint("CheckResult")
    public LiveData<Movie> getMovieDetails(int movieId) {
        MutableLiveData<Movie> liveData = new MutableLiveData<>();
        movieRepository.getMovieDetails(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
//                .subscribe(liveData::postValue,
//                        throwable -> {
//                        });
                .subscribe(movie -> {
                    // Sau khi lấy thông tin chi tiết, lấy trạng thái "favorite" từ cơ sở dữ liệu
                    movieRepository.getMovieById(movieId).observeOn(Schedulers.io())
                            .subscribe(favoriteMovie -> {
                                if (favoriteMovie != null) {
                                    // Cập nhật trạng thái "favorite" cho đối tượng Movie
                                    movie.setFavorite(true);
                                } else {
                                    movie.setFavorite(false);
                                }
                                // Trả về đối tượng Movie đã cập nhật
                                liveData.postValue(movie);
                            }, throwable -> {
                                // Nếu không tìm thấy thông tin trong DB, mặc định là không phải favorite
                                movie.setFavorite(false);
                                liveData.postValue(movie);
                            });
                }, throwable -> {
                    liveData.postValue(null);
                });
        return liveData;
    }

    @SuppressLint("CheckResult")
    public LiveData<CreditsResponse> getMovieCredits(int movieId) {
        MutableLiveData<CreditsResponse> liveData = new MutableLiveData<>();
        movieRepository.getMovieCredits(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(liveData::postValue,
                        throwable -> {
                        });
        return liveData;
    }
}
