package com.example.room.persistance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.room.model.MyWord
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [MyWord::class], version = 1, exportSchema = false)
abstract class MyRoomDatabase: RoomDatabase() {
    abstract fun getWordDao(): WordDao

    companion object {
        @Volatile
        private var INSTANCE: MyRoomDatabase? = null

        fun getDatabaseInstance(
            context: Context,
            scope: CoroutineScope): MyRoomDatabase {
            return (INSTANCE?:synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyRoomDatabase::class.java,
                        "word_databse")
                        .addCallback(WordDatabaseCallback(scope))
                        .build()
                INSTANCE = instance
                instance
            })
        }

        private class WordDatabaseCallback(private val scope: CoroutineScope): RoomDatabase.Callback(){

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                INSTANCE?.let {
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(it.getWordDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(wordDao: WordDao) {
            wordDao.deleteAll()
            var word = MyWord("Hello")
            wordDao.insertWord(word)

            var word1 = MyWord("World!")
            wordDao.insertWord(word1)
        }
    }
}