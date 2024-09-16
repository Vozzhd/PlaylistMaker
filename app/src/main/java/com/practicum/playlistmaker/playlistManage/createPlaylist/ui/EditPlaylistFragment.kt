package com.practicum.playlistmaker.playlistManage.createPlaylist.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.FragmentEditPlaylistBinding
import com.practicum.playlistmaker.playlistManage.createPlaylist.domain.entity.Playlist
import com.practicum.playlistmaker.playlistManage.createPlaylist.ui.model.EditPlaylistViewModel
import com.practicum.playlistmaker.utilities.KEY_FOR_PLAYLIST
import org.koin.androidx.viewmodel.ext.android.viewModel


class EditPlaylistFragment : Fragment() {
    private var _binding: FragmentEditPlaylistBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun createArgs(playlist: Playlist): Bundle {
            return bundleOf(
                KEY_FOR_PLAYLIST to playlist
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditPlaylistBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: EditPlaylistViewModel by viewModel()

        val playlist = requireArguments().get(KEY_FOR_PLAYLIST) as Playlist

        binding.albumNameFrame.editText?.setText(playlist.name)
        binding.description.setText(playlist.description)
        binding.playlistCover.setImageURI(playlist.sourceOfPlaylistCoverImage)

        var nameForEdit = playlist.name
        var descriptionForEdit = playlist.description

        saveButtonState(binding.name.text)

        val playlistNameTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                nameForEdit = text.toString()
                saveButtonState(text)
                frameAndTextColorsState(binding.albumNameFrame, text)
                viewModel.setPlaylistName(text)
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        val playlistDescriptionTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setPlaylistDescription(text)
                descriptionForEdit = text.toString()
                frameAndTextColorsState(binding.descriptionNameFrame, text)
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    binding.playlistCover.setImageURI(uri)
                    viewModel.setUri(uri)
                    playlist.sourceOfPlaylistCoverImage = uri
                }
            }

        binding.playlistCover.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }



        binding.name.addTextChangedListener(playlistNameTextWatcher)
        binding.description.addTextChangedListener(playlistDescriptionTextWatcher)
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.saveButton.setOnClickListener {
            viewModel.saveChangesToPlaylist(
                Playlist(
                    playlist.playlistId,
                    nameForEdit,
                    descriptionForEdit,
                    playlist.trackQuantity,
                    playlist.sourceOfPlaylistCoverImage
                )
            )
            findNavController().popBackStack()
        }
    }


    private fun saveButtonState(text: CharSequence?) {
        binding.saveButton.isEnabled = !text.isNullOrEmpty()
    }

    private fun frameAndTextColorsState(frame: TextInputLayout, text: CharSequence?) {
        if (!text.isNullOrEmpty()) {
            frame.defaultHintTextColor =
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.YP_blue
                    )
                )

            frame.setBoxStrokeColorStateList(
                ColorStateList(
                    arrayOf(
                        intArrayOf(-android.R.attr.state_enabled),
                        intArrayOf(android.R.attr.state_hovered, android.R.attr.state_enabled),
                        intArrayOf(android.R.attr.state_focused, android.R.attr.state_enabled)
                    ),
                    intArrayOf(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.YP_blue
                        ),
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.YP_text_gray
                        ),
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.YP_blue
                        )
                    )
                )
            )
        } else {
            frame.defaultHintTextColor =
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.YP_text_gray
                    )
                )
            frame.hintTextColor = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.YP_blue
                )
            )
            frame.setBoxStrokeColorStateList(
                ColorStateList(
                    arrayOf(
                        intArrayOf(-android.R.attr.state_enabled),
                        intArrayOf(android.R.attr.state_hovered, android.R.attr.state_enabled),
                        intArrayOf(android.R.attr.state_focused, android.R.attr.state_enabled)
                    ),
                    intArrayOf(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.YP_text_gray
                        ),
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.YP_text_gray
                        ),
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.YP_blue
                        )
                    )
                )
            )
        }
    }


}