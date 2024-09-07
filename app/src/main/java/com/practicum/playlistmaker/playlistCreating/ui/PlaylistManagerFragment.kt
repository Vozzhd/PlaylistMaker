package com.practicum.playlistmaker.playlistCreating.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.FragmentPlaylistManagerBinding
import com.practicum.playlistmaker.playlistCreating.ui.model.PlaylistManagerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlaylistManagerFragment : Fragment() {

    private var _binding: FragmentPlaylistManagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlaylistManagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: PlaylistManagerViewModel by viewModel()

        val albumNameTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                createButtonState(text)
                frameAndTextColorsState(binding.albumNameFrame, text)
                viewModel.setPlaylistName(text)
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        val albumDescriptionTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setPlaylistDescription(text)
                frameAndTextColorsState(binding.descriptionNameFrame, text)
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    binding.shapeableImageView.setImageURI(uri)
                    viewModel.setUri(uri)
                }
            }
        binding.shapeableImageView.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.name.addTextChangedListener(albumNameTextWatcher)
        binding.description.addTextChangedListener(albumDescriptionTextWatcher)

        binding.backButton.setOnClickListener {
            if (!viewModel.emptyCheck()) {
                dialog()
            } else {
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!viewModel.emptyCheck()) {
                        dialog()
                    } else
                        findNavController().popBackStack()
                }
            })

        binding.createButton.setOnClickListener {
            viewModel.createPlaylist()
            Toast.makeText(
                requireContext(),
                "${requireContext().getString(R.string.playlist)} ${binding.name.text} ${
                    requireContext().getString(R.string.created)
                }.",
                Toast.LENGTH_SHORT
            ).show()

            findNavController().popBackStack()
        }


    }

    private fun createButtonState(text: CharSequence?) {
        binding.createButton.isEnabled = !text.isNullOrEmpty()
    }

    fun dialog() {
        MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertText)
            .setTitle(requireContext().getString(R.string.finishCreatingPlaylist))
            .setMessage(requireContext().getString(R.string.unsavedDataWillBeLost))
            .setNegativeButton(requireContext().getString(R.string.cancel)) { _, _ -> }
            .setPositiveButton(requireContext().getString(R.string.complete)) { _, _ -> findNavController().popBackStack() }
            .show()
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