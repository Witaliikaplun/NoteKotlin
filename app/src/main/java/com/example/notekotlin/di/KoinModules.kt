package com.example.notekotlin.di

import com.example.notekotlin.data.Repository
import com.example.notekotlin.data.provider.FireStoreProvider
import com.example.notekotlin.data.provider.RemoteDataProvider
import com.example.notekotlin.ui.main.MainViewModel
import com.example.notekotlin.ui.main.note.NoteViewModel
import com.example.notekotlin.ui.splash.SplashViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.experimental.builder.viewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FireStoreProvider(get(), get()) } bind RemoteDataProvider::class
    single { Repository(get()) }
}
val splasModule = module {
    viewModel() { SplashViewModel(get()) }
}
val mainModule = module {
    viewModel() { MainViewModel(get()) }
}
val noteModule = module {
    viewModel() { NoteViewModel(get()) }
}