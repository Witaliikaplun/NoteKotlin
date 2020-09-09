package com.example.notekotlin.ui.main.note

import com.example.notekotlin.data.model.Note
import com.example.notekotlin.ui.base.BaseViewState

class NoteViewState (note: Note? = null, error: Throwable? = null ) :
        BaseViewState<Note?>(note, error)