package com.practicum.playlistmaker.settings.data

import android.content.Context
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.settings.domain.model.EmailData
import com.practicum.playlistmaker.settings.domain.api.SharingRepository

class SharingRepositoryImpl (private val context : Context) : SharingRepository {
    override fun getShareAppLink(): String {
       return context.getString(R.string.supportLetterSubject)
    }

    override fun getTermsLink(): String {
        return context.getString(R.string.linkToTermsOfUse)
    }

    override fun getSupportEmailData(): EmailData {
        return EmailData(
            subject = context.getString(R.string.supportLetterAddressee),
            message =context.getString(R.string.supportLetterText),
            addressee = context.getString(R.string.supportLetterAddressee)
        )
    }
}