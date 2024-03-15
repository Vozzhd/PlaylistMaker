package com.practicum.playlistmaker.recyclerView

import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.screens.PlayerActivity
import com.practicum.playlistmaker.screens.SearchHistory

class TrackAdapter(
    val trackList: MutableList<Track>,
    sharedPreferences: SharedPreferences
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

            val context = holder.itemView.context
            val playerActivityIntent = Intent(context, PlayerActivity::class.java)
            val gson = Gson()
            val json = gson.toJson(trackList[position])
            context.startActivity(playerActivityIntent.putExtra(KEY_FOR_TRACK, json))
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
