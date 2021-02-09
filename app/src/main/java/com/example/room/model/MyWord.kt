package com.example.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table" )
data class MyWord(@PrimaryKey @ColumnInfo(name="word")  val word: String) {
}