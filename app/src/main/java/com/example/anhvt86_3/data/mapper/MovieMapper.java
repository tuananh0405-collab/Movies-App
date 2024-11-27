package com.example.anhvt86_3.data.mapper;


import com.example.anhvt86_3.data.datasource.local.entity.MovieEntity;
import com.example.anhvt86_3.domain.model.Movie;

public class MovieMapper {
    public static Movie FromMovieEntityToMovie(MovieEntity object) {
        Movie movie = new Movie();
        movie.setId(object.getId());
        movie.setTitle(object.getTitle());
        movie.setReleaseDate(object.getReleaseDate());
        movie.setRating(object.getRating());
        movie.setAdult(object.isAdult());
        movie.setOverview(object.getOverview());
        movie.setPosterPath(object.getPosterPath());
        movie.setFavorite(object.isFavorite());
        return movie;

    }
}
