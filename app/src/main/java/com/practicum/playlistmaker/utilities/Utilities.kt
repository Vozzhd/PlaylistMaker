package com.practicum.playlistmaker.utilities

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.player.domain.entity.Track

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun interface TrackClickListener {
    fun click(track: Track)
}

fun interface TrackLongClickListener {
    fun longClick(track: Track)
}

fun trackQuantityEndingFormat(size: Int, context: Context): String {
    return when (if (size in 11..14) size % 100 else size % 10) {
        1 -> context.getString(R.string.singleTrackEnding)
        2, 3, 4 -> context.getString(R.string.fromTwoToFourTrackEnding)
        else -> context.getString(R.string.severalTrackEnding)
    }
}

fun minutesQuantityEndingFormat(size: Int, context: Context): String {
    return when (if (size in 11..14) size % 100 else size % 10) {
        1 -> context.getString(R.string.singleMinuteEnding)
        2, 3, 4 -> context.getString(R.string.fromTwoToFourMinutesEnding)
        else -> context.getString(R.string.severalMinuteEnding)
    }
}

