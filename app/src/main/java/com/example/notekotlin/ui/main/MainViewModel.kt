package com.example.notekotlin.ui.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Observer
import com.example.notekotlin.data.Repository
import com.example.notekotlin.data.model.Note
import com.example.notekotlin.data.model.Result
import com.example.notekotlin.ui.base.BaseViewModel
import com.example.notekotlin.data.model.Result.Error
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch


class MainViewModel(repository: Repository) :
        BaseViewModel<List<Note>?>() {
    private val notesChannel = repository.getNotes()

    init {
        launch {
            notesChannel.consumeEach {
                when (it) {
                    is Result.Success<*> -> setData(it.data as? List<Note>)
                    is Error -> setError(it.error)
                }
            }
        }
    }

    override fun onCleared() {
        notesChannel.cancel()
        super.onCleared()
    }
}