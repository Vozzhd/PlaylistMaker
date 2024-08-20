package com.practicum.playlistmaker.search.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.search.domain.api.HistoryInteractor
import com.practicum.playlistmaker.search.domain.api.TracksInteractor
import com.practicum.playlistmaker.search.domain.models.TrackListState
import com.practicum.playlistmaker.utilities.SingleEventLiveData
import com.practicum.playlistmaker.utilities.debounce
import kotlinx.coroutines.launch

class SearchViewModel(
    private val trackSearchInteractor: TracksInteractor,
    private val trackHistoryInteractor: HistoryInteractor,
    private val context: Context

) : ViewModel() {
    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    private val clickEvent = SingleEventLiveData<Track>()
    private val stateLiveData = MutableLiveData<TrackListState>()
    private var latestSearchText: String? = null
    private val trackSearchDebounce = debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changedText -> searchRequest(changedText) }

    fun observeScreenState(): LiveData<TrackListState> = stateLiveData
    fun observeClickEvent(): LiveData<Track> = clickEvent
    fun onTrackClick(track: Track) {
        trackHistoryInteractor.addToHistoryList(track)
        clickEvent.postValue(track)
    }

    fun searchRequest(newSearchText: String) {

        if (newSearchText.isNotEmpty()) {
            renderState(TrackListState.Loading)

            viewModelScope.launch {
                trackSearchInteractor
                    .searchTracks(newSearchText)
                    .collect { pair ->
                        processResult(pair.first, pair.second)
                    }
            }
        }
    }

    private fun processResult(foundTracks: List<Track>?, errorMessage: String?) {

        val tracks = mutableListOf<Track>()
        if (foundTracks != null) {
            tracks.addAll(foundTracks)
        }

        when {
            errorMessage != null -> {
                renderState(
                    TrackListState.Error(
                        context.getString(R.string.something_went_wrong)
                    )
                )
            }

            tracks.isEmpty() -> {
                renderState(
                    TrackListState.Empty(
                        context.getString(R.string.nothing_found)
                    )
                )
            }

            else -> {
                renderState(TrackListState.ContentFromNetwork(tracks))
            }
        }
    }

    private fun renderState(state: TrackListState) = stateLiveData.postValue(state)

    fun showHistory() {
        stateLiveData.postValue(TrackListState.ContentFromHistory(getHistoryList()))
    }

    private fun getHistoryList(): List<Track> = trackHistoryInteractor.getHistoryList()

    fun clearHistory() = trackHistoryInteractor.clearHistoryList()

    fun searchWithDebounce(changedText: String) {
        if (latestSearchText != changedText) {
            latestSearchText = changedText
            trackSearchDebounce(changedText)
        }
    }
}