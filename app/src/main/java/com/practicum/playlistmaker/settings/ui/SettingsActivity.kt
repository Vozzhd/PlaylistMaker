package com.practicum.playlistmaker.settings.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmaker.utilities.App
import com.practicum.playlistmaker.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var viewModel: SettingsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySettingsBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, SettingsViewModelFactory(application))[SettingsViewModel::class.java]

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.shareButton.setOnClickListener {
            viewModel.shareApp()
        }

        binding.themeSwitcher.isChecked = (applicationContext as App).darkTheme

        binding.supportButton.setOnClickListener{
            viewModel.sendEmailToSupport()
        }
        binding.userAgreementButton.setOnClickListener{
            viewModel.showUserAgreement()
        }

        binding.themeSwitcher.isChecked = (applicationContext as App).darkTheme

        viewModel.getThemeSettingsLiveData().observe(this) {
            (applicationContext as App).switchTheme(it.darkTheme)
        }
        binding.themeSwitcher.setOnCheckedChangeListener { _, checked ->
            viewModel.switchTheme(checked)
        }
    }
}