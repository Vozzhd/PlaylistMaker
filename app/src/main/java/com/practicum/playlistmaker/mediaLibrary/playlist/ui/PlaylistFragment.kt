package com.practicum.playlistmaker.mediaLibrary.playlist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.FragmentPlaylistBinding
import com.practicum.playlistmaker.mediaLibrary.playlist.ui.viewModel.PlaylistViewModel
import com.practicum.playlistmaker.playlistCreating.domain.entity.Playlist
import com.practicum.playlistmaker.utilities.KEY_FOR_PLAYLIST
import com.practicum.playlistmaker.utilities.minutesQuantityEndingFormat
import com.practicum.playlistmaker.utilities.trackQuantityEndingFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Locale

class PlaylistFragment : Fragment() {

    private var _binding: FragmentPlaylistBinding? = null
    private val binding get() = _binding!!
    val dateFormat by lazy { SimpleDateFormat("m", Locale.getDefault()) }
    private val viewModel by viewModel<PlaylistViewModel>()

    companion object {
        fun createArgs(playlist: Playlist): Bundle {
            return bundleOf(
                KEY_FOR_PLAYLIST to playlist
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlaylistBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val playlist = requireArguments().get(KEY_FOR_PLAYLIST) as Playlist
        viewModel.initPlaylist(playlist)
        binding.playlistName.text = playlist.name
        binding.playlistDescription.text = playlist.description

        val trackQuantityFormattedText = "${playlist.trackQuantity} ${
            trackQuantityEndingFormat(
                playlist.trackQuantity,
                requireContext()
            )
        }"

        viewModel.observePlaylistDuration().observe(viewLifecycleOwner) { renderDuration(it) }


        binding.tracksQuantity.text = trackQuantityFormattedText


        Glide.with(this)
            .load(playlist.sourceOfPlaylistCoverImage)
            .placeholder(R.drawable.placeholder_playlist_cover)
            .into(binding.playlistCover)
    }

    private fun renderDuration(playlistDurationInMillis: Long) {
        val playlistDurationInMinutes = dateFormat.format(playlistDurationInMillis)

        binding.playlistDuration.text = "${playlistDurationInMinutes.toString()} ${
            minutesQuantityEndingFormat(
                playlistDurationInMinutes.toInt(),
                requireContext()
            )
        }"

    }

}