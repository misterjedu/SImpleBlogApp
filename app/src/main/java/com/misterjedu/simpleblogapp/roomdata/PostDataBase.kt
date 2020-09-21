package com.misterjedu.simpleblogapp.roomdata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [RoomPost::class], version = 1, exportSchema = false)
abstract class PostDataBase() : RoomDatabase() {

    abstract fun postDao(): PostDao

    companion object {
        @Volatile
        private var INSTANCE: PostDataBase? = null

        @InternalCoroutinesApi
        fun getDatabase(context: Context): PostDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PostDataBase::class.java,
                    "post_database"
                ).build()

                INSTANCE = instance

                return instance
            }

        }

    }
}