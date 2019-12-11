package com.pintrestapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pintrestapp.data.db.entities.Quotes

@Dao
interface QuotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveallQuotes(quotes: List<Quotes>)
    @Query("SELECT * FROM Quotes")
    fun getQuotes():LiveData<List<Quotes>>
}