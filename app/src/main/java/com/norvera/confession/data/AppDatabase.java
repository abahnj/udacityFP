package com.norvera.confession.data;

import android.content.Context;
import android.util.Log;

import com.norvera.confession.data.dao.SinDao;
import com.norvera.confession.data.models.Sin;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities={Sin.class}, version=1)
abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "confession.db";
    private static volatile AppDatabase sInstance;

    // DAO
    public abstract SinDao sinDao();


    public static AppDatabase getInstance(Context context) {
        if (sInstance==null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance=create(context);
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return(sInstance);
    }

    static AppDatabase create(Context context) {
        RoomDatabase.Builder<AppDatabase> b=
                Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                        DATABASE_NAME);

        return(b.openHelperFactory(new androidx.sqlite.db.framework.AssetSQLiteOpenHelperFactory()).build());
    }
}

