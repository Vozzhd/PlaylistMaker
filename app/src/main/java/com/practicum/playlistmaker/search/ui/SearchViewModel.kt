package com.practicum.playlistmaker.search.ui

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.search.domain.api.HistoryInteractor
import com.practicum.playlistmaker.search.domain.api.TracksInteractor
import com.practicum.playlistmaker.search.domain.models.TrackListState
import com.practicum.playlistmaker.utilities.SingleEventLiveData

class SearchViewModel(
    private val trackSearchInteractor : TracksInteractor,
    private val trackHistoryInteractor : HistoryInteractor,
    private val context: Context

) : ViewModel() {
    companion object {
        private val SEARCH_REQUEST_TOKEN = Any()
        private const val SEARCH_DELAY = 2000L
    }

    private val handler = Handler(Looper.getMainLooper())

    private val clickEvent = SingleEventLiveData<Track>()
    private val stateLiveData = MutableLiveData<TrackListState>()

    fun getScreenState(): LiveData<TrackListState> = stateLiveData
    fun getClickEvent(): LiveData<Track> = clickEvent
    fun onTrackClick(track: Track) {
        trackHistoryInteractor.addToHistoryList(track)
        clickEvent.postValue(track)
    }
    fun searchRequest(newSearchText: String) {
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
        if (newSearchText.isNotEmpty()) {
            renderState(TrackListState.Loading)

            trackSearchInteractor.searchTracks(
                newSearchText,
                object : TracksInteractor.TracksConsumer {
                    override fun consume(foundTracks: List<Track>?, errorMessage: String?) {
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
                })
        }
    }
    private fun renderState(state: TrackListState) = stateLiveData.postValue(state)
    fun showHistory() {
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
        stateLiveData.postValue(TrackListState.ContentFromHistory(getHistoryList()))
    }

    private fun getHistoryList(): List<Track>  = trackHistoryInteractor.getHistoryList()
    fun clearHistory() = trackHistoryInteractor.clearHistoryList()
    fun searchWithDebounce(changedText: String) {
        if (changedText.isNotEmpty()) {
            handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
            val searchRunnable = Runnable { searchRequest(changedText) }
            handler.postDelayed(searchRunnable, SEARCH_REQUEST_TOKEN, SEARCH_DELAY)
        }
    }
    override fun onCleared() {
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
    }

}