package com.example.room


import android.app.Application
import com.example.room.persistance.MyRoomDatabase
import com.example.room.repository.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordsApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { MyRoomDatabase.getDatabaseInstance(this, applicationScope) }


    val repository by lazy { WordRepository(database.getWordDao()) }
}