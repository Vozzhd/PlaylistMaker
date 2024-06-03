package com.practicum.playlistmaker.search.domain

import android.app.Application
import com.practicum.playlistmaker.search.ui.TrackSearchViewModel

class PlaylistmakerApplication : Application() {
    var trackSearchViewModel : TrackSearchViewModel? = null
}