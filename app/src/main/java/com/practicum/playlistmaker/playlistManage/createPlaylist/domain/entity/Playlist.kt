package com.practicum.playlistmaker.playlistManage.createPlaylist.domain.entity

import android.net.Uri
import java.io.Serializable
class Playlist(
    val playlistId: Int = 0,
    var name: String,
    var description: String,
    var trackQuantity:Int,
    var sourceOfPlaylistCoverImage: Uri? = Uri.EMPTY
): Serializable {
}