package com.practicum.playlistmaker.mediaLibrary.playlist.ui.presenter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.PlaylistCardViewBinding
import com.practicum.playlistmaker.playlistCreating.domain.entity.Playlist

class PlaylistViewHolder(private val binding: PlaylistCardViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val corner by lazy {
        itemView.resources.getDimension(R.dimen.album_corner_radius).toInt()
    }

    fun bind(playlist: Playlist) {
        binding.playlistCardName.text = playlist.name
        val trackFormatText = "${playlist.trackQuantity} ${
            trackQuantityFormatting(playlist.trackQuantity, itemView.context)}"

        binding.playlistTracksQuantity.text = trackFormatText

        Glide
            .with(itemView)
            .load(playlist.sourceOfPlaylistCoverImage)
            .fitCenter()
            .placeholder(R.drawable.placeholder_album_cover)
            .transform(RoundedCorners(corner))
            .into(binding.playlistCardImage)
    }


    private fun trackQuantityFormatting(tracksQuantity: Int, context: Context): String {
        return when (if (tracksQuantity in 11..14) tracksQuantity % 100 else tracksQuantity % 10) {
            1 -> context.getString(R.string.singleTrackEnding)
            2, 3, 4 -> context.getString(R.string.fromTwoToFourTrackEnding)
            else -> context.getString(R.string.severalTrackEnding)
        }
    }
}