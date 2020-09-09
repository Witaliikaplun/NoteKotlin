package com.example.notekotlin.ui.main.note

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.notekotlin.data.Repository
import com.example.notekotlin.data.model.Note
import com.example.notekotlin.data.model.NoteResult
import com.example.notekotlin.ui.base.BaseViewModel
import com.example.notekotlin.data.model.NoteResult.Error

class NoteViewModel ( val repository: Repository = Repository) :
        BaseViewModel<Note?, NoteViewState>() {
    private var pendingNote: Note? = null
    fun saveChanges (note: Note ) {
        pendingNote = note
    }
    override fun onCleared () {
        if (pendingNote != null ) {
            repository.saveNote(pendingNote!!)
        }
    }
    fun loadNote (noteId: String ) {
        repository.getNoteById(noteId).observeForever( object :
                Observer<NoteResult> {
            override fun onChanged (t: NoteResult ?) {
                if (t == null ) return
                when (t) {
                    is NoteResult.Success<*> ->
                        viewStateLiveData.value = NoteViewState(note = t. data as? Note)
                    is Error ->
                        viewStateLiveData.value = NoteViewState(error = t.error)
                }
            }
        })
    }
}