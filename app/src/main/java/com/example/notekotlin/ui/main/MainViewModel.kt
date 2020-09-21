package com.example.notekotlin.ui.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Observer
import com.example.notekotlin.data.Repository
import com.example.notekotlin.data.model.Note
import com.example.notekotlin.data.model.Result
import com.example.notekotlin.ui.base.BaseViewModel
import com.example.notekotlin.data.model.Result.Error

class MainViewModel ( val repository: Repository = Repository) :
        BaseViewModel<List<Note>?, MainViewState>() {
    private val notesObserver = object : Observer<Result> {//Стандартный

        override fun onChanged (t: Result?) {
            if (t == null ) return
            when (t) {
                is com.example.notekotlin.data.model.Result.Success<*> -> {
// Может понадобиться вручную импортировать класс data.model.NoteResult.Success
                    viewStateLiveData.value = MainViewState(notes = t.data as? List<Note>)
                }
                is Error -> {
// Может понадобиться вручную импортировать класс data.model.NoteResult.Error
                    viewStateLiveData.value = MainViewState(error = t.error)
                }
            }
        }
    }
    private val repositoryNotes = repository.getNotes()
    init {
        viewStateLiveData.value = MainViewState()
        repositoryNotes.observeForever(notesObserver)
    }

    @VisibleForTesting
    public override fun onCleared () {
        repositoryNotes.removeObserver(notesObserver)
    }
}