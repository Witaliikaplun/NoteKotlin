package com.example.notekotlin.ui.splash

import com.example.notekotlin.ui.base.BaseViewState

class SplashViewState(isAuth: Boolean? = null, error: Throwable? = null) :
        BaseViewState<Boolean?>(isAuth, error)