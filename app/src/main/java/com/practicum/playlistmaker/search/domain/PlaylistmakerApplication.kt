package com.practicum.playlistmaker.search.domain

import android.app.Application
import com.practicum.playlistmaker.search.ui.SearchViewModel

class PlaylistmakerApplication : Application() {
    var searchViewModel : SearchViewModel? = null
}