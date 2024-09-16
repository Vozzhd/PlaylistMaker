package com.practicum.playlistmaker.mediaLibrary.playlist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.FragmentPlaylistBinding
import com.practicum.playlistmaker.mediaLibrary.playlist.ui.presenters.PlaylistAdapter
import com.practicum.playlistmaker.mediaLibrary.playlist.ui.viewModel.PlaylistViewModel
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.player.ui.PlayerFragment
import com.practicum.playlistmaker.playlistManage.createPlaylist.domain.entity.Playlist
import com.practicum.playlistmaker.playlistManage.createPlaylist.ui.EditPlaylistFragment
import com.practicum.playlistmaker.search.ui.SearchFragment.Companion.CLICK_DEBOUNCE_DELAY
import com.practicum.playlistmaker.utilities.KEY_FOR_PLAYLIST
import com.practicum.playlistmaker.utilities.Result
import com.practicum.playlistmaker.utilities.debounce
import com.practicum.playlistmaker.utilities.minutesQuantityEndingFormat
import com.practicum.playlistmaker.utilities.trackQuantityEndingFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Locale

class PlaylistFragment : Fragment() {

    private var _binding: FragmentPlaylistBinding? = null
    private val binding get() = _binding!!
    val dateFormatForPlaylist by lazy { SimpleDateFormat("m", Locale.getDefault()) }
    private val dateFormatForTrack by lazy { SimpleDateFormat("mm:ss", Locale.getDefault()) }
    private val viewModel by viewModel<PlaylistViewModel>()
    private var onTrackClickDebounce: ((Track) -> Unit)? = null
    private var onLongTrackClickDebounce: ((Track) -> Unit)? = null
    private var playlistAdapter: PlaylistAdapter? = null
    private lateinit var playlist: Playlist
    private lateinit var extendedBottomSheet: BottomSheetBehavior<LinearLayout>

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

        playlist = requireArguments().get(KEY_FOR_PLAYLIST) as Playlist

        viewModel.updatePlaylistInformation(playlist)

        onTrackClickDebounce =
            debounce(CLICK_DEBOUNCE_DELAY, viewLifecycleOwner.lifecycleScope, false) { track ->
                viewModel.onTrackClick(track)
            }

        onLongTrackClickDebounce =
            debounce(CLICK_DEBOUNCE_DELAY, viewLifecycleOwner.lifecycleScope, false) { track ->
                viewModel.onLongTrackClick(track)
            }

        extendedBottomSheet = BottomSheetBehavior.from(binding.extendedMenuBottomSheet)
        extendedBottomSheet.state = BottomSheetBehavior.STATE_HIDDEN

        viewModel.updatePlaylistInformation(playlist)

        binding.playlistName.text = playlist.name
        binding.playlistDescription.text = playlist.description

