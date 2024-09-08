package com.practicum.playlistmaker.playlistCreating.domain.entity

import android.net.Uri
import java.io.Serializable
class Playlist(
    val id: Int?,
    val name: String,
    val description: String,
    var trackQuantity:Int,
    var listOfTrackIDs: List<String>,
    val sourceOfPlaylistCoverImage: Uri? = Uri.EMPTY
): Serializable {
}