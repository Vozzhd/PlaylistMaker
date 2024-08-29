package com.practicum.playlistmaker.mediaLibrary.playlist.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.PlaylistFragmentBinding
import com.practicum.playlistmaker.mediaLibrary.playlist.ui.presenter.PlaylistAdapter
import com.practicum.playlistmaker.mediaLibrary.playlist.ui.viewModel.PlaylistFragmentViewModel
import com.practicum.playlistmaker.mediaLibrary.playlist.ui.presenter.PlaylistsFragmentScreenState
import com.practicum.playlistmaker.playlistCreating.domain.entity.Playlist
import com.practicum.playlistmaker.utilities.debounce
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistFragment : Fragment() {

    companion object {
        fun newInstance() = PlaylistFragment().apply { }
        const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private var _binding: PlaylistFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var onPlaylistClickDebounce: (Playlist) -> Unit
    private lateinit var playlistsAdapter: PlaylistAdapter
    private val viewModel: PlaylistFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PlaylistFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.updateListOfPlaylist()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.createNewPlaylist.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_mediaLibraryFragment_to_newPlaylistFragment)
        }
        onPlaylistClickDebounce = debounce(CLICK_DEBOUNCE_DELAY, viewLifecycleOwner.lifecycleScope, false) { playlist ->
            viewModel.onPlaylistClick(playlist) }

        playlistsAdapter = PlaylistAdapter(onPlaylistClickDebounce)
        binding.playlistRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.playlistRecyclerView.adapter = playlistsAdapter

        viewModel.observePlaylistsLiveData().observe(viewLifecycleOwner) {
            playlistsAdapter.listOfPlaylist.clear()
            playlistsAdapter.listOfPlaylist.addAll(it)
            binding.playlistRecyclerView.adapter?.notifyDataSetChanged()
        }
        viewModel.observeScreenState().observe(viewLifecycleOwner) { renderScreen(it) }
    }

    private fun renderScreen(screenstate: PlaylistsFragmentScreenState) {
        when (screenstate) {
            is PlaylistsFragmentScreenState.Content -> showContent(screenstate.playlists)
            is PlaylistsFragmentScreenState.Empty -> showEmpty()
        }
    }

    private fun showEmpty() {
        binding.playlistRecyclerView.visibility = View.GONE
        binding.listOfPlaylistIsEmptyPlaceholder.visibility = View.VISIBLE
    }

    private fun showContent(playlists: List<Playlist>) {
        playlistsAdapter.listOfPlaylist.clear()
        playlistsAdapter.listOfPlaylist.addAll(playlists)
        binding.playlistRecyclerView.adapter?.notifyDataSetChanged()
        binding.playlistRecyclerView.visibility = View.VISIBLE
        binding.listOfPlaylistIsEmptyPlaceholder.visibility = View.GONE
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onResume() {
        viewModel.updateListOfPlaylist()
        binding.playlistRecyclerView.adapter?.notifyDataSetChanged()
        super.onResume()
    }
}