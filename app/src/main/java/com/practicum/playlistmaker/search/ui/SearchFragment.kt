package com.practicum.playlistmaker.search.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.search.ui.presenters.TrackAdapter
import com.practicum.playlistmaker.databinding.SearchFragmentBinding
import com.practicum.playlistmaker.player.ui.PlayerActivity
import com.practicum.playlistmaker.search.domain.models.TrackListState
import com.practicum.playlistmaker.utilities.hideKeyboard
import com.practicum.playlistmaker.utilities.DEFAULT_TEXT
import com.practicum.playlistmaker.utilities.KEY_FOR_TRACK
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    companion object {
        const val SAVED_TEXT_KEY = "Saved text in input field"
    }

    private lateinit var binding: SearchFragmentBinding
    private var inputInSearchView = DEFAULT_TEXT

    private lateinit var trackListAdapter: TrackAdapter
    private val viewModel by viewModel<SearchViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = SearchFragmentBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.inputField.setText(savedInstanceState?.getString(SAVED_TEXT_KEY))


        trackListAdapter = TrackAdapter { viewModel.onTrackClick(it) }
        binding.recyclerViewTracks.layoutManager = LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewTracks.adapter = trackListAdapter


        binding.clearButton.setOnClickListener {
            binding.inputField.setText(DEFAULT_TEXT)
            binding.inputField.hideKeyboard()
            trackListAdapter.notifyDataSetChanged()
            binding.placeholderErrorLayout.visibility = View.GONE
        }

        binding.clearHistoryButton.setOnClickListener {
            viewModel.clearHistory()
            trackListAdapter.trackList.clear()
            trackListAdapter.notifyDataSetChanged()
            binding.historyViewTitle.setTransitionVisibility(View.GONE)
            binding.clearHistoryButton.setTransitionVisibility(View.GONE)
        }


        viewModel.getScreenState().observe(viewLifecycleOwner) { renderScreen(it) }
        viewModel.getClickEvent().observe(viewLifecycleOwner) { openPlayerActivity(it) }


        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(textInView: CharSequence?, p1: Int, p2: Int, p3: Int) {
                trackListAdapter.trackList.clear()
                viewModel.searchWithDebounce(changedText = textInView?.toString() ?: "")

                binding.clearButton.visibility = getClearButtonVisibility(textInView)
                inputInSearchView = textInView.toString()

                if (textInView.isNullOrEmpty()) {
                    viewModel.showHistory()
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                // empty
            }
        }
        textWatcher.let { binding.inputField.removeTextChangedListener(it) }


        binding.inputField.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                viewModel.showHistory()

            }
        }

        binding.inputField.addTextChangedListener(textWatcher)
        binding.inputField.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.searchRequest(binding.inputField.text.toString())
            }
            false
        }
        binding.placeholderRefreshButton.setOnClickListener { viewModel.searchRequest(binding.inputField.text.toString()) }
    }

    private fun renderScreen(state: TrackListState) {
        when (state) {
            TrackListState.Loading -> showLoading()
            is TrackListState.ContentFromNetwork -> showContent(state.tracks)
            is TrackListState.ContentFromHistory -> showHistory(state.tracks)
            is TrackListState.Error -> showError(state.errorMessage)
            is TrackListState.Empty -> showEmpty(state.message)
        }
    }
    private fun showLoading() {
        binding.clearHistoryButton.visibility = View.GONE
        binding.historyViewTitle.visibility = View.GONE
        binding.recyclerViewTracks.visibility = View.GONE
        binding.placeholderErrorLayout.visibility = View.GONE
        binding.placeholderRefreshButton.visibility = View.GONE
        binding.progressBarAtView.visibility = View.VISIBLE
    }

    private fun showContent(movies: List<Track>) {
        binding.progressBarAtView.visibility = View.GONE
        binding.recyclerViewTracks.visibility = View.VISIBLE
        trackListAdapter.trackList.clear()
        trackListAdapter.trackList.addAll(movies)
        trackListAdapter.notifyDataSetChanged()
    }

    private fun showHistory(movies: List<Track>) {
        if (movies.isEmpty()) {
            trackListAdapter.trackList.clear()
            binding.recyclerViewTracks.visibility = View.GONE
        } else {
            trackListAdapter.trackList.clear()
            trackListAdapter.trackList.addAll(movies)
            trackListAdapter.notifyDataSetChanged()
            binding.progressBarAtView.visibility = View.GONE
            binding.placeholderErrorLayout.visibility = View.GONE
            binding.recyclerViewTracks.visibility = View.VISIBLE
            binding.historyViewTitle.visibility = View.VISIBLE
            binding.clearHistoryButton.visibility = View.VISIBLE
        }
    }

    private fun showError(errorMessage: String) {
        binding.progressBarAtView.visibility = View.GONE
        binding.recyclerViewTracks.visibility = View.GONE

        binding.historyViewTitle.visibility = View.GONE
        binding.clearHistoryButton.visibility = View.GONE

        binding.placeholderErrorLayout.visibility = View.VISIBLE
        binding.placeholderErrorMessage.visibility = View.VISIBLE
        binding.placeholderRefreshButton.visibility = View.VISIBLE

        binding.placeholderErrorImage.setImageResource(R.drawable.connection_error)
        binding.placeholderErrorMessage.text = errorMessage
    }

    private fun showEmpty(emptyMessage: String) {
        binding.progressBarAtView.visibility = View.GONE
        binding.recyclerViewTracks.visibility = View.GONE

        binding.placeholderErrorLayout.visibility = View.VISIBLE
        binding.placeholderErrorMessage.visibility = View.VISIBLE

        binding.placeholderErrorImage.setImageResource(R.drawable.search_error)
        binding.placeholderErrorMessage.text = emptyMessage
    }

    private fun openPlayerActivity(track: Track) {
        val intent = Intent(this.activity, PlayerActivity::class.java)
        intent.putExtra(KEY_FOR_TRACK, track)
        startActivity(intent)
    }

    private fun getClearButtonVisibility(textInView: CharSequence?): Int {
        return if (textInView.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
    override fun onResume() {
        super.onResume()
        binding.inputField.setSelection(binding.inputField.length())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SAVED_TEXT_KEY, inputInSearchView)
    }
}
