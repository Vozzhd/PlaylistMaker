package com.practicum.playlistmaker.player.ui.presenters

import android.provider.MediaStore.Audio.Playlists
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.databinding.PlaylistViewBinding
import com.practicum.playlistmaker.mediaLibrary.playlist.ui.presenter.PlaylistAdapter
import com.practicum.playlistmaker.playlistCreating.domain.entity.Playlist

class PlaylistRecyclerAdapter(
    private val list: List<Playlist>,
    private val clickListener: PlaylistAdapter.PlaylistClickListener
) :
    RecyclerView.Adapter<PlaylistsRecyclerViewHolder>() {

    fun interface TrackClickListener {
        fun onTrackClick(playlist: Playlist)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistsRecyclerViewHolder {
        return PlaylistsRecyclerViewHolder(PlaylistViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PlaylistsRecyclerViewHolder, position: Int) {
        val itemView = list[position]
        holder.bind(itemView)

        holder.itemView.setOnClickListener {
            clickListener.onTrackClick(itemView)
        }
    }
}