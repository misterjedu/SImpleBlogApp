//package com.misterjedu.simpleblogapp.roomModel
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//
//@Database(entities = [Comment::class], version = 3, exportSchema = false)
//abstract class CommentDataBase() : RoomDatabase() {
//
//    abstract fun postDao(): RoomDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: CommentDataBase? = null
//
//        fun getCommentDatabase(context: Context): CommentDataBase {
//            val tempInstance = INSTANCE
//            if (tempInstance != null) {
//                return tempInstance
//            }
//
//            synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    CommentDataBase::class.java,
//                    "comment_database"
//                ).fallbackToDestructiveMigration().build()
//
//                INSTANCE = instance
//
//                return instance
//            }
//
//        }
//
//    }
//}