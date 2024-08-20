package com.practicum.playlistmaker.mediaLibrary.playlist.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.PlaylistFragmentBinding
import com.practicum.playlistmaker.mediaLibrary.playlist.ui.presenter.PlaylistAdapter
import com.practicum.playlistmaker.mediaLibrary.playlist.ui.viewModel.PlaylistFragmentViewModel
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
    private val isClickAllowed: Boolean = true
    private val viewModel: PlaylistFragmentViewModel by viewModel()
    private var listOfPlaylist: MutableList<Playlist> = mutableListOf()
    private lateinit var onPlaylistClickDebounce: (Playlist) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PlaylistFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.createNewPlaylist.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_mediaLibraryFragment_to_newPlaylistFragment)
        }

        onPlaylistClickDebounce =
            debounce(CLICK_DEBOUNCE_DELAY, viewLifecycleOwner.lifecycleScope, false) { track ->
                isClickAllowed
            }
        observeInit()
        recyclerViewInit()

    }


    private fun observeInit() {
        viewModel.observeListOfPlaylistMutableLiveData.observe(viewLifecycleOwner) {
            listOfPlaylist.clear()
            listOfPlaylist.addAll(it)
            binding.playlistRecyclerView.adapter?.notifyDataSetChanged()

            binding.playlistRecyclerView.visibility = View.VISIBLE
            binding.listOfPlaylistIsEmptyPlaceholder.visibility = View.GONE
        }
    }

    fun recyclerViewInit() {
        binding.playlistRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.playlistRecyclerView.adapter = PlaylistAdapter(listOfPlaylist = listOfPlaylist) {
            isClickAllowed
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onResume() {
        Log.e("On", "Resume")
        viewModel.updateListOfPlaylist()
        binding.playlistRecyclerView.adapter?.notifyDataSetChanged()
        super.onResume()
    }
}