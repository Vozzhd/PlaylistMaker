package com.practicum.playlistmaker.mediaLibrary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.practicum.playlistmaker.databinding.PlaylistFragmentBinding

class PlaylistFragment : Fragment() {

    companion object {
        fun newInstance() = PlaylistFragment().apply { }
    }

    private lateinit var binding: PlaylistFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PlaylistFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

}