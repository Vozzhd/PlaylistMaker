package com.practicum.playlistmaker.screens

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.ItunesAPI
import com.practicum.playlistmaker.PlaceholderState
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.Track
import com.practicum.playlistmaker.adapters.TrackAdapter
import com.practicum.playlistmaker.data.TrackResponse
import com.practicum.playlistmaker.hideKeyboard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {
    private var savedInputInFindView: String = DEFAULT_TEXT
    private lateinit var inputEditText: EditText
    private lateinit var placeholderMessage: TextView

    private val itunesBaseUrl = "https://itunes.apple.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(itunesBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val itunesService = retrofit.create(ItunesAPI::class.java)
    private val trackList = mutableListOf<Track>() //уйти от этого в будущем
    private val historyList = mutableListOf<Track>(
        Track("trackNameTest1", "ArtistNameTest1", "124220", "nothing", 223),
        Track("trackNameTest2", "ArtistNameTest2", "124141", "nothing", 2322),
        Track("trackNameTest3", "ArtistNameTest3", "232424", "nothing", 1123),
        Track("trackNameTest4", "ArtistNameTest4", "1123445", "nothing", 5552)
    )

    companion object {
        const val SAVED_TEXT_KEY = "SAVED_TEXT_KEY"
        const val DEFAULT_TEXT = ""

        const val SAVE_HISTORY_DIRECTORY = "Saved history"
        const val SAVE_HISTORY_KEY = "Saved history key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferencesForSearchHistory: SharedPreferences =
            getSharedPreferences(SAVE_HISTORY_DIRECTORY, MODE_PRIVATE) //??
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val recyclerViewTracks = findViewById<RecyclerView>(R.id.recyclerViewTracks)
        val historyViewAdapter = TrackAdapter(historyList, sharedPreferencesForSearchHistory) //??
        val trackViewAdapter = TrackAdapter(
            trackList,
            sharedPreferencesForSearchHistory
        ) //Убрать, использовать адаптер напрямую

        inputEditText = findViewById(R.id.findField)
        placeholderMessage = findViewById(R.id.placeholderErrorMessage)
        val inputEditText = findViewById<EditText>(R.id.findField)
        val clearButton = findViewById<ImageView>(R.id.clearButton)
        val placeholderErrorLayout = findViewById<FrameLayout>(R.id.placeholderErrorLayout)
        val errorImage = findViewById<ImageView>(R.id.placeholderErrorImage)
        val refreshButton = findViewById<Button>(R.id.placeholderRefreshButton)
        val historyTitleText = findViewById<TextView>(R.id.historyViewTitle)
        val clearHistoryButton = findViewById<Button>(R.id.clearHistoryButton)

        recyclerViewTracks.adapter = trackViewAdapter

        clearButton.setOnClickListener {
            inputEditText.setText("")
            inputEditText.hideKeyboard()
            trackList.clear()
            trackViewAdapter.notifyDataSetChanged()
            placeholderErrorLayout.visibility = View.GONE
        }

        fun getClearButtonVisibility(textInView: CharSequence?): Int {
            return if (textInView.isNullOrEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }

        fun showMessage(text: String) {
            if (text.isNotEmpty()) {
                trackList.clear()
                trackViewAdapter.notifyDataSetChanged()
                placeholderMessage.text = text
            } else {
                placeholderMessage.text = "Описание ошибки отсутствует"
            }
        }

        fun setPlaceholderState(placeholderState: PlaceholderState) {
            when (placeholderState) {
                PlaceholderState.GOOD -> {
                    placeholderErrorLayout.visibility = View.GONE
                }

                PlaceholderState.BAD_REQUEST -> {
                    showMessage(getString(R.string.nothing_found))
                    errorImage.setImageResource(R.drawable.search_error)
                    refreshButton.visibility = View.GONE
                    placeholderErrorLayout.visibility = View.VISIBLE
                }

                PlaceholderState.NO_CONNECTION -> {
                    showMessage(getString(R.string.something_went_wrong))
                    errorImage.setImageResource(R.drawable.connection_error)
                    refreshButton.visibility = View.VISIBLE
                    placeholderErrorLayout.visibility = View.VISIBLE
                }
            }
        }

        fun searchTrack() {
            if (inputEditText.text.isNotEmpty()) {
                itunesService.search(inputEditText.text.toString())
                    .enqueue(object : Callback<TrackResponse> {
                        override fun onResponse(
                            call: Call<TrackResponse>,
                            response: Response<TrackResponse>
                        ) {
                            var checkedResponseBody: List<Track> = mutableListOf()

                            if (response.body()?.results != null) {
                                checkedResponseBody = response.body()?.results!!
                            }

                            if (response.code() == 200) {
                                trackList.clear()
                                if (checkedResponseBody.isNotEmpty()) {
                                    trackList.addAll(checkedResponseBody)
                                    trackViewAdapter.notifyDataSetChanged()
                                }
                                if (trackList.isEmpty()) {
                                    setPlaceholderState(PlaceholderState.BAD_REQUEST)
                                } else {
                                    setPlaceholderState(PlaceholderState.GOOD)
                                }
                            } else {
                                showMessage(getString(R.string.something_went_wrong))
                            }
                        }

                        override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                            setPlaceholderState(PlaceholderState.NO_CONNECTION)
                        }
                    })
            }
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(textInView: CharSequence?, p1: Int, p2: Int, p3: Int) {
                clearHistoryButton.visibility = if (inputEditText.hasFocus() && textInView?.isEmpty() == true) View.VISIBLE else View.GONE
                historyTitleText.visibility = if (inputEditText.hasFocus() && textInView?.isEmpty() == true) View.VISIBLE else View.GONE
                clearButton.visibility = getClearButtonVisibility(textInView)
                savedInputInFindView = inputEditText.text.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
                // empty
            }
        }

        inputEditText.setOnFocusChangeListener { view, hasFocus ->
            clearHistoryButton.visibility = if (hasFocus && inputEditText.text.isEmpty()) View.VISIBLE else View.GONE
            historyTitleText.visibility = if (hasFocus && inputEditText.text.isEmpty()) View.VISIBLE else View.GONE
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)
        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchTrack()
            }
            false
        }
        refreshButton.setOnClickListener {
            searchTrack()
        }
    }

    override fun onResume() {
        super.onResume()
        inputEditText.setSelection(inputEditText.length())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SAVED_TEXT_KEY, savedInputInFindView)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {

        super.onRestoreInstanceState(savedInstanceState)
        savedInputInFindView = savedInstanceState.getString(SAVED_TEXT_KEY, DEFAULT_TEXT)

        val inputEditText = findViewById<EditText>(R.id.findField)
        inputEditText.setText(savedInputInFindView)
    }
}