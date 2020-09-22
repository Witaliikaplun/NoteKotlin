package com.example.notekotlin.ui.splash

import com.example.notekotlin.data.Repository
import com.example.notekotlin.data.errors.NoAuthException
import com.example.notekotlin.ui.base.BaseViewModel

class SplashViewModel(private val repository: Repository) :
        BaseViewModel<Boolean?, SplashViewState>() {
    fun requestUser() {
        repository.getCurrentUser().observeForever {
            viewStateLiveData.value = if (it != null) {
                SplashViewState(isAuth = true)
            } else {
                SplashViewState(error = NoAuthException())
            }
        }
    }
}