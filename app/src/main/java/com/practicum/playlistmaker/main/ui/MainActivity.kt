package com.practicum.playlistmaker.main.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmaker.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel by viewModel<MainViewModel>()

        binding.findButton.setOnClickListener {
            viewModel.startSearchActivity()
        }

        binding.libraryButton.setOnClickListener {
            viewModel.startMediaLibraryActivity()

        }

        binding.settingsButton.setOnClickListener {
            viewModel.startSettingsActivity()
        }
    }
}