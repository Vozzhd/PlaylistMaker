package com.practicum.playlistmaker.mediaLibrary.playlist.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.playlistCreating.domain.api.PlaylistManagerInteractor
import com.practicum.playlistmaker.playlistCreating.domain.entity.Playlist
import kotlinx.coroutines.launch

class PlaylistFragmentViewModel(
    val playlistManagerInteractor : PlaylistManagerInteractor
) : ViewModel() {
    private val listOfPlaylistMutableLiveData = MutableLiveData<List<Playlist>>()
    val observeListOfPlaylistMutableLiveData = listOfPlaylistMutableLiveData

    fun updateListOfPlaylist() {
        viewModelScope.launch {
            playlistManagerInteractor.getPlaylistsFromTable().collect {
                listOfPlaylistMutableLiveData.value = it.reversed()
            }
        }
    }
}