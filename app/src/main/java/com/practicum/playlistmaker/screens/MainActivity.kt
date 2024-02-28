package com.practicum.playlistmaker.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import com.practicum.playlistmaker.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchButton = findViewById<MaterialButton>(R.id.findButton)
        searchButton.setOnClickListener { startActivity(Intent(this, SearchActivity::class.java)) }

        val libraryButton = findViewById<MaterialButton>(R.id.libraryButton)
        libraryButton.setOnClickListener { startActivity(Intent(this, LibraryActivity::class.java)) }

        val settingsButton = findViewById<MaterialButton>(R.id.settingsButton)
        settingsButton.setOnClickListener { startActivity(Intent(this, SettingsActivity::class.java)) }
    }
}