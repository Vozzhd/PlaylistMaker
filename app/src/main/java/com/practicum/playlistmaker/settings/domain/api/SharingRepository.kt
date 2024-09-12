package com.practicum.playlistmaker.settings.domain.api

import com.practicum.playlistmaker.settings.domain.model.EmailData

interface SharingRepository {
    fun getShareAppLink(): String
    fun getTermsLink(): String
    fun getSupportEmailData(): EmailData
    fun getSharingPlaylistText() : String
}