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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteFragmentViewModel(
    private val context: Context,
    private val favoriteTrackInteractor: FavoriteTrackInteractor
) : ViewModel() {

    init {
        updateFavoriteList()
    }

    private val clickEvent = SingleEventLiveData<Track>()
    private val stateLiveData = MutableLiveData<FavoriteListState>()

    fun observeScreenState(): LiveData<FavoriteListState> = stateLiveData
    fun observeClickEvent(): LiveData<Track> = clickEvent
    fun onTrackClick(track: Track) = clickEvent.postValue(track)


    fun updateFavoriteList() {
        viewModelScope.launch(Dispatchers.IO) {
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
        }
    }

    private fun renderState(state: FavoriteListState) = stateLiveData.postValue(state)

    private val actualFavoriteListLiveData = MutableLiveData<List<Track>>()
    fun observeActualFavoriteListLiveData(): LiveData<List<Track>> = actualFavoriteListLiveData

}