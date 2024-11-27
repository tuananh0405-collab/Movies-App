package com.example.anhvt86_3.data.datasource.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.anhvt86_3.data.datasource.local.entity.MovieEntity;

import java.util.List;


@Dao
public interface MovieDAO {
    @Insert
    void insert(MovieEntity movieEntity);

    @Query("SELECT * FROM movie_table")
    LiveData<List<MovieEntity>> getAllMovies();

    @Query("DELETE FROM movie_table WHERE id = :id")
    void delete(int id);
}
