package com.practicum.playlistmaker.mediaLibrary.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.ActivityMediaLibraryBinding

class MediaLibraryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMediaLibraryBinding
    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaLibraryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = MediaLibraryPagerAdapter(supportFragmentManager, lifecycle)
        tabLayoutMediator =
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                when (position) {
                    0 ->tab.text = resources.getString(R.string.favoriteTracks)
                    1 ->tab.text = resources.getString(R.string.Playlists)
                }
            }
        tabLayoutMediator.attach()

        binding.backButton.setOnClickListener{
            finish()
        }
    }

    override fun onDestroy() {
        tabLayoutMediator.detach()
        super.onDestroy()
    }
}