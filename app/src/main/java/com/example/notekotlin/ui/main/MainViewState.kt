package com.example.notekotlin.ui.main

import androidx.lifecycle.LiveData
import com.example.notekotlin.data.model.Note
import com.example.notekotlin.ui.base.BaseViewState

//data class MainViewState(val notes: List<Note>)

class MainViewState (notes: List<Note>? = null , error: Throwable? = null )
    : BaseViewState<List<Note>?>(notes, error)

