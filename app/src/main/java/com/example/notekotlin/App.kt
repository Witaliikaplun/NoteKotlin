package com.example.notekotlin

import androidx.multidex.MultiDexApplication
import com.example.notekotlin.di.appModule
import com.example.notekotlin.di.mainModule
import com.example.notekotlin.di.noteModule
import com.example.notekotlin.di.splasModule
import org.koin.android.ext.android.startKoin

class App : MultiDexApplication() {
    override fun onCreate () {
        super .onCreate()
        startKoin( this , listOf(appModule, splasModule, mainModule, noteModule))
    }
}