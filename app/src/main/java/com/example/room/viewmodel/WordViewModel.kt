package com.example.room.viewmodel

import androidx.lifecycle.*
import com.example.room.model.MyWord
import com.example.room.repository.WordRepository
import kotlinx.coroutines.launch

class WordViewModel(val repository: WordRepository): ViewModel() {

    val allWords: LiveData<List<MyWord>> = repository.allWords.asLiveData()

//check heck here ViewModelScope is required or not, it may get crash sometime
    fun insert(word: MyWord){
        viewModelScope.launch {
            repository.insertWord(word)
        }
    }
}


class WordViewModelFactory(val repository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}