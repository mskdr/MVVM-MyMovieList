package com.muhammetkdr.mymovielist.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muhammetkdr.mymovielist.models.MoviesResponse

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(movies: MoviesResponse): Long

    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<MoviesResponse>>

    @Delete
    suspend fun deleteMovie(movie: MoviesResponse)
}