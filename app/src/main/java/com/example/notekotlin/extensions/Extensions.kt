package com.example.notekotlin.extensions

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.notekotlin.R
import com.example.notekotlin.data.model.Note
import java.text.SimpleDateFormat
import java.util.*

const val DATE_TIME_FORMAT = "dd.MMM.yy HH:mm"
const val SAVE_DELAY = 2000L


fun Date.format(): String = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault())
        .format(this)

fun Note.Color.getColorInt(context: Context) =
        ContextCompat.getColor(context, when (this) {
            Note.Color.WHITE -> R.color.color_white
            Note.Color.VIOLET -> R.color.color_violet
            Note.Color.YELLOW -> R.color.color_yello
            Note.Color.RED -> R.color.color_red
            Note.Color.PINK -> R.color.color_pink
            Note.Color.GREEN -> R.color.color_green
            Note.Color.BLUE -> R.color.color_blue
        })