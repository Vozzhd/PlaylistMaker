package com.practicum.playlistmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView

class SearchActivity : AppCompatActivity() {
    private var savedInputInFindView: String = DEFAULT_TEXT

    companion object {
        const val SAVED_TEXT_KEY = "INPUT_IN_FIND_FIELD"
        const val DEFAULT_TEXT = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        if (savedInstanceState != null) {
            savedInputInFindView = savedInstanceState.getString(SAVED_TEXT_KEY, DEFAULT_TEXT)
        }

        val inputEditText = findViewById<EditText>(R.id.findField)
        val clearButton = findViewById<ImageView>(R.id.clearButton)

        inputEditText.setText(savedInputInFindView)

        clearButton.setOnClickListener {
            inputEditText.setText("")
        }

        fun clearButtonVisibility(s: CharSequence?): Int {
            if (s.isNullOrEmpty()) {
                return View.GONE
            } else {
                return View.VISIBLE
            }
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                clearButton.visibility = clearButtonVisibility(p0)
                savedInputInFindView = inputEditText.text.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
                // empty
            }

        }
        inputEditText.addTextChangedListener(simpleTextWatcher)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SAVED_TEXT_KEY, savedInputInFindView)
    }
}