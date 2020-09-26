package com.example.notekotlin.ui.main.note

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings.System.DATE_FORMAT
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import com.example.notekotlin.R
import com.example.notekotlin.data.model.Note
import com.example.notekotlin.extensions.DATE_TIME_FORMAT
import com.example.notekotlin.extensions.SAVE_DELAY
import com.example.notekotlin.extensions.format
import com.example.notekotlin.extensions.getColorInt
import com.example.notekotlin.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_note.*
import kotlinx.android.synthetic.main.item_note.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : BaseActivity<NoteData>() {

    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + " extra . NOTE "
        fun start(context: Context, noteId: String?) =
                context.startActivity<NoteActivity>(EXTRA_NOTE to noteId)
    }

    private val textChangeListener = object : TextWatcher {
        // создание ананимного экземпляра класса
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

    override val model: NoteViewModel by viewModel()
    override val layoutRes: Int = R.layout.activity_note
    private var note: Note? = null
    private var color: Note.Color = Note.Color.WHITE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val noteId = intent.getStringExtra(EXTRA_NOTE)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        noteId?.let {
            model.loadNote(it)
        }
        if (noteId == null) supportActionBar?.title =
                getString(R.string.new_note_title)
        titleEt.addTextChangedListener(textChangeListener)
        bodyEt.addTextChangedListener(textChangeListener)

        colorPicker.onColorClickListener = {
            color = it
            toolbar.setBackgroundColor(note!!.color.getColorInt(this))
            triggerSaveNote()
        }
    }

    override fun renderData(data: NoteData) {
        if (data.isDeleted) finish()
        this.note = data.note
        data.note?.let { color = it.color }
        initView()
    }

    private fun initView() {
        note?.run {
            supportActionBar?.title = lastChanged.format()
            removeEditListener()
            titleEt.setText(title)
            bodyEt.setText(body.text)//??? в методичке просто body
            toolbar.setBackgroundColor(color.getColorInt(this@NoteActivity))
            setEditListener()
        }
    }

    private fun triggerSaveNote () {
        if (titleEt.text!!.length < 3 && bodyEt.text.length < 3 ) return
        launch {
            delay(SAVE_DELAY)
            note = note?.copy(title = titleEt.text.toString(),
                    note = bodyEt.text.toString(),
                    lastChanged = Date(),
                    color = color)
                    ?: createNewNote()
            note?.let { model.saveChanges(it) }
        }
    }

    private fun createNewNote(): Note = Note(UUID.randomUUID().toString(),
            titleEt.text.toString(),
            bodyEt.text.toString())

    override fun onCreateOptionsMenu(menu: Menu?): Boolean =
            menuInflater.inflate(R.menu.note_menu, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> super.onBackPressed().let { true }
        R.id.palette -> togglePalette().let { true }
        R.id.delete -> deleteNote().let { true }
        else -> super.onOptionsItemSelected(item)
    }

    private fun togglePalette() {
        if (colorPicker.isOpen) {
            colorPicker.close()
        } else {
            colorPicker.open()
        }
    }

    private fun deleteNote() {
        alert {
            messageResource = R.string.delete_dialog_message
            negativeButton(R.string.cancel_btn_title) { dialog -> dialog.dismiss() }
            positiveButton(R.string.ok_bth_title) { model.deleteNote() }
        }.show()
    }

    private fun setEditListener() {
        titleEt.addTextChangedListener(textChangeListener)
        bodyEt.addTextChangedListener(textChangeListener)
    }

    private fun removeEditListener() {
        titleEt.removeTextChangedListener(textChangeListener)
        bodyEt.removeTextChangedListener(textChangeListener)
    }

    private fun setToolbarColor(color: Color) {
        toolbar.setBackgroundColor(note!!.color.getColorInt(this))
    }

    override fun onBackPressed() {
        if (colorPicker.isOpen) {
            colorPicker.close()
            return
        }
        super.onBackPressed()
    }


}