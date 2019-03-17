package com.norvera.confession.data;

import android.content.Context;
import android.util.Log;

import com.norvera.confession.data.dao.CommandmentDao;
import com.norvera.confession.data.dao.ExaminationDao;
import com.norvera.confession.data.dao.GuideDao;
import com.norvera.confession.data.dao.InspirationDao;
import com.norvera.confession.data.dao.PrayersDao;
import com.norvera.confession.data.models.CommandmentEntry;
import com.norvera.confession.data.models.ExaminationActiveEntry;
import com.norvera.confession.data.models.ExaminationEntry;
import com.norvera.confession.data.models.GuideEntry;
import com.norvera.confession.data.models.InspirationEntry;
import com.norvera.confession.data.models.PersonToSinEntry;
import com.norvera.confession.data.models.PrayersEntry;

import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.framework.AssetSQLiteOpenHelperFactory;

@Database(entities = {
                ExaminationEntry.class,
                CommandmentEntry.class,
                GuideEntry.class,
                InspirationEntry.class,
                PersonToSinEntry.class,
                PrayersEntry.class,
                ExaminationActiveEntry.class
        },
        version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "confession.db";
    private static volatile AppDatabase sInstance;

    // DAO
    public abstract ExaminationDao examinationDao();
    public abstract CommandmentDao commandmentDao();
    public abstract GuideDao guideDao();
    public abstract PrayersDao prayersDao();


    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = create(context);
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return (sInstance);
    }

    static AppDatabase create(Context context) {
        RoomDatabase.Builder<AppDatabase> b =
                Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                        DATABASE_NAME);

        return (b.openHelperFactory(new AssetSQLiteOpenHelperFactory()).build());
    }

    public abstract InspirationDao inspirationDao();
}

