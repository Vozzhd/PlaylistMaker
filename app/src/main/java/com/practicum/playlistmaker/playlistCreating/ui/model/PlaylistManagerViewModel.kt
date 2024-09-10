package com.practicum.playlistmaker.playlistCreating.ui.model

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.playlistCreating.domain.api.PlaylistManagerInteractor
import com.practicum.playlistmaker.playlistCreating.domain.entity.Playlist
import com.practicum.playlistmaker.utilities.App
import com.practicum.playlistmaker.utilities.nextPlaylistId
import kotlinx.coroutines.launch

class PlaylistManagerViewModel(
    private val playlistManagerInteractor: PlaylistManagerInteractor
) : ViewModel() {

    private val playlistStateMutableLiveData = MutableLiveData<PlaylistState>()

    init {
        playlistStateMutableLiveData.value = PlaylistState()
    }

    fun createPlaylist() {
        viewModelScope.launch { playlistManagerInteractor.addPlaylist(
                Playlist(
                    generatePlaylistID(),
                    playlistStateMutableLiveData.value?.playlistName as String,
                    playlistStateMutableLiveData.value?.playlistDescription as String,
                    0,
                    playlistStateMutableLiveData.value?.sourceForImageCover
                )
            )
        }
    }
    private fun generatePlaylistID():Int {
        return ++nextPlaylistId
    }
    fun setUri(uri: Uri) { playlistStateMutableLiveData.value?.sourceForImageCover = uri }
    fun setPlaylistName(name: CharSequence?) { playlistStateMutableLiveData.value?.playlistName = name.toString() }
    fun setPlaylistDescription(description: CharSequence?) { playlistStateMutableLiveData.value?.playlistDescription = description.toString() }
    fun emptyCheck(): Boolean {
        return if (playlistStateMutableLiveData.value?.playlistName?.isEmpty() == false) {
            false
        } else if (playlistStateMutableLiveData.value?.playlistDescription?.isEmpty() == false) {
            false
        } else if (playlistStateMutableLiveData.value?.sourceForImageCover?.toString()
                ?.isEmpty() == false
        ) {
            false
        } else {
            true
        }
    }
}