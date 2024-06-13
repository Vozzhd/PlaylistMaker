package com.practicum.playlistmaker.search.ui

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.creator.Creator
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.search.domain.api.TracksInteractor
import com.practicum.playlistmaker.search.domain.models.TrackListState
import com.practicum.playlistmaker.utilities.SingleEventLiveData

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        private val SEARCH_REQUEST_TOKEN = Any()
        private const val SEARCH_DELAY = 2000L

        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SearchViewModel(this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application)
            }
        }
    }

    private val trackSearchInteractor = Creator.provideTracksInteractor(getApplication())
    private val trackHistoryInteractor = Creator.provideTrackHistoryInteractor(getApplication())
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
                                        getApplication<Application>().getString(
                                            R.string.something_went_wrong
                                        )
                                    )
                                )
                            }

                            tracks.isEmpty() -> {
                                renderState(
                                    TrackListState.Empty(
                                        message = getApplication<Application>().getString(
                                            R.string.nothing_found
                                        )
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