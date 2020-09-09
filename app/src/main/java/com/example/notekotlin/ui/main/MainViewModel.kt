package com.example.notekotlin.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.notekotlin.data.Repository
import com.example.notekotlin.data.model.Note
import com.example.notekotlin.data.model.NoteResult
import com.example.notekotlin.ui.base.BaseViewModel
import com.example.notekotlin.data.model.NoteResult.Error

class MainViewModel ( val repository: Repository = Repository) :
        BaseViewModel<List<Note>?, MainViewState>() {
    private val notesObserver = object : Observer<NoteResult> {//Стандартный

        override fun onChanged (t: NoteResult ?) {
            if (t == null ) return
            when (t) {
                is NoteResult.Success<*> -> {
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
    override fun onCleared () {
        repositoryNotes.removeObserver(notesObserver)
    }
}