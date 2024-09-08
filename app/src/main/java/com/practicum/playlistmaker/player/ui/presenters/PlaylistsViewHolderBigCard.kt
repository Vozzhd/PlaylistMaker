package com.practicum.playlistmaker.player.ui.presenters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.PlaylistCardBigBinding
import com.practicum.playlistmaker.playlistCreating.domain.entity.Playlist
import com.practicum.playlistmaker.utilities.trackQuantityEndingFormat

class PlaylistsViewHolderBigCard(private val binding: PlaylistCardBigBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val corner by lazy { itemView.resources.getDimension(R.dimen.corner_radius).toInt() }


    fun bind(playlist: Playlist) {
        binding.playlistNameBigCard.text = playlist.name

        val trackQuantityFormattedText = "${playlist.trackQuantity} ${trackQuantityEndingFormat(playlist.trackQuantity, itemView.context)}"
        binding.playlistTracksQuantityBigCard.text = trackQuantityFormattedText



        Glide
            .with(itemView)
            .load(playlist.sourceOfPlaylistCoverImage)
            .fitCenter()
            .placeholder(R.drawable.placeholder_album_cover)
            .transform(RoundedCorners(corner))
            .into(binding.playlistImageBigCard)
    }

}