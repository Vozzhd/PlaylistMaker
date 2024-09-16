package com.practicum.playlistmaker.mediaLibrary.playlist.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.playlistManage.createPlaylist.domain.api.PlaylistManagerInteractor
import com.practicum.playlistmaker.playlistManage.createPlaylist.domain.entity.Playlist
import com.practicum.playlistmaker.settings.domain.api.SharingInteractor
import com.practicum.playlistmaker.utilities.Result
import com.practicum.playlistmaker.utilities.SingleEventLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val playlistManagerInteractor: PlaylistManagerInteractor,
    private val sharingInteractor: SharingInteractor
) : ViewModel() {

    private val clickEvent = SingleEventLiveData<Track>()
    private val longClickEvent = SingleEventLiveData<Track>()
    private val playlist = MutableLiveData<Playlist>()
    private val playlistDuration = MutableLiveData<Long>()
    private val playlistTracks = MutableLiveData<List<Track>>()
    private val playlistQuantity = MutableLiveData<Int>()
    private val shareStatus = MutableLiveData<Result>()
    private val trackListForShare = MutableLiveData<List<Track>>()

    fun observePlaylistDuration(): LiveData<Long> = playlistDuration
    fun observeClickEvent(): LiveData<Track> = clickEvent
    fun observeLongClickEvent(): LiveData<Track> = longClickEvent
    fun observePlaylistTracks(): LiveData<List<Track>> = playlistTracks
    fun observePlaylistQuantity(): LiveData<Int> = playlistQuantity
    fun observeShareStatus(): LiveData<Result> = shareStatus
    fun observePlaylist(): LiveData<Playlist> = playlist
    fun observeTrackListForShare(): LiveData<List<Track>> = trackListForShare

    fun updatePlaylistInformation(playlist: Playlist) {
        calculatePlaylistTime(playlist)
        getTracksInPlaylist(playlist)
        getTracksQuantityInPlaylist(playlist)
        getPlaylistInformation(playlist)
    }

    private fun getPlaylistInformation(it: Playlist) {
        viewModelScope.launch(Dispatchers.IO) {
            playlist.postValue(playlistManagerInteractor.getPlaylist(it.playlistId))
        }
    }

    private fun getTracksQuantityInPlaylist(playlist: Playlist) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistQuantity.postValue(playlistManagerInteractor.getTracksInPlaylist(playlist.playlistId).size)
        }
    }

    private fun getTracksInPlaylist(playlist: Playlist) {
        viewModelScope.launch(Dispatchers.IO) {
            val listWithTracks = playlistManagerInteractor.getTracksInPlaylist(playlist.playlistId)
            playlistTracks.postValue(listWithTracks)
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
            playlistManagerInteractor.deleteTrackFromPlaylist(track, playlist)
        }
    }

    fun shareRequest(playlist: Playlist) {
        if (playlist.trackQuantity == 0) {
            shareStatus.postValue(Result(false, ""))
        } else {
            shareStatus.postValue(Result(true, ""))
        }
    }

    fun getTrackList(playlistId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            trackListForShare.postValue(playlistManagerInteractor.getTracksInPlaylist(playlistId))
        }
    }

    fun shareTrackList(shareText: String) {
        sharingInteractor.sharePlaylist(shareText)
    }


    fun deletePlaylist(playlist: Playlist) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistManagerInteractor.deletePlaylist(playlist)
        }
    }
}