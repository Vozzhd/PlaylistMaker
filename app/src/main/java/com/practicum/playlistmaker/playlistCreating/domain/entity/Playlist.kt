package com.practicum.playlistmaker.playlistCreating.domain.entity

import android.net.Uri

class Playlist (
    val id: Int?,
    val name: String,
    val description: String,
    val trackQuantity:Int,
    val coverImagr : Uri? = Uri.EMPTY
)