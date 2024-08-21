package com.practicum.playlistmaker.player.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.ActivityPlayerBinding
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.player.domain.model.PlayerState
import com.practicum.playlistmaker.playlistCreating.domain.entity.Playlist
import com.practicum.playlistmaker.utilities.KEY_FOR_TRACK
import com.practicum.playlistmaker.utilities.debounce
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Locale


class PlayerFragment() : Fragment() {

    private lateinit var binding: ActivityPlayerBinding
    private val viewModel by viewModel<PlayerViewModel>()
    private val playlists = mutableListOf<Playlist>()
    private lateinit var bottomSheet: BottomSheetBehavior<LinearLayout>
    private lateinit var likeClickDebounce: (Boolean) -> Unit
    companion object {
        const val CLICK_DEBOUNCE_DELAY = 2000L

        fun createArgs(track: Track): Bundle {
            return bundleOf(
                KEY_FOR_TRACK to track
            )
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val track = requireArguments().get(KEY_FOR_TRACK) as Track
        val bigRoundForCorner = resources.getDimension(R.dimen.corner_radius_for_big_cover).toInt()
        viewModel.initPlayer(track)
        val dateFormat by lazy { SimpleDateFormat("mm:ss", Locale.getDefault()) }

        bottomSheet = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN

        binding.shadow.visibility = View.GONE

        with(binding) {
            elapsedTrackTime.text = getString(R.string.defaultElapsedTrackTimeVisu)
            trackDuration.text = dateFormat.format(track.trackTimeMillis.toInt())
            trackReleaseCountry.text = track.country
            trackAlbumName.text = track.collectionName
            trackReleaseYear.text = track.releaseDate.substring(0, 4)
            trackGenre.text = track.primaryGenreName
            trackNameInPlayer.text = track.trackName
            artistNameInPlayer.text = track.artistName
        }

        binding.playButton.setOnClickListener {
            viewModel.playBackControl()
        }

        binding.likeButton.setOnClickListener {
            viewModel.favoriteButtonClicked(track)
        }

        binding.addButton.setOnClickListener {
            bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        binding.createPlayList.setOnClickListener {

        }
        bottomSheet.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {

                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        viewModel.updateListOfPlaylists()
                    }

                    BottomSheetBehavior.STATE_HIDDEN -> {
                        binding.shadow.visibility = View.GONE
                    }

                    else -> {
                        binding.shadow.visibility = View.VISIBLE
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                //Do nothing
            }
        })

        viewModel.observePlayerState().observe(viewLifecycleOwner) {
            changeButtonImage(it)
        }
        viewModel.observeIsFavorite().observe(viewLifecycleOwner) {
            changeLikeImage(it)
        }

        viewModel.getCurrentTimeLiveData().observe(viewLifecycleOwner) {
            binding.elapsedTrackTime.text = it.toString()
        }

        viewModel.observeListWithPlaylists().observe(viewLifecycleOwner) {
            updatePlaylistsRecyclerView(it)
        }

        Glide.with(this)
            .load(track.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg"))
            .transform(RoundedCorners(bigRoundForCorner))
            .placeholder(R.drawable.placeholder_album_cover)
            .into(binding.albumCoverImageInPlayer)

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }


    }

    private fun changeLikeImage(it: Boolean) {
        when (it) {
            true -> binding.likeButton.setImageResource((R.drawable.liked_button))
            false -> binding.likeButton.setImageResource((R.drawable.unliked_button))
        }
    }

    private fun changeButtonImage(playerState: PlayerState) {
        when (playerState) {
            PlayerState.DEFAULT -> {
                binding.playButton.setImageResource(R.drawable.play_button)
                binding.elapsedTrackTime.text = getString(R.string.defaultElapsedTrackTimeVisu)
            }

            PlayerState.PREPARED -> {
                binding.playButton.setImageResource(R.drawable.play_button)

            }

            PlayerState.PLAYING -> {
                viewModel.getCurrentTime()
                binding.playButton.setImageResource(R.drawable.pause_button)
            }

            PlayerState.PAUSED -> binding.playButton.setImageResource(R.drawable.play_button)
            PlayerState.COMPLETED -> {
                binding.playButton.setImageResource(R.drawable.play_button)
                binding.elapsedTrackTime.text = getString(R.string.defaultElapsedTrackTimeVisu)
            }
        }
    }

    private fun updatePlaylistsRecyclerView(newList: List<Playlist>) {
        playlists.clear()
        playlists.addAll(newList)
        binding.playlistsRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onPause() {
        viewModel.pause()
        super.onPause()
    }

    override fun onDestroy() {
        viewModel.release()
        super.onDestroy()
    }
}
