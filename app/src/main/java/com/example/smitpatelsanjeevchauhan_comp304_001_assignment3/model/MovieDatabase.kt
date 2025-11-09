package com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var Instance: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "moviedb"
                ).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        CoroutineScope(Dispatchers.IO).launch {
                            getDatabase(context).movieDao().insertSampleMovies(
                                listOf(
                                    Movie(11, "Inception", "Christopher Nolan", 20.0, "2010-07-16", 148, "Thriller", true),
                                    Movie(22, "The Lion King", "Roger Allers", 15.0, "1994-06-24", 88, "Family", false),
                                    Movie(33, "The Avengers", "Joss Whedon", 25.0, "2012-05-04", 143, "Action", false)
                                )
                            )
                        }
                    }
                }).build().also { Instance = it }
            }
        }
    }
}