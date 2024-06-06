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
import com.practicum.playlistmaker.search.data.local.HistoryRepositoryImplementation
import com.practicum.playlistmaker.player.ui.PlayerActivity
import com.practicum.playlistmaker.search.domain.PlaylistmakerApplication
import com.practicum.playlistmaker.search.ui.presenters.TrackClickListener
import com.practicum.playlistmaker.search.ui.presenters.TrackListState

class SearchActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchBinding

    companion object {
        const val SAVED_TEXT_KEY = "SAVED_TEXT_KEY"
        const val DEFAULT_TEXT = ""
        const val SAVE_HISTORY_DIRECTORY = "Saved history"
        const val SAVE_HISTORY_KEY = "Saved history key"
        const val DEBOUNCE_DELAY_FOR_SEND_SEARCH_REQUEST = 2000L
    }

    private var savedInputInSearchView: String = DEFAULT_TEXT
    private lateinit var historyRepositoryImplementation: HistoryRepositoryImplementation

    private val trackListAdapter = TrackAdapter(
        object : TrackClickListener {
            override fun onTrackClick(track: Track) {
                if (clickDebounce()) {
                    val intent = Intent(this@SearchActivity, PlayerActivity::class.java)
                    intent.putExtra(TrackAdapter.KEY_FOR_TRACK, track)
                    startActivity(intent)
                }
            }
        }
    )

    private val handler = Handler(Looper.getMainLooper())
    private var isClickAllowed: Boolean = true
    private lateinit var viewModel: SearchViewModel
    private var textWatcher: TextWatcher? = null
    private lateinit var placeholderMessage: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar


    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, DEBOUNCE_DELAY_FOR_SEND_SEARCH_REQUEST)
        }
        return current
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            SearchViewModel.getViewModelFactory()
        )[SearchViewModel::class.java]
        progressBar = findViewById(R.id.progressBarAtView)
        recyclerView = findViewById(R.id.recyclerViewTracks)

        recyclerView.adapter = trackListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        placeholderMessage = findViewById(R.id.placeholderErrorMessage)

        val sharedPreferencesForSearchHistory: SharedPreferences =
            getSharedPreferences(SAVE_HISTORY_DIRECTORY, MODE_PRIVATE)
        historyRepositoryImplementation =
            HistoryRepositoryImplementation(sharedPreferencesForSearchHistory)
        historyRepositoryImplementation.initHistoryList()

        binding.recyclerViewTracks.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.clearButton.setOnClickListener {
            binding.inputField.setText("")
            binding.inputField.hideKeyboard()
            binding.placeholderErrorLayout.visibility = View.GONE
        }

        binding.clearHistoryButton.setOnClickListener {
            historyRepositoryImplementation.clearHistoryList()
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
                trackListAdapter.clear()
                binding.placeholderErrorMessage.text = text
            } else {
                binding.placeholderErrorMessage.text = "Описание ошибки отсутствует"
            }
        }

        viewModel.getScreenState().observe(this) { renderScreen(it) }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(textInView: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // viewModel.searchDebounce(changedText = textInView?.toString() ?: "")
                binding.clearButton.visibility = getClearButtonVisibility(textInView)
                savedInputInSearchView = binding.inputField.text.toString()

                when (binding.inputField.hasFocus() && textInView?.isEmpty() == true && HistoryRepositoryImplementation.historyList.isNotEmpty()) {
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
            when (hasFocus && binding.inputField.text.isEmpty() && HistoryRepositoryImplementation.historyList.isNotEmpty()) {
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
        binding.placeholderErrorLayout.visibility = View.GONE
        binding.placeholderRefreshButton.visibility = View.GONE
        binding.progressBarAtView.visibility = View.VISIBLE
    }

    fun showError(errorMessage: String) {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE

        binding.placeholderErrorLayout.visibility = View.VISIBLE
        placeholderMessage.visibility = View.VISIBLE
        binding.placeholderRefreshButton.visibility = View.VISIBLE

        binding.placeholderErrorImage.setImageResource(R.drawable.connection_error)
        placeholderMessage.text = errorMessage
    }

    fun showEmpty(emptyMessage: String) {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE

        binding.placeholderErrorLayout.visibility = View.VISIBLE
        placeholderMessage.visibility = View.VISIBLE

        binding.placeholderErrorImage.setImageResource(R.drawable.search_error)
        placeholderMessage.text = emptyMessage
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
            (this.applicationContext as? PlaylistmakerApplication)?.searchViewModel = null
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