package com.practicum.playlistmaker.mediaLibrary.mediaLibraryRoot

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.practicum.playlistmaker.mediaLibrary.favorite.ui.FavoriteFragment
import com.practicum.playlistmaker.mediaLibrary.playlist.ui.PlaylistFragment

class MediaLibraryPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) FavoriteFragment.newInstance() else PlaylistFragment.newInstance()
    }
}