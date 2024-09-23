package com.practicum.playlistmaker.player.ui.presenters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.databinding.PlaylistCardBigBinding
import com.practicum.playlistmaker.mediaLibrary.playlists.ui.presenter.PlaylistAdapterPlayerFragment
import com.practicum.playlistmaker.playlistManage.createPlaylist.domain.entity.Playlist

class PlaylistsAdapterPlaylistFragment(private val clickListener: PlaylistAdapterPlayerFragment.PlaylistClickListener) :
    RecyclerView.Adapter<PlaylistsViewHolderBigCard>() {
    val listOfPlaylist: MutableList<Playlist> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistsViewHolderBigCard {
        return PlaylistsViewHolderBigCard(
            PlaylistCardBigBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listOfPlaylist.size
    }

    override fun onBindViewHolder(holder: PlaylistsViewHolderBigCard, position: Int) {
        val item = listOfPlaylist[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            clickListener.onTrackClick(item)
        }
    }
}