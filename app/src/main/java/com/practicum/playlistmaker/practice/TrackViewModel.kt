package com.practicum.playlistmaker.practice
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
//import androidx.lifecycle.viewmodel.initializer
//import androidx.lifecycle.viewmodel.viewModelFactory
//import com.practicum.playlistmaker.practice.TracksInteractor
//import com.practicum.playlistmaker.practice.MyApplication
//import com.practicum.playlistmaker.practice.TrackPlayer
//import com.practicum.playlistmaker.practice.TrackScreenState
//
//class TrackViewModel(
//    private val trackId: String,
//    private val tracksInteractor: TracksInteractor,
//    private val trackPlayer: TrackPlayer
//) : ViewModel() {
//
//    private var screenStateLiveData = MutableLiveData<TrackScreenState>(TrackScreenState.Loading)
//    private val playStatusLiveData = MutableLiveData<PlayStatus>()
//
//    init {
//        tracksInteractor.loadTrackData(
//            trackId = trackId,
//            onComplete = { trackModel ->
//                // 1
//                screenStateLiveData.postValue(
//                    TrackScreenState.Content(trackModel)
//                )
//            }
//        )
//    }
//
//    fun pause() {
//        trackPlayer.pause(trackId)
//    }
//
//    override fun onCleared() {
//        trackPlayer.release(trackId)
//
//    }
//
//    private fun getCurrentPlayStatus(): PlayStatus {
//        return playStatusLiveData.value ?: PlayStatus(progress = 0f, isPlaying = false)
//    }
//
//    fun play() {
//        trackPlayer.play(
//            trackId = trackId,
//            statusObserver = object : TrackPlayer.StatusObserver {
//                override fun onProgress(progress: Float) {
//                    playStatusLiveData.value = getCurrentPlayStatus().copy(progress = progress)
//                }
//
//                override fun onStop() {
//                    // 3
//                    playStatusLiveData.value = getCurrentPlayStatus().copy(isPlaying = false)
//                }
//
//                override fun onPlay() {
//                    // 4
//                    playStatusLiveData.value = getCurrentPlayStatus().copy(isPlaying = true)
//                }
//            }
//        )
//    }
//
//    fun getScreenStateLiveData(): LiveData<TrackScreenState> = screenStateLiveData
//    fun getPlayStatusLiveData(): LiveData<PlayStatus> = playStatusLiveData
//
//    companion object {
//        fun getViewModelFactory(trackId: String): ViewModelProvider.Factory = viewModelFactory {
//            initializer {
//                val myApp = this[APPLICATION_KEY] as MyApplication
//                val interactor = myApp.provideTracksInteractor()
//                val trackPlayer = myApp.provideTrackPlayer()
//                TrackViewModel(
//                    trackId,
//                    interactor,
//                    trackPlayer
//                )
//            }
//        }
//    }
//}