package com.practicum.playlistmaker.screens

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.gson.Gson
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.recyclerView.Track
import com.practicum.playlistmaker.recyclerView.TrackAdapter
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val backButton = findViewById<ImageButton>(R.id.backButton)
        val albumCoverImage = findViewById<ImageView>(R.id.albumCoverImageInPlayer)
        val trackNameInPlayer = findViewById<TextView>(R.id.trackNameInPlayer)
        val artistNameInPlayer = findViewById<TextView>(R.id.artistNameInPlayer)
        val playButton = findViewById<ImageButton>(R.id.playButton)
        val addButton = findViewById<ImageButton>(R.id.addButton)
        val likeButton = findViewById<ImageButton>(R.id.likeButton)
        val elapsedTrackTime = findViewById<TextView>(R.id.elapsedTrackTime)
        val trackAlbumName = findViewById<TextView>(R.id.trackAlbumName)
        val trackDuration = findViewById<TextView>(R.id.trackDuration)
        val trackReleaseYear = findViewById<TextView>(R.id.trackReleaseYear)
        val trackGenre = findViewById<TextView>(R.id.trackGenre)
        val trackReleaseCountry = findViewById<TextView>(R.id.releaseCountry)
        val corner = resources.getDimension(R.dimen.corner_radius_for_big_cover).toInt()
        backButton.setOnClickListener {
            finish()
        }

        val gson = Gson()
        val json = intent.getStringExtra(TrackAdapter.KEY_FOR_TRACK)
        val track = gson.fromJson(json, Track::class.java)

        Glide.with(this)
            .load(track.artworkUrl512)
            .transform(RoundedCorners(corner))
            .placeholder(R.drawable.placeholder_album_cover).into(albumCoverImage)


        trackNameInPlayer.text = track.trackName
        artistNameInPlayer.text = track.artistName

        if (track.trackTimeMillis.isNotBlank()) {
            trackDuration.text =
                SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis.toInt())
        } else {
            trackDuration.text = "-:--"
        }

        trackAlbumName.text = track.collectionName
        trackReleaseYear.text = track.releaseDate.substring(0, 4)
        trackGenre.text = track.primaryGenreName
        trackReleaseCountry.text = track.country
    }
}