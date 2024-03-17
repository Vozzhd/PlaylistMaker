package com.practicum.playlistmaker.recyclerView

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.screens.SearchHistory

class TrackAdapter(
    private val trackList: MutableList<Track>,
    sharedPreferences: SharedPreferences,
    private val listener : (Track) -> Unit
) : RecyclerView.Adapter<TrackViewHolder>() {
    companion object {
    const val KEY_FOR_TRACK = "TrackInExtra"
    }

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

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(trackList[position])
        holder.itemView.setOnClickListener {
            searchHistory.addToHistoryList(trackList[position])
            listener(trackList[position])
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
