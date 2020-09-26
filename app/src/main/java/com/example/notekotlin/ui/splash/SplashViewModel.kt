package com.example.notekotlin.ui.splash

import com.example.notekotlin.data.Repository
import com.example.notekotlin.data.errors.NoAuthException
import com.example.notekotlin.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class SplashViewModel ( private val repository: Repository) :
        BaseViewModel< Boolean >() {
    fun requestUser () {
        launch {
            repository.getCurrentUser()?.let {
                setData( true )
            } ?: setError(NoAuthException())
        }
    }
}