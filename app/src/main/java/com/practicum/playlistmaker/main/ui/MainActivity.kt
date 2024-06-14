package com.practicum.playlistmaker.main.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmaker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, MainViewModelFactory(this))[MainViewModel::class.java]

        binding.findButton.setOnClickListener {
            viewModel.startSearchActivity()
        }

        binding.libraryButton.setOnClickListener{
            viewModel.startMediaLibraryActivity()
        }

        binding.settingsButton.setOnClickListener{
            viewModel.startSettingsActivity()
        }
    }
}