package com.practicum.playlistmaker.mediaLibrary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.PlaylistFragmentBinding
import com.practicum.playlistmaker.mediaLibrary.ui.viewModel.FavoriteFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistFragment : Fragment() {

    companion object {
        fun newInstance() = PlaylistFragment().apply { }
    }

    private var _binding: PlaylistFragmentBinding? =null
    private val binding get() = _binding!!

    private val viewModel: FavoriteFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PlaylistFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.newPlaylist.setOnClickListener{
            view.findNavController().navigate(R.id.action_mediaLibraryFragment_to_newPlaylistFragment)
        }
    }
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}