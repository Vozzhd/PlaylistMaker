package com.practicum.playlistmaker.playlistCreating.ui.model

import android.net.Uri

class NewPlaylistState {
    val playlistName: String = ""
    val description: String = ""
    val image: Uri = Uri.EMPTY
    val tracksInPlaylist: List<String> = emptyList()
}