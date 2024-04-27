package com.practicum.playlistmaker.presentation.ui
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.playlistmaker.presentation.presenters.PlaceholderState
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.domain.entity.Track
import com.practicum.playlistmaker.presentation.presenters.TrackAdapter
import com.practicum.playlistmaker.data.network.TrackResponse
import com.practicum.playlistmaker.databinding.ActivitySearchBinding
import com.practicum.playlistmaker.domain.hideKeyboard
import com.practicum.playlistmaker.data.SearchHistory
import com.practicum.playlistmaker.data.network.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchBinding

    companion object {
        const val SAVED_TEXT_KEY = "SAVED_TEXT_KEY"
        const val DEFAULT_TEXT = ""
        const val SAVE_HISTORY_DIRECTORY = "Saved history"
        const val SAVE_HISTORY_KEY = "Saved history key"
        const val DEBOUNCE_DELAY_FOR_SEND_SEARCH_REQUEST = 2000L
    }

    private var savedInputInFindView: String = DEFAULT_TEXT
    private lateinit var searchHistory: SearchHistory
    private lateinit var searchResultsList: MutableList<Track>


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val itunesService = retrofit.create(RetrofitApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchResultsList = mutableListOf()

        val sharedPreferencesForSearchHistory: SharedPreferences =
            getSharedPreferences(SAVE_HISTORY_DIRECTORY, MODE_PRIVATE)
        searchHistory = SearchHistory(sharedPreferencesForSearchHistory)
        searchHistory.initHistoryList()

        val handler = Handler(Looper.getMainLooper())

        val trackViewAdapter = TrackAdapter(searchResultsList, sharedPreferencesForSearchHistory) { track ->
                val playerActivityIntent = Intent(this, PlayerActivity::class.java)
                playerActivityIntent.putExtra(TrackAdapter.KEY_FOR_TRACK, track)
                startActivity(playerActivityIntent)
            }

        binding.recyclerViewTracks.adapter = trackViewAdapter
        binding.recyclerViewTracks.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.clearButton.setOnClickListener {
            binding.findField.setText("")
            binding.findField.hideKeyboard()
            binding.placeholderErrorLayout.visibility = View.GONE
        }

        binding.clearHistoryButton.setOnClickListener {
            searchHistory.clearHistoryList()
            trackViewAdapter.clear()
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
                trackViewAdapter.clear()
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

        fun searchTrack() {
            if (binding.findField.text.isNotEmpty()) {

                binding.clearHistoryButton.visibility = View.GONE
                binding.historyViewTitle.visibility = View.GONE
                binding.recyclerViewTracks.visibility= View.GONE
                binding.recievingTrackListProgressBar.visibility = View.VISIBLE



                itunesService.search(binding.findField.text.toString())
                    .enqueue(object : Callback<TrackResponse> {
                        override fun onResponse(
                            call: Call<TrackResponse>,
                            response: Response<TrackResponse>
                        ) {
                            binding.recievingTrackListProgressBar.visibility = View.GONE
                            binding.recyclerViewTracks.visibility= View.VISIBLE
                            var checkedResponseBody: List<Track> = mutableListOf()

                            if (response.body()?.results != null) {
                                checkedResponseBody = response.body()?.results!!
                            }

                            if (response.code() == 200) {
                                trackViewAdapter.clear()
                                if (checkedResponseBody.isNotEmpty()) {
                                    searchResultsList = checkedResponseBody as MutableList<Track>
                                    trackViewAdapter.addAllData(searchResultsList)

                                }
                                if (checkedResponseBody.isEmpty()) {
                                    setPlaceholderState(PlaceholderState.BAD_REQUEST)
                                } else {
                                    setPlaceholderState(PlaceholderState.GOOD)
                                }
                            } else {
                                showMessage(getString(R.string.something_went_wrong))
                            }
                        }

                        override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                            binding.recievingTrackListProgressBar.visibility = View.GONE
                            setPlaceholderState(PlaceholderState.NO_CONNECTION)
                        }
                    })
            }
        }

        val searchRunnable = Runnable { searchTrack() }
        fun searchDebounce() {
            handler.removeCallbacks(searchRunnable)
            handler.postDelayed(searchRunnable, DEBOUNCE_DELAY_FOR_SEND_SEARCH_REQUEST)
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(textInView: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchDebounce()
                binding.clearButton.visibility = getClearButtonVisibility(textInView)
                savedInputInFindView = binding.findField.text.toString()

                when (binding.findField.hasFocus() && textInView?.isEmpty() == true && SearchHistory.historyList.isNotEmpty()) {
                    true -> {
                        binding.clearHistoryButton.visibility = View.VISIBLE
                        binding.historyViewTitle.visibility = View.VISIBLE
                        binding.placeholderErrorLayout.visibility = View.GONE
                        trackViewAdapter.replaceList(SearchHistory.historyList)
                    }

                    false -> {
                        binding.clearHistoryButton.visibility = View.GONE
                        binding.historyViewTitle.visibility = View.GONE
                        trackViewAdapter.replaceList(searchResultsList)
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                // empty
            }
        }

        binding.findField.setOnFocusChangeListener { _, hasFocus ->
            when (hasFocus && binding.findField.text.isEmpty() && SearchHistory.historyList.isNotEmpty()) {
                true -> {
                    binding.clearHistoryButton.visibility = View.VISIBLE
                    binding.historyViewTitle.visibility = View.VISIBLE
                    trackViewAdapter.replaceList(SearchHistory.historyList)
                }

                false -> {
                    binding.clearHistoryButton.visibility = View.GONE
                    binding.historyViewTitle.visibility = View.GONE
                    trackViewAdapter.replaceList(searchResultsList)
                }
            }
        }
        binding.findField.addTextChangedListener(simpleTextWatcher)
        binding.findField.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchTrack()
            }
            false
        }
        binding.placeholderRefreshButton.setOnClickListener {
            searchTrack()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.findField.setSelection(binding.findField.length())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SAVED_TEXT_KEY, savedInputInFindView)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {

        super.onRestoreInstanceState(savedInstanceState)
        savedInputInFindView = savedInstanceState.getString(SAVED_TEXT_KEY, DEFAULT_TEXT)
        binding.findField.setText(savedInputInFindView)
    }
}