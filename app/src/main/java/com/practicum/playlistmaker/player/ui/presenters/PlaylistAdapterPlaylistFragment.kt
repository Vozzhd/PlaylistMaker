package com.practicum.playlistmaker.player.ui.presenters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.databinding.PlaylistCardBigBinding
import com.practicum.playlistmaker.mediaLibrary.playlist.ui.presenter.PlaylistAdapterPlayerFragment
import com.practicum.playlistmaker.playlistCreating.domain.entity.Playlist

class PlaylistAdapterPlaylistFragment(private val clickListener: PlaylistAdapterPlayerFragment.PlaylistClickListener) :
    RecyclerView.Adapter<PlaylistsViewHolderSmallCard>() {
    val listOfPlaylist: MutableList<Playlist> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistsViewHolderSmallCard {
        return PlaylistsViewHolderSmallCard(
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

    override fun onBindViewHolder(holder: PlaylistsViewHolderSmallCard, position: Int) {
        val item = listOfPlaylist[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            clickListener.onTrackClick(item)
        }
    }
}