package com.example.seriesexplorer.RoomDatabase;

import com.example.seriesexplorer.model.Series;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface TaskDao {
    @Query("SELECT * From wishlistseries")
    LiveData<List<Series>> loadAllTasks();

    @Query("DELETE From wishlistseries")
    void deleteAllTasks();

    @Query("select * from wishlistseries where wishlistseries.id= :id")
    Series getTaskById(String id);

    @Query("DELETE FROM wishlistseries Where wishlistseries.name = :name")
    void deleteSeriesByTitle(String name);

    @Insert
    void insertSeries(Series favoriteSeries);

    @Delete
    void deleteSeries(Series favoriteSeries);
}
