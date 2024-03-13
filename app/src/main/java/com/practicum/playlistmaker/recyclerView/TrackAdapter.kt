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
    var trackList: MutableList<Track>,
    sharedPreferences: SharedPreferences
) : RecyclerView.Adapter<TrackViewHolder>() {

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
            searchHistory.addToHistoryList (trackList[position])

            val context = holder.itemView.context
            val playerActivityIntent = Intent(context,PlayerActivity::class.java)
            val gson = Gson()
            val json = gson.toJson(trackList[position])
            context.startActivity(playerActivityIntent.putExtra("key", json))
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
}
