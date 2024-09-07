package com.practicum.playlistmaker.player.ui.presenters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.PlaylistCardBigBinding
import com.practicum.playlistmaker.playlistCreating.domain.entity.Playlist

class PlaylistsViewHolderSmallCard(private val binding: PlaylistCardBigBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val corner by lazy { itemView.resources.getDimension(R.dimen.corner_radius).toInt() }


    fun bind(playlist: Playlist) {
        binding.playlistNameBigCard.text = playlist.name

        val trackQuantityFormat =
            "${playlist.trackQuantity} ${trackQuantityEndingFormat(playlist.trackQuantity, itemView.context)}"
        binding.playlistTracksQuantityBigCard.text = trackQuantityFormat

        Glide
            .with(itemView)
            .load(playlist.sourceOfPlaylistCoverImage)
            .fitCenter()
            .placeholder(R.drawable.placeholder_album_cover)
            .transform(RoundedCorners(corner))
            .into(binding.playlistImageBigCard)
    }

    private fun trackQuantityEndingFormat(size: Int, context: Context): String {

        return when (if (size in 11..14) size % 100 else size % 10) {
            1 -> context.getString(R.string.singleTrackEnding)
            2, 3, 4 -> context.getString(R.string.fromTwoToFourTrackEnding)
            else -> context.getString(R.string.severalTrackEnding)
        }
    }
}