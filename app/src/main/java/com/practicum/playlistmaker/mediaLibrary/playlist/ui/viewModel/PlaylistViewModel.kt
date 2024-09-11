package com.practicum.playlistmaker.mediaLibrary.playlist.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.playlistManage.domain.api.PlaylistManagerInteractor
import com.practicum.playlistmaker.playlistManage.domain.entity.Playlist
import com.practicum.playlistmaker.utilities.SingleEventLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val playlistManagerInteractor: PlaylistManagerInteractor
) : ViewModel() {

    private val clickEvent = SingleEventLiveData<Track>()
    private val longClickEvent = SingleEventLiveData<Track>()
    private val playlistDuration = MutableLiveData<Long>()
    private val playlistTracks = MutableLiveData<List<Track>>()
    private val playlistQuantity = MutableLiveData<Int>()

    fun observePlaylistDuration(): LiveData<Long> = playlistDuration
    fun observeClickEvent(): LiveData<Track> = clickEvent
    fun observeLongClickEvent(): LiveData<Track> = longClickEvent
    fun observePlaylistTracks(): LiveData<List<Track>> = playlistTracks
    fun observePlaylistQuantity(): LiveData<Int> = playlistQuantity

    fun updatePlaylistInformation(playlist: Playlist) {
        calculatePlaylistTime(playlist)
        getTracksInPlaylist(playlist)
        getTracksQuantityInPlaylist(playlist)
    }

    private fun getTracksQuantityInPlaylist(playlist: Playlist) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistQuantity.postValue(playlistManagerInteractor.getTracksInPlaylist(playlist.playlistId).size)
        }
    }

    private fun getTracksInPlaylist(playlist: Playlist) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistTracks.postValue(playlistManagerInteractor.getTracksInPlaylist(playlist.playlistId))
        }
    }

    fun onTrackClick(track: Track) {
        clickEvent.postValue(track)
    }

    private fun calculatePlaylistTime(playlist: Playlist) {
        var playlistDurationInMillis = 0L
        viewModelScope.launch(Dispatchers.IO) {
            val list = playlistManagerInteractor.getTracksInPlaylist(playlist.playlistId)
            list.forEach { track ->
                playlistDurationInMillis += track.trackTimeMillis.toLong()
            }
            playlistDuration.postValue(playlistDurationInMillis)
        }
    }

    fun onLongTrackClick(track: Track) {
        longClickEvent.postValue(track)
    }

    fun deleteTrackFromPlaylist(track: Track, playlist: Playlist) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistManagerInteractor.deleteTrackFromPlaylist(track,playlist)
        }
    }
}