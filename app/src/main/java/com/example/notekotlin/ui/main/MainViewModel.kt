package com.example.notekotlin.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notekotlin.data.Repository

class MainViewModel : ViewModel() {
    private val viewStateLiveData: MutableLiveData<MainViewState> = MutableLiveData()

    init {
        Repository.getNotes().observeForever {
            viewStateLiveData.value =
                    viewStateLiveData.value?.copy(notes = it!!) ?: MainViewState(it!!)

        }
    }
        //viewStateLiveData.value = MainViewState(Repository.getNotes())




    fun viewState(): LiveData<MainViewState> = viewStateLiveData
}