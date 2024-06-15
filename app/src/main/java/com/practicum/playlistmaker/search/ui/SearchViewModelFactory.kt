package com.practicum.playlistmaker.search.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
class SearchViewModelFactory (private val context: Context) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            return SearchViewModel(context) as T
        }
    }