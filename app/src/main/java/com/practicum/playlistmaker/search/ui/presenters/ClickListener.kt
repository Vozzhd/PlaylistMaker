package com.practicum.playlistmaker.search.ui.presenters

import com.practicum.playlistmaker.player.domain.entity.Track

fun interface ClickListener {
        fun click(track: Track)
    }
