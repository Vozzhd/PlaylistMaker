package com.practicum.playlistmaker.mediaLibrary.playlist.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.player.domain.model.PlayerState
import com.practicum.playlistmaker.playlistCreating.domain.api.PlaylistManagerInteractor
import com.practicum.playlistmaker.playlistCreating.domain.entity.Playlist
import com.practicum.playlistmaker.utilities.SingleEventLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val playlistManagerInteractor: PlaylistManagerInteractor
) : ViewModel() {

    private val clickEvent = SingleEventLiveData<Track>()
    private lateinit var playlist: Playlist

    private val playlistDuration = MutableLiveData<Long>()
    fun observePlaylistDuration(): LiveData<Long> = playlistDuration
    fun observeClickEvent(): LiveData<Track> = clickEvent

    fun initPlaylist(playlist: Playlist) {
        calculatePlaylistTime(playlist)
        this.playlist = playlist
    }

    fun onTrackClick(track: Track) {
        clickEvent.postValue(track)
    }

    fun calculatePlaylistTime(playlist: Playlist) {
        var tracks = 0L
        viewModelScope.launch(Dispatchers.IO) {
            val list = playlistManagerInteractor.getTracksInPlaylist(playlist.playlistId)
            list.forEach { track ->
                tracks += track.trackTimeMillis.toLong()
            }
            playlistDuration.postValue(tracks)
        }
    }
}