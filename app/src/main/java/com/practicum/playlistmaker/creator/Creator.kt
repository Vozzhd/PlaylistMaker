package com.practicum.playlistmaker.creator

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.practicum.playlistmaker.main.data.MainMenuRepositoryImpl
import com.practicum.playlistmaker.main.domain.api.MainMenuInteractor
import com.practicum.playlistmaker.main.domain.api.MainMenuRepository
import com.practicum.playlistmaker.main.domain.useCase.MainMenuInteractorImpl
import com.practicum.playlistmaker.player.data.TrackMapperRepositoryImpl
import com.practicum.playlistmaker.player.domain.api.TrackMapperInteractor
import com.practicum.playlistmaker.player.domain.api.TrackMapperRepository
import com.practicum.playlistmaker.player.domain.interactor.TrackMapperInteractorImpl
import com.practicum.playlistmaker.settings.data.ExternalNavigatorImpl
import com.practicum.playlistmaker.settings.data.SettingsRepositoryImpl
import com.practicum.playlistmaker.settings.domain.api.SharingRepository
import com.practicum.playlistmaker.settings.data.SharingRepositoryImpl
import com.practicum.playlistmaker.settings.domain.api.ExternalNavigator
import com.practicum.playlistmaker.settings.domain.api.SettingsInteractor
import com.practicum.playlistmaker.settings.domain.api.SettingsRepository
import com.practicum.playlistmaker.settings.domain.api.SharingInteractor
import com.practicum.playlistmaker.settings.domain.useCase.SettingsInteractorImpl
import com.practicum.playlistmaker.settings.domain.useCase.SharingInteractorImpl
import com.practicum.playlistmaker.utilities.SHARED_PREFS


object Creator {

//    fun provideMediaPlayer(): MediaPlayerInteractorImpl {
//        return MediaPlayerInteractorImpl(provideMediaPlayerRepository())
//    }

//    private fun provideMediaPlayerRepository(): MediaPlayerRepository {
//        return MediaPlayerRepositoryImpl()
    //   }

//    private fun provideTracksRepository(context: Context): TracksRepository {
//        return TracksRepositoryImplementation(RetrofitNetworkClient(context))
//    }

//    fun provideTracksInteractor(context: Context): TracksInteractor {
//        return TracksInteractorImplementation(provideTracksRepository(context))
//    }

//    fun provideTrackHistoryInteractor(context: Context): HistoryInteractor {
//        return HistoryInteractorImplementation(provideTrackHistoryRepository(context))
//    }

//    private fun provideTrackHistoryRepository(context: Context): HistoryRepository {
//        return HistoryRepositoryImplementation(
//            context.getSharedPreferences(
//                SHARED_PREFS,
//                AppCompatActivity.MODE_PRIVATE
//            )
//        )
//    }

//    fun provideSharingInteractor(context: Context): SharingInteractor {
//        return SharingInteractorImpl(
//            provideExternalNavigator(context),
//            provideSharingReporitory(context)
//        )
//    }
//
//    private fun provideExternalNavigator(context: Context): ExternalNavigator {
//        return ExternalNavigatorImpl(context)
//    }

//    private fun provideSharingReporitory(context: Context): SharingRepository {
//        return SharingRepositoryImpl(context)
//    }

//    fun provideSettingInteractor(context: Context): SettingsInteractor {
//        return SettingsInteractorImpl(provideSettingsRepository(context))
//    }

//    private fun provideSettingsRepository(context: Context): SettingsRepository {
//        return SettingsRepositoryImpl(
//            context.getSharedPreferences(
//                SHARED_PREFS,
//                AppCompatActivity.MODE_PRIVATE
//            )
//        )
//    }

//    fun provideMainMenuInteractor(context: Context): MainMenuInteractor {
//        return MainMenuInteractorImpl(provideMainMenuRepository(context))
//    }
//
//    private fun provideMainMenuRepository(context: Context): MainMenuRepository {
//        return MainMenuRepositoryImpl(context)
//    }

    fun provideTrackMapperInteractor(): TrackMapperInteractor {
        return TrackMapperInteractorImpl(provideTrackMapperRepository())
    }

    fun provideTrackMapperRepository(): TrackMapperRepository {
        return TrackMapperRepositoryImpl()
    }

//    var application: Application? = null
//
//    fun getSharedPrefs(): SharedPreferences? {
//        return application?.getSharedPreferences(
//            SHARED_PREFS,
//            AppCompatActivity.MODE_PRIVATE
//        )
//    }
}

