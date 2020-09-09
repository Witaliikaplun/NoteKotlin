package com.example.notekotlin.data.provider

import androidx.lifecycle.LiveData
import com.example.notekotlin.data.model.Note
import com.example.notekotlin.data.model.NoteResult

interface RemoteDataProvider {
    fun subscribeToAllNotes() : LiveData<NoteResult>
    fun getNoteById(id: String) : LiveData<NoteResult>
    fun saveNote(note: Note) : LiveData<NoteResult>
}