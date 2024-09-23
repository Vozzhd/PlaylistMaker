package com.practicum.playlistmaker.settings.domain.api

import com.practicum.playlistmaker.settings.domain.model.EmailData

interface ExternalNavigator {
    fun shareLink(link: String)
    fun sharePlaylist(playlistText:String)
    fun openLink(link: String)
    fun sendEmail(email: EmailData)
}