package com.practicum.playlistmaker.player.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.creator.Creator
import com.practicum.playlistmaker.databinding.ActivityPlayerBinding
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.player.domain.model.PlayerState
import com.practicum.playlistmaker.player.data.TrackMapper
import com.practicum.playlistmaker.search.ui.presenters.TrackAdapter
import com.practicum.playlistmaker.search.ui.presenters.TrackViewHolder
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding
    private val dateFormat by lazy { SimpleDateFormat("mm:ss", Locale.getDefault()) }
    private val player = Creator.provideMediaPlayer()
    private val handler = Handler(Looper.getMainLooper())
    private val showCurrentTrackTime = showCurrentPosition()
    private lateinit var viewModel: PlayerViewModel

    companion object {
        private const val REFRESH_TRACK_TIME_DELAY = 300L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bigRoundForCorner = resources.getDimension(R.dimen.corner_radius_for_big_cover).toInt()

        val trackPresentation = TrackMapper.map(intent.getSerializableExtra(TrackAdapter.KEY_FOR_TRACK) as Track)
        val url = trackPresentation.previewUrl
        val track = (intent.getSerializableExtra(TrackAdapter.KEY_FOR_TRACK) as Track)


        viewModel = ViewModelProvider(
            this,
            PlayerViewModel.getViewModelFactory(
                url,
                trackPresentation,
                Creator.provideMediaPlayer()
            )
        )[PlayerViewModel::class.java]
        player.preparePlayer(url)

        with(binding) {
            elapsedTrackTime.text = getString(R.string.defaultElapsedTrackTimeVisu)
            trackReleaseCountry.text = trackPresentation.country
            trackAlbumName.text = trackPresentation.collectionName
            trackReleaseYear.text = trackPresentation.releaseDate
            trackGenre.text = trackPresentation.primaryGenreName
            trackNameInPlayer.text = trackPresentation.trackName
            artistNameInPlayer.text = trackPresentation.artistName
        }

        binding.playButton.setOnClickListener {
            //   player.playbackControl()
            //   playButtonState()
            viewModel.playBackControl()
        }

        viewModel.getPlayerState().observe(this) {
            when (it) {
                PlayerState.DEFAULT -> {}
                PlayerState.PREPARED -> {}
                PlayerState.PLAYING -> {
                    binding.playButton.setImageResource(R.drawable.pause_button)
                    handler.post(showCurrentTrackTime)
                }
                PlayerState.PAUSED -> binding.playButton.setImageResource(R.drawable.play_button)
            }

            Glide.with(this)
                .load(trackPresentation.artworkUrl512)
                .transform(RoundedCorners(bigRoundForCorner))
                .placeholder(R.drawable.placeholder_album_cover)
                .into(binding.albumCoverImageInPlayer)

            binding.backButton.setOnClickListener {
                handler.removeCallbacks(showCurrentTrackTime)
                finish()
            }
        }
    }
        private fun showCurrentPosition(): Runnable {
            return Runnable {
                binding.elapsedTrackTime.text = dateFormat.format(player.showCurrentPosition())
                handler.postDelayed(showCurrentTrackTime, REFRESH_TRACK_TIME_DELAY)
            }
        }

        override fun onPause() {
            handler.removeCallbacks(showCurrentTrackTime)
            player.pause()
            super.onPause()
        }

        override fun onDestroy() {
            player.release()
            super.onDestroy()
        }
    }
