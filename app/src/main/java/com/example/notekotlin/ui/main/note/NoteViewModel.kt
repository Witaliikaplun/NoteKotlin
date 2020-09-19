package com.example.notekotlin.ui.main.note

import androidx.lifecycle.Observer
import com.example.notekotlin.data.Repository
import com.example.notekotlin.data.model.Note
import com.example.notekotlin.data.model.Result
import com.example.notekotlin.ui.base.BaseViewModel
import com.example.notekotlin.data.model.Result.Error

class NoteViewModel(val repository: Repository = Repository) :
        BaseViewModel<NoteViewState.Data, NoteViewState>() {


    fun saveChanges (note: Note ) {
        viewStateLiveData.value = NoteViewState(NoteViewState.Data(note = note))
    }
    override fun onCleared () {
        currentNote?.let { repository.saveNote(it) }
    }

    fun loadNote(noteId: String) {
        repository.getNoteById(noteId).observeForever { t ->
            t?.let {
                viewStateLiveData.value = when (t) {
                    is Result.Success<*> -> NoteViewState(NoteViewState.Data(note = t.data as? Note))
                    is Error -> NoteViewState(error = t.error)
                }
            }
        }
    }

    private val currentNote: Note?
        get() = viewStateLiveData.value?.data?.note

    fun deleteNote () {
        currentNote?.let {
            repository.deleteNote(it.id).observeForever { t ->
                t?.let {
                    viewStateLiveData.value = when (it) {
                        is Result.Success<*> -> NoteViewState(NoteViewState.Data(isDeleted = true))
                        is Error -> NoteViewState(error = it.error)
                    }
                }
            }
        }
    }
}