        val trackQuantityFormattedText = "${playlist.trackQuantity} ${
            trackQuantityEndingFormat(
                playlist.trackQuantity,
                requireContext()
            )
        }"

        viewModel.observeClickEvent().observe(viewLifecycleOwner) { openPlayerFragment(it) }
        viewModel.observeLongClickEvent().observe(viewLifecycleOwner) { openDeleteDialog(it) }
        viewModel.observePlaylistDuration().observe(viewLifecycleOwner) { renderDuration(it) }
        viewModel.observePlaylistTracks().observe(viewLifecycleOwner) { fillTracks(it) }
        viewModel.observePlaylistQuantity().observe(viewLifecycleOwner) { renderQuantity(it) }
        viewModel.observeShareStatus().observe(viewLifecycleOwner) { shareAction(it) }
        viewModel.observePlaylist().observe(viewLifecycleOwner) { updatePlaylistInformation(it) }
        viewModel.observeTrackListForShare().observe(viewLifecycleOwner) { share(it) }

        playlistAdapter = PlaylistAdapter(onTrackClickDebounce!!, onLongTrackClickDebounce!!)

        binding.tracksInPlaylistRecyclerView.layoutManager =
            LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL, false)

        binding.tracksInPlaylistRecyclerView.adapter = playlistAdapter

        binding.tracksQuantity.text = trackQuantityFormattedText

        binding.shareButton.setOnClickListener {
            viewModel.shareRequest(playlist)
        }
        binding.sharePlaylistFrameButton.setOnClickListener {
            viewModel.shareRequest(playlist)
        }

        binding.extendedMenu.setOnClickListener {
            extendedBottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        binding.editPlaylistFrameButton.setOnClickListener {

            findNavController().navigate(
                R.id.action_playlistFragment_to_editPlaylist,
                EditPlaylistFragment.createArgs(playlist)
            )
        }
        binding.deletePlaylistFrameButton.setOnClickListener {
            viewModel.deletePlaylist(playlist)
            findNavController().popBackStack()

        }

        Glide.with(this)
            .load(playlist.sourceOfPlaylistCoverImage)
            .placeholder(R.drawable.placeholder_playlist_cover)
            .into(binding.playlistCover)
    }

    private fun share(it: List<Track>) {

        var formattedTextList = ""
        var trackNumber = 0L
        var trackDuration = ""
        formattedTextList =
            "${playlist.name}\n${playlist.description}\n${playlist.trackQuantity} ${trackQuantityEndingFormat(playlist.trackQuantity, requireContext())}\n"

        it.forEach { track ->
            ++trackNumber
            trackDuration = dateFormatForTrack.format(track.trackTimeMillis.toInt())
            formattedTextList += "${trackNumber}. ${track.artistName} - ${track.trackName} (${trackDuration})\n"
        }
        viewModel.shareTrackList(formattedTextList)
    }


    private fun shareAction(result: Result) {
        when (result.result) {
            true -> sharePlaylist(playlist)
            false -> showToastEmpty()
        }
    }

    private fun sharePlaylist(playlist: Playlist) {
        viewModel.getTrackList(playlist.playlistId)
    }

    private fun showToastEmpty() {
        Toast.makeText(
            requireContext(),
            requireContext().getString(R.string.sharePlaylistIsEmpty),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun updatePlaylistInformation(playlistFromTable: Playlist) {
        playlist = playlistFromTable
        binding.playlistName.text = playlist.name
        binding.playlistDescription.text = playlist.description
        binding.albumCoverImage

        Glide.with(this)
            .load(playlistFromTable.sourceOfPlaylistCoverImage)
            .placeholder(R.drawable.placeholder_playlist_cover)
            .into(binding.playlistCover)
    }

    private fun renderQuantity(it: Int) {
        playlist.trackQuantity = it
        val trackQuantityFormattedText = "${it.toString()} ${
            trackQuantityEndingFormat(
                it,
                requireContext()
            )
        }"
        binding.tracksQuantity.text = trackQuantityFormattedText
    }

    private fun openDeleteDialog(it: Track) {
        MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertText)
            .setTitle(requireContext().getString(R.string.deletingTrackFromPlaylist))
            .setNegativeButton(requireContext().getString(R.string.no)) { _, _ -> }
            .setPositiveButton(requireContext().getString(R.string.yes)) { _, _ ->
                run {
                    viewModel.deleteTrackFromPlaylist(it, playlist)
                    viewModel.updatePlaylistInformation(playlist)
                }
            }
            .show()
        playlistAdapter?.notifyDataSetChanged()
    }

    private fun fillTracks(trackList: List<Track>) {
        playlistAdapter?.trackList?.clear()
        playlistAdapter!!.trackList.addAll(trackList)
        playlistAdapter!!.notifyDataSetChanged()
    }

    private fun openPlayerFragment(track: Track) {
        findNavController().navigate(
            R.id.action_playlistFragment_to_playerFragment,
            PlayerFragment.createArgs(track)
        )
    }

    private fun renderDuration(playlistDurationInMillis: Long) {
        val playlistDurationInMinutes = dateFormatForPlaylist.format(playlistDurationInMillis)

        binding.playlistDuration.text = "${playlistDurationInMinutes.toString()} ${
            minutesQuantityEndingFormat(
                playlistDurationInMinutes.toInt(),
                requireContext()
            )
        }"
    }
}