package com.practicum.playlistmaker.search.ui.presenters

import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.player.domain.entity.Track

class TrackAdapter(
//   private val trackList: MutableList<Track>,
//    sharedPreferences: SharedPreferences,
    private val clickListener: TrackClickListener
) : RecyclerView.Adapter<TrackViewHolder>() {
    companion object {
        const val KEY_FOR_TRACK = "TrackInExtra"
        const val CLICK_DEBOUNCE_DELAY = 1000L
    }

   // private val trackList = ArrayList<Track>() это в примере ArrayList - не моё
   var trackList : MutableList<Track> = mutableListOf()

    private val handler = Handler(Looper.getMainLooper())
   // private val searchHistory = SearchHistory(sharedPreferences)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
//        val view = LayoutInflater
//            .from(parent.context)
//            .inflate(R.layout.track_view, parent, false)
//        return TrackViewHolder(view)
        return TrackViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return trackList.size
    }

    private var isClickAllowed = true
    private fun clickDebounce(): Boolean {
        val currentState = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return currentState
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {

        holder.bind(trackList[position])

        holder.itemView.setOnClickListener {
            if (clickDebounce()) {
           //     searchHistory.addToHistoryList(trackList[position])
                clickListener.onTrackClick(trackList[position])
            }
        }
    }

    fun clear() {
        trackList.clear()
        notifyDataSetChanged()
    }
//
//    fun addAllData(mutableList: MutableList<Track>) {
//        trackList.addAll(mutableList)
//        notifyDataSetChanged()
//    }
//
//    fun replaceList(list: List<Track>) {
//        trackList.clear()
//        trackList.addAll(list)
//        this.notifyDataSetChanged()
//    }
}
