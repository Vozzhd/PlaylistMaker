package com.practicum.playlistmaker.search.domain.useCase

import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.search.domain.api.HistoryRepository
import com.practicum.playlistmaker.search.domain.api.HistoryInteractor

class HistoryInteractorImplementation (private val historyRepository: HistoryRepository): HistoryInteractor{

    override fun getHistoryList(): List<Track> {
      return  historyRepository.getHistoryList()
    }

    override fun clearHistoryList() {
        historyRepository.clearHistoryList()
    }

    override fun putSavedTracksToSharedPreferences(tracks: MutableList<Track>) {
        historyRepository.putSavedTracksToSharedPreferences(tracks)
    }

    override fun addToHistoryList(track: Track) {
        historyRepository.addToHistoryList(track)
    }

}