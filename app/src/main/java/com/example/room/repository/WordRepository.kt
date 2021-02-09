package com.example.room.repository

import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Insert
import com.example.room.model.MyWord
import com.example.room.persistance.WordDao
import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao:WordDao) {
    val allWords :Flow<List<MyWord>> = wordDao.getAllWords()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertWord(word: MyWord){
        wordDao.insertWord(word)
    }
}