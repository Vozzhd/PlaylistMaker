package com.practicum.playlistmaker.mediaLibrary.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.FavoriteFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.practicum.playlistmaker.mediaLibrary.ui.viewModel.FavoriteFragmentViewModel
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.player.ui.PlayerActivity
import com.practicum.playlistmaker.player.ui.model.FavoriteListState
import com.practicum.playlistmaker.search.ui.SearchFragment.Companion.CLICK_DEBOUNCE_DELAY
import com.practicum.playlistmaker.search.ui.presenters.TrackAdapter
import com.practicum.playlistmaker.utilities.KEY_FOR_TRACK
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

        binding.mediaLibraryEmptyText.text = getText(R.string.mediaLibraryIsEmpty)

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
        viewModel.observeClickEvent().observe(viewLifecycleOwner) { openPlayerActivity(it) }
        viewModel.observeActualFavoriteListLiveData().observe(viewLifecycleOwner) {
            showContent(it)
        }
    }

    private fun openPlayerActivity(track: Track?) {
        val intent = Intent(requireContext(), PlayerActivity::class.java)
        intent.putExtra(KEY_FOR_TRACK, track)
        startActivity(intent)
    }

    private fun renderScreen(state: FavoriteListState) {
        when (state) {
            is FavoriteListState.Content -> {
                showContent(state.favoriteList)
            }

            is FavoriteListState.Empty -> showMessage(state.message)
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

    private fun showMessage(message: String) {
        binding.recyclerViewFavoriteTracks.visibility = View.GONE
        binding.mediaLibraryEmptyImage.visibility = View.VISIBLE
        binding.mediaLibraryEmptyText.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}