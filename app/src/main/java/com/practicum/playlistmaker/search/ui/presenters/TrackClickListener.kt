package com.practicum.playlistmaker.search.ui.presenters

import com.practicum.playlistmaker.player.domain.entity.Track

interface TrackClickListener {

        fun onTrackClick(track: Track)
        fun addToHistoryList(track: Track)
    }
