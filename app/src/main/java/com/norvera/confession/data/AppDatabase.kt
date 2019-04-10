package com.norvera.confession.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.framework.AssetSQLiteOpenHelperFactory
import com.norvera.confession.R
import com.norvera.confession.data.dao.*
import com.norvera.confession.data.models.*
import timber.log.Timber

@Database(
    entities = [ExaminationEntry::class, CommandmentEntry::class, GuideEntry::class, InspirationEntry::class, PrayersEntry::class, ExaminationActiveEntry::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // DAO
    abstract fun examinationDao(): ExaminationDao
    abstract fun commandmentDao(): CommandmentDao
    abstract fun guideDao(): GuideDao
    abstract fun prayersDao(): PrayersDao
    abstract fun inspirationDao(): InspirationDao

    companion object {

        private val LOG_TAG = AppDatabase::class.java.simpleName
        private val LOCK = Any()
        @Volatile
        private var sInstance: AppDatabase? = null


        fun getInstance(context: Context): AppDatabase? {
            if (sInstance == null) {
                synchronized(LOCK) {
                    Timber.tag(LOG_TAG).d("Creating new database instance")
                    sInstance = create(context)
                }
            }
            Timber.tag(LOG_TAG).d("Getting the database instance")
            return sInstance
        }

        internal fun create(context: Context): AppDatabase {
            val b = Room.databaseBuilder(
                context.applicationContext, AppDatabase::class.java,
                context.getString(R.string.database_name)
            )

            return b.openHelperFactory(AssetSQLiteOpenHelperFactory()).build()
        }
    }
}

