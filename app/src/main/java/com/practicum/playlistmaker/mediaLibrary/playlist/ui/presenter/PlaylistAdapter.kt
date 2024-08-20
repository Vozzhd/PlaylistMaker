package com.practicum.playlistmaker.mediaLibrary.playlist.ui.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.databinding.PlaylistCardViewBinding
import com.practicum.playlistmaker.playlistCreating.domain.entity.Playlist

class PlaylistAdapter(
    private val listOfPlaylist: List<Playlist>,
    private val clickListener: PlaylistClickListener
) : RecyclerView.Adapter<PlaylistViewHolder>() {


    fun interface PlaylistClickListener {
        fun onTrackClick(playlist: Playlist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        return PlaylistViewHolder(
            PlaylistCardViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listOfPlaylist.size
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            clickListener.onTrackClick(listOfPlaylist[position])
        }
    }
}