package com.practicum.playlistmaker.mediaLibrary.ui.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.mediaLibrary.domain.api.FavoriteTrackInteractor
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.player.ui.model.FavoriteListState
import com.practicum.playlistmaker.utilities.SingleEventLiveData
import kotlinx.coroutines.launch

class FavoriteFragmentViewModel(
    private val context: Context,
    private val favoriteTrackInteractor: FavoriteTrackInteractor
) : ViewModel() {

    init {
        viewModelScope.launch {
            favoriteTrackInteractor
                .getFavoriteTrackList()
                .collect { tracks ->
                    processResult(tracks)
                }
        }
    }

    private fun processResult(tracks: List<Track>) {
        if (tracks.isEmpty()) {
            renderState(FavoriteListState.Empty(context.getString(R.string.mediaLibraryIsEmpty)))
        } else {
            renderState(FavoriteListState.Content(tracks))
            actualFavoriteListLiveData.postValue(tracks)
        }
    }

    private fun renderState(state: FavoriteListState) {
        stateLiveData.postValue(state)
    }

    private val stateLiveData = MutableLiveData<FavoriteListState>()
    private val actualFavoriteListLiveData = MutableLiveData<List<Track>>()

    fun observeState(): LiveData<FavoriteListState> = stateLiveData
    fun observeActualFavoriteListLiveData(): LiveData<List<Track>> = actualFavoriteListLiveData
    private val clickEvent = SingleEventLiveData<Track>()


    fun getClickEvent(): LiveData<Track> = clickEvent
    suspend fun onTrackClick(track: Track) {
        clickEvent.postValue(track)
        toggleFavorite(track)
    }


      suspend fun toggleFavorite(track: Track) {
        if (track.isFavorite) {
            favoriteTrackInteractor.deleteFromFavorite(track)
        } else {
            favoriteTrackInteractor.addToFavorite(track)
        }
        updateFavoriteListContent(track.trackId, track.copy(isFavorite = !track.isFavorite))
    }

    fun updateFavoriteListContent(trackId: String, newTrack: Track) {

        val currentState = stateLiveData.value

        if (currentState is FavoriteListState.Content) {
            val trackIndex = currentState.favoriteList.indexOfFirst { it.trackId == trackId }
            if (trackIndex != -1) {
                stateLiveData.value = FavoriteListState.Content(
                    currentState.favoriteList.toMutableList().also {
                        it[trackIndex] = newTrack
                    }
                )
            }
        }
    }
}