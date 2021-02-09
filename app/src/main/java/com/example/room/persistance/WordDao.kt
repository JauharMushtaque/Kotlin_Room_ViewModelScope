package com.example.room.persistance

import androidx.room.*
import com.example.room.model.MyWord
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Query ("SELECT * FROM word_table")
    fun getAllWords(): Flow<List<MyWord>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWord(word: MyWord)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()
}