package com.practicum.playlistmaker.mediaLibrary.favorite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.FavoriteFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.practicum.playlistmaker.mediaLibrary.favorite.ui.viewModel.FavoriteFragmentViewModel
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.player.ui.PlayerFragment
import com.practicum.playlistmaker.mediaLibrary.favorite.ui.model.FavoriteListState
import com.practicum.playlistmaker.search.ui.SearchFragment.Companion.CLICK_DEBOUNCE_DELAY
import com.practicum.playlistmaker.search.ui.presenters.TrackAdapter
import com.practicum.playlistmaker.utilities.debounce
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {

    companion object {
        fun newInstance() = FavoriteFragment().apply { }
    }

    private var _binding: FavoriteFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var onTrackClickDebounce: (Track) -> Unit
    private lateinit var favoriteTrackListAdapter: TrackAdapter
    private val viewModel by viewModel<FavoriteFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.updateFavoriteList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onTrackClickDebounce =
            debounce(CLICK_DEBOUNCE_DELAY, viewLifecycleOwner.lifecycleScope, false) { track ->
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.onTrackClick(track)
                }
            }
        favoriteTrackListAdapter = TrackAdapter(onTrackClickDebounce)

        binding.recyclerViewFavoriteTracks.layoutManager =
            LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL, true)
        binding.recyclerViewFavoriteTracks.adapter = favoriteTrackListAdapter

        viewModel.observeScreenState().observe(viewLifecycleOwner) { renderScreen(it) }
        viewModel.observeClickEvent().observe(viewLifecycleOwner) { openPlayerFragment(it) }
        viewModel.observeActualFavoriteListLiveData().observe(viewLifecycleOwner) {
            showContent(it)
        }
    }

    private fun openPlayerFragment(track: Track) {
            findNavController().navigate(
                R.id.action_mediaLibraryFragment_to_playerFragment,
                PlayerFragment.createArgs(track))
    }

    private fun renderScreen(state: FavoriteListState) {
        when (state) {
            is FavoriteListState.Content -> {
                showContent(state.favoriteList)
            }

            is FavoriteListState.Empty -> showMessage()
        }
    }

    private fun showContent(favoriteList: List<Track>) {
        binding.mediaLibraryEmptyImage.visibility = View.GONE
        binding.mediaLibraryEmptyText.visibility = View.GONE
        binding.recyclerViewFavoriteTracks.visibility = View.VISIBLE
        favoriteTrackListAdapter.trackList.clear()
        favoriteTrackListAdapter.trackList.addAll(favoriteList)
        favoriteTrackListAdapter.notifyDataSetChanged()
    }

    private fun showMessage() {
        binding.recyclerViewFavoriteTracks.visibility = View.GONE
        binding.mediaLibraryEmptyImage.visibility = View.VISIBLE
        binding.mediaLibraryEmptyText.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}