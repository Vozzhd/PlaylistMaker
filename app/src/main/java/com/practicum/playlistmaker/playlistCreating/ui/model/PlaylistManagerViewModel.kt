package com.practicum.playlistmaker.playlistCreating.ui.model

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.playlistCreating.domain.api.PlaylistManagerInteractor
import com.practicum.playlistmaker.playlistCreating.domain.entity.Playlist
import kotlinx.coroutines.launch

class PlaylistManagerViewModel(
    private val playlistManagerInteractor: PlaylistManagerInteractor
) : ViewModel() {


    private val playlistStateMutableLiveData = MutableLiveData<PlaylistState>(

    )

    init {
        playlistStateMutableLiveData.value = PlaylistState()
    }

    fun createPlaylist() {
        viewModelScope.launch {
            playlistManagerInteractor.addPlaylist(
                Playlist(
                    null,
                    playlistStateMutableLiveData.value?.playlistName as String,
                    playlistStateMutableLiveData.value?.playlistDescription as String,
                    0,
                    mutableListOf<String>(),
                    playlistStateMutableLiveData.value?.sourceForImageCover
                )
            )
        }
    }

    fun setUri(uri: Uri) {
        playlistStateMutableLiveData.value?.sourceForImageCover = uri
    }

    fun setPlaylistName(name: CharSequence?) {
        playlistStateMutableLiveData.value?.playlistName = name.toString()
    }

    fun setPlaylistDescription(description: CharSequence?) {
        playlistStateMutableLiveData.value?.playlistDescription = description.toString()
    }

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