package com.romix.videoplayer.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope


@Database(entities = arrayOf(VideoEntity::class), version = 1, exportSchema = false)
abstract class VideoDatabase: RoomDatabase() {
    abstract fun videoDao(): VideoDao

    companion object {
        private var INSTANCE: VideoDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): VideoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    VideoDatabase::class.java,
                    "video_db")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}