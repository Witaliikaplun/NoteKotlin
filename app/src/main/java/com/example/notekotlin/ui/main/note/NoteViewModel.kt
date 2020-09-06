package com.example.notekotlin.ui.main.note

import androidx.lifecycle.ViewModel
import com.example.notekotlin.data.Repository
import com.example.notekotlin.data.model.Note

class NoteViewModel (private val repository: Repository = Repository) :
        ViewModel() {
    private var pendingNote: Note? = null
    fun saveChanges (note: Note) {
        pendingNote = note
    }
    override fun onCleared (){
        if (pendingNote != null) {
            repository.saveNote(pendingNote!!)
        }
    }
}