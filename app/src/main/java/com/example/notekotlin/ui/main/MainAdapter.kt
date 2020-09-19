package com.example.notekotlin.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notekotlin.R
import com.example.notekotlin.data.model.Note
import com.example.notekotlin.data.model.Note.Color
import com.example.notekotlin.extensions.getColorInt
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_note.*

class MainAdapter(private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<MainAdapter.NoteViewHolder>() {
    var notes: List<Note> = listOf() 
        set(value) {
            field = value
            notifyDataSetChanged() //уведомляет список об изменении данных для обновления списка на экране
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount() = notes.size
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int): Unit {
        holder.bind(notes[position])
    }

    inner class NoteViewHolder ( override val containerView: View) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(note: Note) {
            title.text = note.title
            body.text = note.note

            itemView.setBackgroundColor(note.color.getColorInt(itemView.context))
            itemView.setOnClickListener { onItemClickListener.onItemClick(note) }
        }
    }
    interface OnItemClickListener{
        fun onItemClick(note: Note)
    }
}



