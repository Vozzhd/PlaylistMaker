package com.practicum.playlistmaker.mediaLibrary.playlist.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.playlistCreating.domain.entity.Playlist
import com.practicum.playlistmaker.utilities.SingleEventLiveData

class PlaylistViewModel : ViewModel() {
    private val clickEvent = SingleEventLiveData<Track>()

    fun observeClickEvent(): LiveData<Track> = clickEvent

    fun initPlaylist(playlist: Playlist) {
        TODO("Not yet implemented")
    }
    fun onTrackClick(track: Track) {
        clickEvent.postValue(track)
    }

    fun calculatePlaylistTime(playlist: Playlist) {

    }

}