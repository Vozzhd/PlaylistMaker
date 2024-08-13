package com.practicum.playlistmaker.mediaLibrary.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.mediaLibrary.domain.api.FavoriteTrackInteractor
import com.practicum.playlistmaker.player.ui.model.FavoriteListState
import kotlinx.coroutines.launch

class FavoriteFragmentViewModel(
    private val favoriteTrackInteractor: FavoriteTrackInteractor
) : ViewModel() {

    private val favoriteListMutableLiveData = MutableLiveData<FavoriteListState>()
    fun observeFavoriteListState(): LiveData<FavoriteListState> = favoriteListMutableLiveData

    init {
        viewModelScope.launch {
            favoriteTrackInteractor.getFavoriteTrackList()
        }
    }
}