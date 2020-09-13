package com.example.notekotlin.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.example.notekotlin.R
import com.example.notekotlin.data.model.Note
import com.example.notekotlin.ui.base.BaseActivity
import com.example.notekotlin.ui.main.note.NoteActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseActivity<List<Note>?, MainViewState>() {
    override val viewModel: MainViewModel by lazy {
        ViewModelProviders.of( this ). get (MainViewModel:: class.java ) }
    override val layoutRes: Int = R.layout.activity_main
    private lateinit var adapter: MainAdapter

    override fun onCreate (savedInstanceState: Bundle ?) {
        super .onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        adapter = MainAdapter( object : MainAdapter.OnItemClickListener {
            override fun onItemClick (note: Note ) {
                openNoteScreen(note)
            }
        })
        mainRecycler.adapter = adapter
        fab.setOnClickListener( object : View.OnClickListener {
            override fun onClick (v: View?) {
                openNoteScreen( null )
            }
        })
    }
    override fun renderData ( data : List < Note >?) {
        if ( data == null ) return
        adapter.notes = data
    }
    private fun openNoteScreen (note: Note?) {
        val intent = NoteActivity.getStartIntent( this, note?.id)
        startActivity(intent)
    }
    companion object {
        fun getStartIntent(context: Context) = Intent(context,
                MainActivity:: class . java )
    }
}