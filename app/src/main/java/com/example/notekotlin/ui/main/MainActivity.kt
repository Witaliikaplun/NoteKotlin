package com.example.notekotlin.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.notekotlin.R
import com.example.notekotlin.data.model.Note
import com.example.notekotlin.ui.main.note.NoteActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)//Не забудьте поменять в Стилях приложения
                //тему на Resources.Theme.AppCompat.Light.NoActionBar
        viewModel = ViewModelProviders.of(this).get(MainViewModel:: class.java)
        adapter = MainAdapter(object : MainAdapter.OnItemClickListener{
            override fun onItemClick(note: Note){
                openNoteScreen(note)
            }
        })
        mainRecycler.adapter = adapter


        viewModel.viewState().observe(this, Observer<MainViewState> {t ->
            t?.let {adapter.notes = it.notes}
        })

        fab.setOnClickListener { openNoteScreen(null) }
}
    private fun openNoteScreen(note: Note?){
        val intent = NoteActivity.getStartIntent(this, note)
        startActivity(intent)
    }
}