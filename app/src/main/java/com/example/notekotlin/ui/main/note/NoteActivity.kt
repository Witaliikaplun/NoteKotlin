package com.example.notekotlin.ui.main.note

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings.System.DATE_FORMAT
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModelProviders
import com.example.notekotlin.R
import com.example.notekotlin.data.model.Note
import com.example.notekotlin.extensions.DATE_TIME_FORMAT
import com.example.notekotlin.extensions.SAVE_DELAY
import com.example.notekotlin.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_note.*
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : BaseActivity<Note?, NoteViewState>() {

    companion object {
        private val EXTRA_NOTE = NoteActivity:: class.java.name + "extra.NOTE"
        fun getStartIntent (context: Context, note: Note?): Intent {
            val intent = Intent(context, NoteActivity:: class.java)
            intent.putExtra(EXTRA_NOTE, note)
            return intent
        }
    }

    private val textChangeListener = object : TextWatcher{// создание ананимного экземпляра класса
                                                          // реализующий интерфейс TextWather.
                                                          //созданный таким образом объект не является синглтоном
        override fun afterTextChanged(p0: Editable?) {
            triggerSaveNote()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }

    override val viewModel: NoteViewModel by lazy {
        ViewModelProviders.of( this ). get (NoteViewModel:: class . java ) }
    override val layoutRes: Int = R.layout.activity_note
    private var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super .onCreate(savedInstanceState)
        val noteId = intent.getStringExtra(EXTRA_NOTE)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled( true )
        noteId?.let {
            viewModel.loadNote(it)
        }
        if (noteId == null ) supportActionBar?.title =
                getString(R.string.new_note_title)
        titleEt.addTextChangedListener(textChangeListener)
        bodyEt.addTextChangedListener(textChangeListener)
    }
    override fun renderData ( data : Note ?) {
        this .note = data
        initView()
    }
    private fun initView () {
        if (note != null) {
            titleEt.setText(note?.title ?: "")
            bodyEt.setText(note?.note ?: "")

            val color = when (note!!.color) {
                Note.Color.WHITE -> R.color.color_white
                Note.Color.VIOLET -> R.color.color_violet
                Note.Color.YELLOW -> R.color.color_yello
                Note.Color.RED -> R.color.color_red
                Note.Color.PINK -> R.color.color_pink
                Note.Color.GREEN -> R.color.color_green
                Note.Color.BLUE -> R.color.color_blue
            }
            toolbar.setBackgroundColor(resources.getColor(color))
        }
        titleEt.addTextChangedListener(textChangeListener)
        bodyEt.addTextChangedListener(textChangeListener)
    }

    private fun triggerSaveNote () {
        if (titleEt.text!!.length < 3) return
        Handler().postDelayed( object : Runnable {
            override fun run () {
                note = note?.copy(title = titleEt.text.toString(),
                        note = bodyEt.text.toString(),
                        lastChanged = Date())
                        ?: createNewNote()
                if (note != null) viewModel.saveChanges(note!!)
            }
        }, SAVE_DELAY)
    }

    private fun createNewNote(): Note = Note(UUID.randomUUID().toString(),
            titleEt.text.toString(),
            bodyEt.text.toString())
}