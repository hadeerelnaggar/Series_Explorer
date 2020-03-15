package com.example.seriesexplorer.RoomDatabase;

import android.content.Context;

import com.example.seriesexplorer.model.Series;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Series.class},version = 1,exportSchema = false)
public abstract class AppDataBase  extends RoomDatabase
{
    private static final String DATABASE_NAME="wishlistseries";
    private static final Object LOCK = new Object();
    private static AppDataBase sInstance;

    public static AppDataBase getInstance(Context context)
    {
        if(sInstance==null)
        {
            synchronized (LOCK){
                sInstance= Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class,AppDataBase.DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;

    }

    public abstract TaskDao taskDao();
}
