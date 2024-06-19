package com.practicum.playlistmaker.mediaLibrary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.MediaLibraryFragmentBinding

class MediaLibraryFragment : Fragment() {

    private lateinit var binding: MediaLibraryFragmentBinding
    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = MediaLibraryFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewPager.adapter = MediaLibraryPagerAdapter(childFragmentManager, lifecycle)

        tabLayoutMediator =
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = resources.getString(R.string.favoriteTracks)
                    1 -> tab.text = resources.getString(R.string.Playlists)
                }
            }
        tabLayoutMediator.attach()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        tabLayoutMediator.detach()
    }
}