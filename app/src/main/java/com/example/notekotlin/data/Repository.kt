package com.example.notekotlin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notekotlin.data.model.Note
import java.util.*

object Repository {
    private val notesLiveData = MutableLiveData<List<Note>>()
    private val notes: MutableList<Note> = mutableListOf(
            Note(id = UUID.randomUUID().toString(),
                    title = "Моя первая заметка",
                    note = "Kotlin очень краткий, но при этом выразительный язык",
                    color = Note.Color.WHITE),
            Note(id = UUID.randomUUID().toString(),
                    title = "Моя первая заметка",
                    note = "Kotlin очень краткий, но при этом выразительный язык",
                    color = Note.Color.BLUE),
            Note(id = UUID.randomUUID().toString(),
                    title = "Моя первая заметка",
                    note = "Kotlin очень краткий, но при этом выразительный язык",
                    color = Note.Color.RED),
            Note(id = UUID.randomUUID().toString(),
                    title = "Моя первая заметка",
                    note = "Kotlin очень краткий, но при этом выразительный язык",
                    color = Note.Color.GREEN),
            Note(id = UUID.randomUUID().toString(),
                    title = "Моя первая заметка",
                    note = "Kotlin очень краткий, но при этом выразительный язык",
                    color = Note.Color.VIOLET),
            Note(id = UUID.randomUUID().toString(),
                    title = "Моя первая заметка",
                    note = "Kotlin очень краткий, но при этом выразительный язык",
                    color = Note.Color.YELLOW),
            Note(id = UUID.randomUUID().toString(),
                    title = "Моя первая заметка",
                    note = "Kotlin очень краткий, но при этом выразительный язык",
                    color = Note.Color.PINK),
    )

    init {
        notesLiveData.value = notes
    }

    fun getNotes(): LiveData<List<Note>> {
        return notesLiveData
    }

//    fun getNotes(): List<Note> {
//        return notes
//    }

    fun saveNote(note: Note) {
        addOrReplace(note)
        notesLiveData.value = notes
    }

    private fun addOrReplace(note: Note) {
        for (i in 0 until notes.size) {
            if (notes[i] == note) {
                notes.set(i, note)
                return
            }
        }
        notes.add(note)
    }

}