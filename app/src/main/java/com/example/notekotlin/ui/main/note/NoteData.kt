package com.example.notekotlin.ui.main.note

import com.example.notekotlin.data.model.Note
import ru.geekbrains.gb_kotlin.data.entity.Note
import ru.geekbrains.gb_kotlin.ui.base.BaseViewState

data class NoteData(val isDeleted: Boolean = false, val note: Note? = null)