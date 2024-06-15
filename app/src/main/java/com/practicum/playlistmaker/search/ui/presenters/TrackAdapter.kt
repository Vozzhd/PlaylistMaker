package com.practicum.playlistmaker.search.ui.presenters

import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.player.domain.entity.Track

class TrackAdapter(private val clickListener: ClickListener) : RecyclerView.Adapter<TrackViewHolder>() {
    companion object {
        const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    var trackList: MutableList<Track> = mutableListOf()
    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder(parent)
    }
    override fun getItemCount(): Int {
        return trackList.size
    }
    private fun clickDebounce(): Boolean {
        val currentState = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return currentState
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(trackList[position])
        holder.itemView.setOnClickListener {
            if (clickDebounce()) {
                clickListener.click(trackList[position])
            }
        }

    }
    fun interface ClickListener {
        fun click(track: Track)
    }
}
