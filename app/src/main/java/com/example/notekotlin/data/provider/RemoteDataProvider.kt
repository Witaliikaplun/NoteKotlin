package com.example.notekotlin.data.provider

import androidx.lifecycle.LiveData
import com.example.notekotlin.data.model.Note
import com.example.notekotlin.data.model.Result
import com.example.notekotlin.data.model.User

interface RemoteDataProvider {
    fun subscribeToAllNotes(): LiveData<Result>
    fun getNoteById(id: String): LiveData<Result>
    fun saveNote(note: Note): LiveData<Result>
    fun getCurrentUser(): LiveData<User?>
    fun deleteNote (noteId: String ): LiveData<Result>
}