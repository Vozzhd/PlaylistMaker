package com.practicum.playlistmaker.settings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.practicum.playlistmaker.databinding.SettingsFragmentBinding
import com.practicum.playlistmaker.utilities.App
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {

    private lateinit var binding: SettingsFragmentBinding
    private val viewModel by viewModel<SettingsViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SettingsFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getThemeSettings()

        binding.shareButton.setOnClickListener {
            viewModel.shareApp()
        }


        binding.supportButton.setOnClickListener {
            viewModel.sendEmailToSupport()
        }
        binding.userAgreementButton.setOnClickListener {
            viewModel.showUserAgreement()
        }


        viewModel.getThemeSettingsLiveData().observe(viewLifecycleOwner, Observer{
            (requireActivity().applicationContext as App).switchTheme(it.darkTheme)
        })

        binding.themeSwitcher.setOnCheckedChangeListener { _, checked ->
            viewModel.switchTheme(checked)
        }


    }
}