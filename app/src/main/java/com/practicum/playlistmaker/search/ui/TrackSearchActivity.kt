package com.practicum.playlistmaker.search.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.search.ui.presenters.PlaceholderState
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.search.ui.presenters.TrackAdapter
import com.practicum.playlistmaker.databinding.ActivitySearchBinding
import com.practicum.playlistmaker.hideKeyboard
import com.practicum.playlistmaker.search.domain.useCase.SearchHistory
import com.practicum.playlistmaker.player.ui.PlayerActivity
import com.practicum.playlistmaker.search.domain.PlaylistmakerApplication
import com.practicum.playlistmaker.search.ui.presenters.TrackClickListener
import com.practicum.playlistmaker.search.ui.presenters.TrackListState

class TrackSearchActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchBinding

    companion object {
        const val SAVED_TEXT_KEY = "SAVED_TEXT_KEY"
        const val DEFAULT_TEXT = ""
        const val SAVE_HISTORY_DIRECTORY = "Saved history"
        const val SAVE_HISTORY_KEY = "Saved history key"
        const val DEBOUNCE_DELAY_FOR_SEND_SEARCH_REQUEST = 2000L
    }

    private var savedInputInSearchView: String = DEFAULT_TEXT
    private lateinit var searchHistory: SearchHistory


    // Переменные в разработке ниже


    private val trackListAdapter = TrackAdapter(
        object : TrackClickListener {
            override fun onTrackClick(track: Track) {
                if (clickDebounce()) {
                    val intent = Intent(this@TrackSearchActivity, PlayerActivity::class.java)
                    intent.putExtra("trackUrl", track.previewUrl)
                    startActivity(intent)
                }
            }

            override fun addToHistoryList(track: Track) {
                viewModel.addToHistory(track)
            }
        }
    )

    private val handler = Handler(Looper.getMainLooper())
    private var isClickAllowed: Boolean = true
    private lateinit var viewModel: TrackSearchViewModel
    private var textWatcher: TextWatcher? = null
    private lateinit var placeholderMessage: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar


    //Переменные в разработке выше


    //Функции в разработке ниже

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, DEBOUNCE_DELAY_FOR_SEND_SEARCH_REQUEST)
        }
        return current
    }


    //Функции в разработке выше

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            TrackSearchViewModel.getViewModelFactory()
        )[TrackSearchViewModel::class.java]

        progressBar = findViewById(R.id.progressBarAtView)
        recyclerView = findViewById(R.id.recyclerViewTracks)

        recyclerView.adapter = trackListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        placeholderMessage = findViewById(R.id.placeholderErrorMessage)

        val sharedPreferencesForSearchHistory: SharedPreferences =
            getSharedPreferences(SAVE_HISTORY_DIRECTORY, MODE_PRIVATE)
        searchHistory = SearchHistory(sharedPreferencesForSearchHistory)
        searchHistory.initHistoryList()

        binding.recyclerViewTracks.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.clearButton.setOnClickListener {
            binding.inputField.setText("")
            binding.inputField.hideKeyboard()
            binding.placeholderErrorLayout.visibility = View.GONE
        }

        binding.clearHistoryButton.setOnClickListener {
            searchHistory.clearHistoryList()
            trackListAdapter.trackList.clear()
            binding.historyViewTitle.setTransitionVisibility(View.GONE)
            binding.clearHistoryButton.setTransitionVisibility(View.GONE)
        }

        binding.backButton.setOnClickListener { finish() }

        fun getClearButtonVisibility(textInView: CharSequence?): Int {
            return if (textInView.isNullOrEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }

        fun showMessage(text: String) {
            if (text.isNotEmpty()) {
                //     trackViewAdapter.clear()
                binding.placeholderErrorMessage.text = text
            } else {
                binding.placeholderErrorMessage.text = "Описание ошибки отсутствует"
            }
        }

        fun setPlaceholderState(placeholderState: PlaceholderState) {
            when (placeholderState) {
                PlaceholderState.GOOD -> {
                    binding.placeholderErrorLayout.visibility = View.GONE
                }

                PlaceholderState.BAD_REQUEST -> {
                    showMessage(getString(R.string.nothing_found))
                    binding.placeholderErrorImage.setImageResource(R.drawable.search_error)
                    binding.placeholderRefreshButton.visibility = View.GONE
                    binding.placeholderErrorLayout.visibility = View.VISIBLE
                }

                PlaceholderState.NO_CONNECTION -> {
                    showMessage(getString(R.string.something_went_wrong))
                    binding.placeholderErrorImage.setImageResource(R.drawable.connection_error)
                    binding.placeholderRefreshButton.visibility = View.VISIBLE
                    binding.placeholderErrorLayout.visibility = View.VISIBLE
                }
            }
        }

//        fun searchTrack() {
//            if (binding.findField.text.isNotEmpty()) {
//
//                itunesService.search(binding.findField.text.toString())
//                    .enqueue(object : Callback<TrackResponse> {
//                        override fun onResponse(
//                            call: Call<TrackResponse>,
//                            response: Response<TrackResponse>
//                        ) {

//                            var checkedResponseBody: List<Track> = mutableListOf()
//
//                            if (response.body()?.results != null) {
//                                checkedResponseBody = response.body()?.results!!
//                            }
//
//                            if (response.code() == 200) {
//                                trackViewAdapter.clear()
//                                if (checkedResponseBody.isNotEmpty()) {
//                                    searchResultsList = checkedResponseBody as MutableList<Track>
//                                    trackViewAdapter.addAllData(searchResultsList)
//
//                                }
//                                if (checkedResponseBody.isEmpty()) {
//                                    setPlaceholderState(PlaceholderState.BAD_REQUEST)
//                                } else {
//                                    setPlaceholderState(PlaceholderState.GOOD)
//                                }
//                            } else {
//                                showMessage(getString(R.string.something_went_wrong))
//                            }
//                        }
//
//                        override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
//                            binding.recievingTrackListProgressBar.visibility = View.GONE
//                            setPlaceholderState(PlaceholderState.NO_CONNECTION)
//                        }
//                    })
//            }
//        }

        viewModel.getScreenState().observe(this) {
            renderScreen(it)
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(textInView: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.searchDebounce(changedText = textInView?.toString() ?: "")
                binding.clearButton.visibility = getClearButtonVisibility(textInView)
                savedInputInSearchView = binding.inputField.text.toString()

                when (binding.inputField.hasFocus() && textInView?.isEmpty() == true && SearchHistory.historyList.isNotEmpty()) {
                    true -> {
                        binding.clearHistoryButton.visibility = View.VISIBLE
                        binding.historyViewTitle.visibility = View.VISIBLE
                        binding.placeholderErrorLayout.visibility = View.GONE
                        //          trackViewAdapter.replaceList(SearchHistory.historyList)
                    }

                    false -> {
                        binding.clearHistoryButton.visibility = View.GONE
                        binding.historyViewTitle.visibility = View.GONE
                        //         trackViewAdapter.replaceList(searchResultsList)
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                // empty
            }
        }
        textWatcher.let { binding.inputField.removeTextChangedListener(it) }

        binding.inputField.setOnFocusChangeListener { _, hasFocus ->
            when (hasFocus && binding.inputField.text.isEmpty() && SearchHistory.historyList.isNotEmpty()) {
                true -> {
                    binding.clearHistoryButton.visibility = View.VISIBLE
                    binding.historyViewTitle.visibility = View.VISIBLE
                    //         trackViewAdapter.replaceList(SearchHistory.historyList)
                }

                false -> {
                    binding.clearHistoryButton.visibility = View.GONE
                    binding.historyViewTitle.visibility = View.GONE
                    //         trackViewAdapter.replaceList(searchResultsList)
                }
            }
        }
        binding.inputField.addTextChangedListener(textWatcher)
        binding.inputField.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.searchRequest(binding.inputField.text.toString())
            }
            false
        }
        binding.placeholderRefreshButton.setOnClickListener {
            viewModel.searchRequest(binding.inputField.text.toString())
        }
    }

    fun showLoading() {
        binding.clearHistoryButton.visibility = View.GONE
        binding.historyViewTitle.visibility = View.GONE
        binding.recyclerViewTracks.visibility = View.GONE
        binding.progressBarAtView.visibility = View.VISIBLE
    }

    fun showError(errorMessage: String) {
        recyclerView.visibility = View.GONE
        placeholderMessage.visibility = View.VISIBLE
        progressBar.visibility = View.GONE

        placeholderMessage.text = errorMessage
    }

    fun showEmpty(emptyMessage: String) {
        showError(emptyMessage)
    }

    fun renderScreen(state: TrackListState) {
        when (state) {
            is TrackListState.ContentFromHistory -> {} //doNothing
            is TrackListState.ContentFromNetwork -> showContent(state.tracks)
            is TrackListState.Empty -> showEmpty(state.message)
            is TrackListState.Error -> showError(state.errorMessage)
            TrackListState.Loading -> showLoading()
        }
    }
    fun showContent(movies: List<Track>) {
        binding.progressBarAtView.visibility = View.GONE
        binding.recyclerViewTracks.visibility = View.VISIBLE
        trackListAdapter.trackList.clear()
        trackListAdapter.trackList.addAll(movies)
        trackListAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        textWatcher?.let { binding.inputField.removeTextChangedListener(it) }

        if (isFinishing) {
            (this.applicationContext as? PlaylistmakerApplication)?.trackSearchViewModel = null
        }
    }

    override fun onResume() {
        super.onResume()
        binding.inputField.setSelection(binding.inputField.length())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SAVED_TEXT_KEY, savedInputInSearchView)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {

        super.onRestoreInstanceState(savedInstanceState)
        savedInputInSearchView = savedInstanceState.getString(SAVED_TEXT_KEY, DEFAULT_TEXT)
        binding.inputField.setText(savedInputInSearchView)
    }
}