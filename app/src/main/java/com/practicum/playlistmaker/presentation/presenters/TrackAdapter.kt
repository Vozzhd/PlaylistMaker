package com.practicum.playlistmaker.presentation.presenters

import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.domain.entity.Track
import com.practicum.playlistmaker._nosort.SearchHistory

class TrackAdapter(
    private val trackList: MutableList<Track>,
    sharedPreferences: SharedPreferences,
    private val listener : (Track) -> Unit
) : RecyclerView.Adapter<TrackViewHolder>() {
    companion object {
    const val KEY_FOR_TRACK = "TrackInExtra"
    const val CLICK_DEBOUNCE_DELAY = 1000L
    }
    private val handler = Handler(Looper.getMainLooper())
    private val searchHistory = SearchHistory(sharedPreferences)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.track_view, parent, false)
        return TrackViewHolder(view)
    }

    override fun getItemCount(): Int {
        return trackList.size
    }

    private var isClickAllowed = true
    private fun clickDebounce() : Boolean {
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
                searchHistory.addToHistoryList(trackList[position])
                listener(trackList[position])
            }
        }
    }
    fun clear() {
        trackList.clear()
        notifyDataSetChanged()
    }

    fun addAllData(mutableList: MutableList<Track>) {
        trackList.addAll(mutableList)
        notifyDataSetChanged()
    }

    fun replaceList(list: List<Track>) {
        trackList.clear()
        trackList.addAll(list)
        this.notifyDataSetChanged()
    }
}