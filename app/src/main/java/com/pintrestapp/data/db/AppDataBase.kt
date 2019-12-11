package com.pintrestapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pintrestapp.data.db.entities.Quotes
import com.pintrestapp.data.db.entities.User

@Database(
    entities = [User::class,Quotes::class],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getQuotesDao():QuotesDao

    companion object {
        @Volatile
        private var instance: AppDataBase? = null

        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabese(context).also {
                instance = it
            }
        }

        private fun buildDatabese(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDataBase::class.java,
            "myDatabase.db"
        ).build()
    }
}