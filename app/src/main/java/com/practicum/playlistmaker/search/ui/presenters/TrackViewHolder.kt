package com.practicum.playlistmaker.search.ui.presenters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.player.domain.entity.Track
import java.text.SimpleDateFormat
import java.util.Locale

class TrackViewHolder(
    parent: ViewGroup,
) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.track_view, parent, false)
    )  {
    private val trackName: TextView = itemView.findViewById(R.id.trackName)
    private val artistName: TextView = itemView.findViewById(R.id.artistName)
    private val trackTime: TextView = itemView.findViewById(R.id.trackDuration)
    private val albumCover: ImageView = itemView.findViewById(R.id.albumCoverImage)
    private val corner = itemView.resources.getDimension(R.dimen.corner_radius).toInt()

    fun bind(model: Track) {

        trackName.text = model.trackName
        artistName.text = model.artistName
        if (model.trackTimeMillis.isNotBlank()) {
            trackTime.text =
                SimpleDateFormat("mm:ss", Locale.getDefault()).format(model.trackTimeMillis.toInt())
        } else {
            trackTime.text = "--:--"
        }

        Glide
            .with(itemView)
            .load(model.artworkUrl100)
            .fitCenter()
            .placeholder(R.drawable.placeholder_album_cover)
            .transform(RoundedCorners(corner))
            .into(albumCover)
    }
}