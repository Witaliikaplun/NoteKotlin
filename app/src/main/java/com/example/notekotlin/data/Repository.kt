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
//    private val notes: MutableList<Note> = mutableListOf(
//            Note(id = UUID.randomUUID().toString(),
//                    title = "Моя первая заметка",
//                    note = "Kotlin очень краткий, но при этом выразительный язык",
//                    color = Note.Color.WHITE),
//            Note(id = UUID.randomUUID().toString(),
//                    title = "Моя первая заметка",
//                    note = "Kotlin очень краткий, но при этом выразительный язык",
//                    color = Note.Color.BLUE),
//            Note(id = UUID.randomUUID().toString(),
//                    title = "Моя первая заметка",
//                    note = "Kotlin очень краткий, но при этом выразительный язык",
//                    color = Note.Color.RED),
//            Note(id = UUID.randomUUID().toString(),
//                    title = "Моя первая заметка",
//                    note = "Kotlin очень краткий, но при этом выразительный язык",
//                    color = Note.Color.GREEN),
//            Note(id = UUID.randomUUID().toString(),
//                    title = "Моя первая заметка",
//                    note = "Kotlin очень краткий, но при этом выразительный язык",
//                    color = Note.Color.VIOLET),
//            Note(id = UUID.randomUUID().toString(),
//                    title = "Моя первая заметка",
//                    note = "Kotlin очень краткий, но при этом выразительный язык",
//                    color = Note.Color.YELLOW),
//            Note(id = UUID.randomUUID().toString(),
//                    title = "Моя первая заметка",
//                    note = "Kotlin очень краткий, но при этом выразительный язык",
//                    color = Note.Color.PINK),
//    )

//    init {
//        notesLiveData.value = notes
//    }

    fun getNotes() = remoteProvider.subscribeToAllNotes()


    fun saveNote(note: Note) = remoteProvider.saveNote(note)

    fun getNoteById(id: String) = remoteProvider.getNoteById(id)

    fun getCurrentUser () = remoteProvider.getCurrentUser()

    fun deleteNote (noteId: String ) = remoteProvider.deleteNote(noteId)

}