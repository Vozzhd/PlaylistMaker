package com.practicum.playlistmaker.search.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
class SearchViewModelFactory (application: Application) : ViewModelProvider.Factory {

        private val application by lazy (LazyThreadSafetyMode.NONE){ application }

        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            return SearchViewModel(application) as T
        }
    }