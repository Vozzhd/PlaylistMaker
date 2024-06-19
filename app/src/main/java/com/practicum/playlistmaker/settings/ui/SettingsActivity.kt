package com.practicum.playlistmaker.settings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.practicum.playlistmaker.utilities.App
import com.practicum.playlistmaker.databinding.ActivitySettingsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsActivity : Fragment() {

    private lateinit var binding: ActivitySettingsBinding
    private val viewModel by viewModel<SettingsViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivitySettingsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)


        binding.shareButton.setOnClickListener {
            viewModel.shareApp()
        }
        viewModel.getThemeSettingsLiveData()

        binding.supportButton.setOnClickListener {
            viewModel.sendEmailToSupport()
        }
        binding.userAgreementButton.setOnClickListener {
            viewModel.showUserAgreement()
        }

        viewModel.getThemeSettingsLiveData().observe(viewLifecycleOwner, Observer {
            binding.themeSwitcher.isChecked = it.darkTheme
        })


        binding.themeSwitcher.setOnCheckedChangeListener { _, checked ->
            viewModel.switchTheme(checked)
        }


    }
}