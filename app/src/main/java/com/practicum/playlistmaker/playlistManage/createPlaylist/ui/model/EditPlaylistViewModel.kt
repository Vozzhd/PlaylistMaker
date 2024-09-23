package com.practicum.playlistmaker.playlistManage.createPlaylist.ui.model

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.playlistManage.createPlaylist.domain.api.PlaylistManagerInteractor
import com.practicum.playlistmaker.playlistManage.createPlaylist.domain.entity.Playlist
import kotlinx.coroutines.launch

class EditPlaylistViewModel(
    private val playlistManagerInteractor: PlaylistManagerInteractor
) : ViewModel() {

    private val playlistStateMutableLiveData = MutableLiveData<PlaylistState>()
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

    fun saveChangesToPlaylist(playlist: Playlist) {
        viewModelScope.launch {
            playlistManagerInteractor.editPlaylist(playlist)
        }
    }
}