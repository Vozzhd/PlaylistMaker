package com.practicum.playlistmaker.creator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.practicum.playlistmaker.main.data.MainMenuRepositoryImpl
import com.practicum.playlistmaker.main.domain.api.MainMenuInteractor
import com.practicum.playlistmaker.main.domain.api.MainMenuRepository
import com.practicum.playlistmaker.main.domain.useCase.MainMenuInteractorImpl
import com.practicum.playlistmaker.search.data.api.TracksRepositoryImplementation
import com.practicum.playlistmaker.player.data.MediaPlayerRepositoryImpl
import com.practicum.playlistmaker.search.domain.api.TracksRepository
import com.practicum.playlistmaker.player.domain.interactor.MediaPlayerInteractorImpl
import com.practicum.playlistmaker.search.domain.api.HistoryRepository
import com.practicum.playlistmaker.search.data.local.HistoryRepositoryImplementation
import com.practicum.playlistmaker.search.data.network.retrofit.RetrofitNetworkClient
import com.practicum.playlistmaker.search.domain.api.HistoryInteractor
import com.practicum.playlistmaker.search.domain.api.TracksInteractor
import com.practicum.playlistmaker.search.domain.useCase.HistoryInteractorImplementation
import com.practicum.playlistmaker.search.domain.useCase.TracksInteractorImplementation
import com.practicum.playlistmaker.settings.domain.useCase.ExternalNavigatorImpl
import com.practicum.playlistmaker.settings.data.SettingsRepositoryImpl
import com.practicum.playlistmaker.settings.domain.api.SharingRepository
import com.practicum.playlistmaker.settings.data.SharingRepositoryImpl
import com.practicum.playlistmaker.settings.domain.api.ExternalNavigator
import com.practicum.playlistmaker.settings.domain.api.SettingsInteractor
import com.practicum.playlistmaker.settings.domain.api.SettingsRepository
import com.practicum.playlistmaker.settings.domain.api.SharingInteractor
import com.practicum.playlistmaker.settings.domain.useCase.SettingsInteractorImpl
import com.practicum.playlistmaker.settings.domain.useCase.SharingInteractorImpl
import com.practicum.playlistmaker.utilities.SAVED_HISTORY_KEY
import com.practicum.playlistmaker.utilities.SAVED_THEME_CONDITION


object Creator {

    fun provideMediaPlayer(): MediaPlayerInteractorImpl {
        return MediaPlayerInteractorImpl(provideMediaPlayerRepository())
    }

    private fun provideMediaPlayerRepository(): MediaPlayerRepositoryImpl {
        return MediaPlayerRepositoryImpl()
    }
    private fun provideTracksRepository(context: Context): TracksRepository {
        return TracksRepositoryImplementation(RetrofitNetworkClient(context))
    }
    fun provideTracksInteractor(context: Context): TracksInteractor {
        return TracksInteractorImplementation(provideTracksRepository(context))
    }
    fun provideTrackHistoryInteractor(context: Context): HistoryInteractor {
        return HistoryInteractorImplementation(provideTrackHistoryRepository(context))
    }
    private fun provideTrackHistoryRepository(context: Context): HistoryRepository {
        return HistoryRepositoryImplementation(
            context.getSharedPreferences(
                SAVED_HISTORY_KEY,
                AppCompatActivity.MODE_PRIVATE
            )
        )
    }

    fun provideSharingInteractor(context: Context) : SharingInteractor {
        return SharingInteractorImpl(provideExternalNavigator(context), provideSharingReporitory(context))
    }
    private fun provideExternalNavigator(context: Context) : ExternalNavigator{
        return ExternalNavigatorImpl(context)
    }
    private fun provideSharingReporitory(context: Context) : SharingRepository {
        return SharingRepositoryImpl(context)
    }

    fun provideSettingInteractor(context: Context) : SettingsInteractor {
        return SettingsInteractorImpl(provideSettingsRepository(context))
    }
    private fun provideSettingsRepository(context: Context) : SettingsRepository {
        return SettingsRepositoryImpl(context.getSharedPreferences(SAVED_THEME_CONDITION, AppCompatActivity.MODE_PRIVATE))
    }

    fun provideMainMenuInteractor(context: Context) : MainMenuInteractor {
        return MainMenuInteractorImpl(provideMainMenuRepository(context))
    }
    private fun provideMainMenuRepository(context: Context): MainMenuRepository {
        return MainMenuRepositoryImpl(context)
    }

}