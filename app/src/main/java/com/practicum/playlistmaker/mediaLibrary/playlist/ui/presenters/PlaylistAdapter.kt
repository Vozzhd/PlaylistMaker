package com.practicum.playlistmaker.mediaLibrary.playlist.ui.presenters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.search.ui.presenters.TrackViewHolder
import com.practicum.playlistmaker.utilities.TrackClickListener
import com.practicum.playlistmaker.utilities.TrackLongClickListener

class PlaylistAdapter(
    private val trackClickListener: TrackClickListener,
    private val trackLongClickListener: TrackLongClickListener
) :
    RecyclerView.Adapter<TrackViewHolder>() {

    var trackList: MutableList<Track> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return trackList.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(trackList[position])
        holder.itemView.setOnClickListener {
            trackClickListener.click(trackList[position])
        }
        holder.itemView.setOnLongClickListener {
            trackLongClickListener.longClick(trackList[position])
            true
        }
    }
}


