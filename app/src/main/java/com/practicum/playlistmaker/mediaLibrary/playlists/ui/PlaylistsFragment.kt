package com.practicum.playlistmaker.mediaLibrary.playlists.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.PlaylistsFragmentBinding
import com.practicum.playlistmaker.mediaLibrary.playlist.ui.PlaylistFragment
import com.practicum.playlistmaker.mediaLibrary.playlists.ui.presenter.PlaylistsFragmentScreenState
import com.practicum.playlistmaker.mediaLibrary.playlists.ui.viewModel.PlaylistsFragmentViewModel
import com.practicum.playlistmaker.player.ui.presenters.PlaylistsAdapterPlaylistFragment
import com.practicum.playlistmaker.playlistManage.domain.entity.Playlist
import com.practicum.playlistmaker.utilities.debounce
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistsFragment : Fragment() {

    companion object {
        fun newInstance() = PlaylistsFragment().apply { }
        const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private var _binding: PlaylistsFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var onPlaylistClickDebounce: (Playlist) -> Unit
    private lateinit var playlistsAdapter: PlaylistsAdapterPlaylistFragment
    private val viewModel: PlaylistsFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PlaylistsFragmentBinding.inflate(inflater, container, false)
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

        onPlaylistClickDebounce =
            debounce(CLICK_DEBOUNCE_DELAY, viewLifecycleOwner.lifecycleScope, false) { playlist ->
                viewModel.onPlaylistClick(playlist)
            }

        playlistsAdapter = PlaylistsAdapterPlaylistFragment(onPlaylistClickDebounce)
        binding.playlistRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.playlistRecyclerView.adapter = playlistsAdapter

        viewModel.observePlaylistsLiveData().observe(viewLifecycleOwner) {
            playlistsAdapter.listOfPlaylist.clear()
            playlistsAdapter.listOfPlaylist.addAll(it)
            binding.playlistRecyclerView.adapter?.notifyDataSetChanged()
        }
        viewModel.observeScreenState().observe(viewLifecycleOwner) { renderScreen(it) }
        viewModel.observeClickEvent().observe(viewLifecycleOwner) { openPlaylistFragment(it) }
    }

    private fun openPlaylistFragment(playlist: Playlist) {
        findNavController().navigate(
            R.id.action_mediaLibraryFragment_to_playlistFragment,
            PlaylistFragment.createArgs(playlist))
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