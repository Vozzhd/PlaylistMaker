package com.practicum.playlistmaker

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.recyclerView.TrackViewHolder

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}
fun RecyclerView.replaceAdapter (adapter:  RecyclerView.Adapter<TrackViewHolder>) {
    this.adapter = adapter
    adapter.notifyDataSetChanged()
}

