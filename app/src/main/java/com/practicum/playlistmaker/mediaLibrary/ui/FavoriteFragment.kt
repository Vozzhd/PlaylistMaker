package com.practicum.playlistmaker.mediaLibrary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.practicum.playlistmaker.databinding.FavoriteFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.practicum.playlistmaker.mediaLibrary.ui.viewModel.FavoriteFragmentViewModel

class FavoriteFragment : Fragment() {

    companion object {
        fun newInstance() = FavoriteFragment().apply { }
    }

    private var _binding: FavoriteFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteFragmentViewModel by viewModel()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}