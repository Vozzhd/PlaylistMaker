package com.practicum.playlistmaker.utilities

import android.app.Application
import com.practicum.playlistmaker.search.ui.SearchViewModel

class PlaylistmakerApplication : Application() {
    var searchViewModel : SearchViewModel? = null
}