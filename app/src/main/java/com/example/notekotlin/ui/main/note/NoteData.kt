package com.example.notekotlin.ui.main.note

import com.example.notekotlin.data.model.Note


data class NoteData(val isDeleted: Boolean = false, val note: Note? = null)