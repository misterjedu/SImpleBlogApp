package com.misterjedu.simpleblogapp.roomModel

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Post::class, Comment::class], version = 4, exportSchema = false)
abstract class DataBase() : RoomDatabase() {

    abstract fun postDao(): RoomDao

    companion object {
        @Volatile
        private var INSTANCE: DataBase? = null

        fun getPostDatabase(context: Context): DataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "post_database"
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance

                return instance
            }

        }

    }
}