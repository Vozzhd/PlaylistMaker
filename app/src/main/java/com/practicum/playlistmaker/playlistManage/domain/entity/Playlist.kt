package com.practicum.playlistmaker.playlistManage.domain.entity

import android.net.Uri
import java.io.Serializable
class Playlist(
    val playlistId: Int = 0,
    val name: String,
    val description: String,
    var trackQuantity:Int,
    val sourceOfPlaylistCoverImage: Uri? = Uri.EMPTY
): Serializable {
}