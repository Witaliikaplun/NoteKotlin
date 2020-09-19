package com.example.notekotlin.ui.main.note

import com.example.notekotlin.data.model.Note
import com.example.notekotlin.ui.base.BaseViewState

class NoteViewState(data: Data = Data(),
                    error: Throwable? = null) :
        BaseViewState<NoteViewState.Data>(data, error) {
    data class Data(val isDeleted: Boolean = false, val note: Note? = null)
}