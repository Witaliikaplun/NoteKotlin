package com.example.notekotlin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notekotlin.data.model.Note
import com.example.notekotlin.data.provider.FireStoreProvider
import com.example.notekotlin.data.provider.RemoteDataProvider
import java.util.*

class Repository ( private val remoteProvider: RemoteDataProvider){
    //private val remoteProvider: RemoteDataProvider = FireStoreProvider()
    private val notesLiveData = MutableLiveData<List<Note>>()


    fun getNotes() = remoteProvider.subscribeToAllNotes()


    fun saveNote(note: Note) = remoteProvider.saveNote(note)

    fun getNoteById(id: String) = remoteProvider.getNoteById(id)

    fun getCurrentUser () = remoteProvider.getCurrentUser()

    fun deleteNote (noteId: String ) = remoteProvider.deleteNote(noteId)

}