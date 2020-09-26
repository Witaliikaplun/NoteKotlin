package com.example.notekotlin.ui.main.note

import androidx.lifecycle.Observer
import com.example.notekotlin.data.Repository
import com.example.notekotlin.data.model.Note
import com.example.notekotlin.data.model.Result
import com.example.notekotlin.ui.base.BaseViewModel
import com.example.notekotlin.data.model.Result.Error
import kotlinx.coroutines.launch

class NoteViewModel (val repository: Repository) :
        BaseViewModel<NoteData>() {
    private val currentNote: Note?
        get () = getViewState().poll()?.note
    fun saveChanges (note: Note ) {
        setData(NoteData(note = note))
    }
    fun loadNote (noteId: String ) {
        launch {
            try {
                repository.getNoteById(noteId).let {
                    setData(NoteData(note = it))
                }
            } catch (e: Throwable) {
                setError(e)
            }
        }
    }
    fun deleteNote () {
        launch {
            try {
                currentNote?.let { repository.deleteNote(it.id) }
                setData(NoteData(isDeleted = true ))
            } catch (e: Throwable) {
                setError(e)
            }
        }
    }
    override fun onCleared () {
        launch {
            currentNote?.let { repository.saveNote(it) }
            super .onCleared()
        }
    }
}