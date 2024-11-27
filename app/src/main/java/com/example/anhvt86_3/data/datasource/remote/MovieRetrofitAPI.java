package com.example.anhvt86_3.data.datasource.remote;

import com.example.anhvt86_3.data.datasource.remote.response.MovieResponse;

import javax.inject.Singleton;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

@Singleton
public interface MovieRetrofitAPI {
    @GET("3/movie/popular?api_key=468cae1037cdafe395896a5ce170330c")
    Single<MovieResponse> getMovies(@Query("page") int page);

    @GET("3/movie/{category}?api_key=40790235e5cf4da229722802d55515db")
    Single<MovieResponse> getMovies2(@Path("category") String category, @Query("page") int page);

}
