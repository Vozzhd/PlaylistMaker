package com.practicum.playlistmaker.mediaLibrary.playlists.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.mediaLibrary.playlists.ui.presenter.PlaylistsFragmentScreenState
import com.practicum.playlistmaker.playlistManage.createPlaylist.domain.api.PlaylistManagerInteractor
import com.practicum.playlistmaker.playlistManage.createPlaylist.domain.entity.Playlist
import com.practicum.playlistmaker.utilities.SingleEventLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaylistsFragmentViewModel(
    val playlistManagerInteractor: PlaylistManagerInteractor
) : ViewModel() {

    private val playlistsMutableLiveData = MutableLiveData<List<Playlist>>()
    private val playlistsFragmentScreenStateMutableLiveData = MutableLiveData<PlaylistsFragmentScreenState>()
    private val clickEvent = SingleEventLiveData<Playlist>()

    fun observePlaylistsLiveData(): LiveData<List<Playlist>> = playlistsMutableLiveData
    fun observeScreenState(): LiveData<PlaylistsFragmentScreenState> = playlistsFragmentScreenStateMutableLiveData
    fun observeClickEvent():LiveData<Playlist> = clickEvent

    init {
        updateListOfPlaylist()
    }

    fun updateListOfPlaylist() {
        viewModelScope.launch (Dispatchers.IO) {
            playlistManagerInteractor
                .getPlaylistsFromTable()
                .collect { playlists ->
                    processResult(playlists)
            }
        }
    }

    private fun processResult(playlists: List<Playlist>) {
        if (playlists.isEmpty()) {
            renderState(PlaylistsFragmentScreenState.Empty)
        } else {
            renderState(PlaylistsFragmentScreenState.Content(playlists.reversed()))
        }
    }

    private fun renderState(screenState: PlaylistsFragmentScreenState) = playlistsFragmentScreenStateMutableLiveData.postValue(screenState)

    fun onPlaylistClick(playlist: Playlist) {
        clickEvent.postValue(playlist)
    }

}