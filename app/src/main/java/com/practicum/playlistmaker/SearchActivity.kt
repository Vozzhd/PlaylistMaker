package com.practicum.playlistmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class SearchActivity : AppCompatActivity() {
    private var savedInputInFindView: String = DEFAULT_TEXT
    private lateinit var inputEditText: EditText

    companion object {
        const val SAVED_TEXT_KEY = "SAVED_TEXT_KEY"
        const val DEFAULT_TEXT = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val recyclerViewTracks = findViewById<RecyclerView>(R.id.recyclerViewTracks)
        val trackViewAdapter = TrackAdapter(takeMockPlaylist())
        recyclerViewTracks.adapter = trackViewAdapter
        inputEditText = findViewById<EditText>(R.id.findField)

        val inputEditText = findViewById<EditText>(R.id.findField)
        val clearButton = findViewById<ImageView>(R.id.clearButton)

        clearButton.setOnClickListener {
            inputEditText.setText("")
            inputEditText.hideKeyboard()

        }

        fun getClearButtonVisibility(textInView: CharSequence?): Int {
            return if (textInView.isNullOrEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(textInView: CharSequence?, p1: Int, p2: Int, p3: Int) {
                clearButton.visibility = getClearButtonVisibility(textInView)
                savedInputInFindView = inputEditText.text.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
                // empty
            }

        }
        inputEditText.addTextChangedListener(simpleTextWatcher)
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