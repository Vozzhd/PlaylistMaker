package com.practicum.playlistmaker.screens

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.ActivityPlayerBinding
import com.practicum.playlistmaker.recyclerView.Track
import com.practicum.playlistmaker.recyclerView.TrackAdapter
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerActivity : AppCompatActivity() {
    private val mediaPlayer = MediaPlayer()
    private lateinit var binding: ActivityPlayerBinding
    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
    }
    private val dateFormat by lazy { SimpleDateFormat("mm:ss", Locale.getDefault()) }
    private var playerState = STATE_DEFAULT

    private val handler = Handler(Looper.getMainLooper())
    private val showCurrentTrackTime = Runnable {
        binding.elapsedTrackTime.text = dateFormat.format(mediaPlayer.currentPosition)
        showCurrentPosition()
    }
    private fun showCurrentPosition(){
        if (playerState == STATE_PLAYING) {
            handler.postDelayed(showCurrentTrackTime,500)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bigRoundForCorner = resources.getDimension(R.dimen.corner_radius_for_big_cover).toInt()
        val track = intent.getSerializableExtra(TrackAdapter.KEY_FOR_TRACK) as Track
        val url = track.previewUrl

        @SuppressLint("SetTextI18n")
        fun preparePlayer() {
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepareAsync()

            mediaPlayer.setOnPreparedListener {
                binding.playButton.isEnabled = true
                playerState = STATE_PREPARED
            }

            mediaPlayer.setOnCompletionListener {
                binding.playButton.isEnabled = true
                binding.playButton.setImageResource(R.drawable.play_button)
                playerState = STATE_PREPARED
                handler.removeCallbacks(showCurrentTrackTime)
                binding.elapsedTrackTime.text = getString(R.string.defaultElapsedTrackTimeVisu)
            }
        }

        preparePlayer()

        fun startPlayer() {
            handler.postDelayed(showCurrentTrackTime,300L)
            mediaPlayer.start()
            binding.playButton.setImageResource(R.drawable.pause_button)
            playerState = STATE_PLAYING
        }

        fun pausePlayer() {
            mediaPlayer.pause()
            binding.playButton.setImageResource(R.drawable.play_button)
            playerState = STATE_PAUSED
        }

        fun playbackControl() {
            when (playerState) {
                STATE_PLAYING -> {
                    pausePlayer()
                }
                STATE_PREPARED, STATE_PAUSED -> {
                    startPlayer()
                }
            }
        }

        binding.playButton.setOnClickListener { playbackControl() }
        binding.apply {
            elapsedTrackTime.text = getString(R.string.defaultElapsedTrackTimeVisu)
            trackReleaseCountry.text = track.country
            trackAlbumName.text = track.collectionName
            trackReleaseYear.text = track.releaseDate.substring(0, 4)
            trackGenre.text = track.primaryGenreName
            trackNameInPlayer.text = track.trackName
            artistNameInPlayer.text = track.artistName
        }

        Glide.with(this)
            .load(track.artworkUrl512)
            .transform(RoundedCorners(bigRoundForCorner))
            .placeholder(R.drawable.placeholder_album_cover).into(binding.albumCoverImageInPlayer)

        binding.backButton.setOnClickListener { finish() }

        if (track.trackTimeMillis.isNotBlank()) {
            binding.trackDuration.text =
                SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis.toInt())
        } else {
            binding.trackDuration.text = "--:--"
        }

    }
    override fun onPause() {
        super.onPause()
        fun pausePlayer() {
            handler.removeCallbacks(showCurrentTrackTime)
            mediaPlayer.pause()
            binding.playButton.setImageResource(R.drawable.play_button)
            playerState = STATE_PAUSED
        }
        pausePlayer()
    }
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(showCurrentTrackTime)
        mediaPlayer.release()
    }
}