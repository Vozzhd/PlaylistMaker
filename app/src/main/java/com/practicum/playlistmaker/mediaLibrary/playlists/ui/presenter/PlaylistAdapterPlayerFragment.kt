package com.practicum.playlistmaker.mediaLibrary.playlists.ui.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.databinding.PlaylistCardSmallBinding
import com.practicum.playlistmaker.playlistManage.domain.entity.Playlist

class PlaylistAdapterPlayerFragment(
    private val clickListener: PlaylistClickListener
) : RecyclerView.Adapter<PlaylistViewHolderBigCard>() {

    val listOfPlaylist: MutableList<Playlist> = mutableListOf()

    fun interface PlaylistClickListener {
        fun onTrackClick(playlist: Playlist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolderBigCard {
        return PlaylistViewHolderBigCard(
            PlaylistCardSmallBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listOfPlaylist.size
    }

    override fun onBindViewHolder(holder: PlaylistViewHolderBigCard, position: Int) {
        val item = listOfPlaylist[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            clickListener.onTrackClick(item)
        }
    }
}