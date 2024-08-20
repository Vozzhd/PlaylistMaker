package com.practicum.playlistmaker.playlistCreating.ui.model

import android.net.Uri

class PlaylistState {
    var playlistName: String = ""
    var playlistDescription: String = ""
    var sourceForImageCover: Uri = Uri.EMPTY
    var tracksInPlaylist: List<String> = emptyList()
}