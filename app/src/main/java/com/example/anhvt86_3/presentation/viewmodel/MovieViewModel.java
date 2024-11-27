package com.example.anhvt86_3.presentation.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.PagingData;

import com.example.anhvt86_3.data.datasource.remote.response.CreditsResponse;
import com.example.anhvt86_3.domain.model.Movie;
import com.example.anhvt86_3.domain.model.Settings;
import com.example.anhvt86_3.domain.usecase.GetMoviesUseCase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class MovieViewModel extends ViewModel {
    private MutableLiveData<PagingData<Movie>> mMovieListLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsGrid = new MutableLiveData<>();
    private final CompositeDisposable mCompositeDisposable;
    private final GetMoviesUseCase mGetMoviesUseCase;
    private LiveData<List<Movie>> mFavoriteMoviesLiveData;

    @Inject
    public MovieViewModel(GetMoviesUseCase getMoviesUseCase) {
        mGetMoviesUseCase = getMoviesUseCase;
        mCompositeDisposable = new CompositeDisposable();
        getFavoriteMovies();
        mFavoriteMoviesLiveData.observeForever(movies -> updateFavoriteCount());

    }

    public MutableLiveData<PagingData<Movie>> getMovieList() {
        Disposable disposable = mGetMoviesUseCase.getMoviesFromAPI(ViewModelKt.getViewModelScope(this), settings.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mMovieListLiveData::setValue,
                        throwable -> Log.d("FATAL", "getMoviePagingData: " + throwable)
                );
        mCompositeDisposable.add(disposable);

        return mMovieListLiveData;
    }

    public MutableLiveData<Boolean> getIsGrid() {
        return mIsGrid;
    }

    public void setIsGrid(boolean isGrid) {
        mIsGrid.setValue(isGrid);
    }

    public void updateMovie(Movie movie) {
        mGetMoviesUseCase.updateMovie(movie);
//        favoriteIconLiveData.setValue();
        updateFavoriteIcon(movie.getId(), movie.isFavorite());
        // Update the movie list
        getMovieList(); // Reload the data
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        if (mFavoriteMoviesLiveData == null) {
            mFavoriteMoviesLiveData = mGetMoviesUseCase.getFavMovies();
        }
        return mFavoriteMoviesLiveData;
    }

    private final MutableLiveData<Settings> settings = new MutableLiveData<>(new Settings());
    public MutableLiveData<Settings> getSettings() {
        return settings;
    }
    public void updateSettings(String movieCategoryFilter, String sortOption, int pagesPerLoading, int movieRating, String releaseYear) {
        Settings builder = settings.getValue();
        if (builder != null) {
            builder.setCategorySetting(movieCategoryFilter)
                    .setSortSetting(sortOption)
                    .setPagesPerLoadingSetting(pagesPerLoading)
                    .setMovieRating(movieRating)
                    .setReleaseYear(releaseYear);

            settings.setValue(builder.build());
        }
        getMovieList();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
    }

    public LiveData<Movie> getMovieDetails(int movieId) {
        return mGetMoviesUseCase.getMovieDetails(movieId);
    }
    public LiveData<CreditsResponse> getMovieCredits(int movieId) {
        return mGetMoviesUseCase.getMovieCredits(movieId);
    }

    private final MutableLiveData<Integer> favoriteCountLiveData = new MutableLiveData<>(0);
    public MutableLiveData<Integer> getFavoriteCountLiveData() {
        return favoriteCountLiveData;
    }
    private void updateFavoriteCount() {
        List<Movie> favoriteMovies = mFavoriteMoviesLiveData.getValue();
        if (favoriteMovies != null) {
            favoriteCountLiveData.setValue(favoriteMovies.size());
        } else {
            favoriteCountLiveData.setValue(0);
        }
    }

    private final MutableLiveData<Map<Integer, Boolean>> favoriteIconLiveData = new MutableLiveData<>(new HashMap<>());

    public MutableLiveData<Map<Integer, Boolean>> getFavoriteIconLiveData() {
        return favoriteIconLiveData;
    }

    public void updateFavoriteIcon(int movieId, boolean isFavorite) {
        Map<Integer, Boolean> currentStatus = favoriteIconLiveData.getValue();
        if (currentStatus != null) {
            currentStatus.put(movieId, isFavorite);
            favoriteIconLiveData.setValue(currentStatus);
        }
    }
}
