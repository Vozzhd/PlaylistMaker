package com.practicum.playlistmaker.search.ui.presenters

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.player.ui.PlayerActivity
import com.practicum.playlistmaker.search.ui.SearchActivity
import com.practicum.playlistmaker.utilities.KEY_FOR_TRACK

class TrackClickListenerImpl (private val context: Context) : ClickListener {

    private val handler = Handler(Looper.getMainLooper())
    private var isClickAllowed: Boolean = true
    override fun click(track: Track) {
        if (clickDebounce()) {
            val intent = Intent(context, PlayerActivity::class.java).putExtra(KEY_FOR_TRACK, track.trackId)
            intent.putExtra(KEY_FOR_TRACK, track)
            context.startActivity(intent)
        }
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed(
                { isClickAllowed = true },
                SearchActivity.DEBOUNCE_DELAY_FOR_SEND_SEARCH_REQUEST
            )
        }
        return current
    }
}