package com.practicum.playlistmaker.player.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.creator.Creator
import com.practicum.playlistmaker.databinding.ActivityPlayerBinding
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.player.domain.model.PlayerState
import com.practicum.playlistmaker.utilities.KEY_FOR_TRACK
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding
    private val viewModel by viewModel<PlayerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val trackMapper = Creator.provideTrackMapperInteractor()

        val bigRoundForCorner = resources.getDimension(R.dimen.corner_radius_for_big_cover).toInt()
        val track = (intent.getSerializableExtra(KEY_FOR_TRACK) as Track)

        viewModel.initPlayer(track)

        val trackPresentation = trackMapper.map(track)

        with(binding) {
            elapsedTrackTime.text = getString(R.string.defaultElapsedTrackTimeVisu)
            trackDuration.text = trackPresentation.trackDuration
            trackReleaseCountry.text = trackPresentation.country
            trackAlbumName.text = trackPresentation.collectionName
            trackReleaseYear.text = trackPresentation.releaseDate
            trackGenre.text = trackPresentation.primaryGenreName
            trackNameInPlayer.text = trackPresentation.trackName
            artistNameInPlayer.text = trackPresentation.artistName
        }

        binding.playButton.setOnClickListener {
            viewModel.playBackControl()
        }

        viewModel.getPlayerState().observe(this) {
            changeButtonImage(it)
            trackingElapsedTime(it)
        }
        viewModel.getCurrentTimeLiveData().observe(this) {
            binding.elapsedTrackTime.text = it.toString()
        }

        Glide.with(this)
            .load(trackPresentation.artworkUrl512)
            .transform(RoundedCorners(bigRoundForCorner))
            .placeholder(R.drawable.placeholder_album_cover)
            .into(binding.albumCoverImageInPlayer)


        binding.backButton.setOnClickListener {
            viewModel.removeUpdatingTimeCallbacks()
            finish()
        }
    }

    private fun trackingElapsedTime(playerState: PlayerState?) {
        if (playerState == PlayerState.PLAYING) viewModel.updatingElapsedTrackTime()
        if (playerState == PlayerState.PREPARED) binding.elapsedTrackTime.text = "00:00"
    }

    private fun changeButtonImage(playerState: PlayerState) {
        when (playerState) {
            PlayerState.DEFAULT -> {
                binding.playButton.setImageResource(R.drawable.play_button)
            }

            PlayerState.PREPARED -> {
                binding.playButton.setImageResource(R.drawable.play_button)
            }

            PlayerState.PLAYING -> binding.playButton.setImageResource(R.drawable.pause_button)
            PlayerState.PAUSED -> binding.playButton.setImageResource(R.drawable.play_button)
            PlayerState.COMPLETED -> binding.playButton.setImageResource(R.drawable.play_button)
        }
    }

    override fun onPause() {
        viewModel.removeUpdatingTimeCallbacks()
        viewModel.pause()
        super.onPause()
    }

    override fun onDestroy() {
        viewModel.release()
        super.onDestroy()
    }
}
