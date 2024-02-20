package com.practicum.playlistmaker.screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.practicum.playlistmaker.App
import com.practicum.playlistmaker.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val backToMainButton = findViewById<ImageButton>(R.id.backButton)
        backToMainButton.setOnClickListener {
            finish()
        }
        val themeSwitcher = findViewById<SwitchCompat>(R.id.themeSwitcher)
        themeSwitcher.setOnCheckedChangeListener { switcher, checked ->
            (applicationContext as App).switchTheme(checked)
        }
        themeSwitcher.isChecked = (application as App).darkTheme

        val shareButton = findViewById<FrameLayout>(R.id.shareButton)
        shareButton.setOnClickListener {
            val intent = Intent()
            val sharedLink = getString(R.string.linkInsideShareButton)
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, sharedLink)
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, getString(R.string.hintInShareWindow)))
        }

        val supportButton = findViewById<FrameLayout>(R.id.supportButton)
        supportButton.setOnClickListener {

            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:")

            val subject = getString(R.string.supportLetterSubject)
            val message = getString(R.string.supportLetterText)
            val addressee = getString(R.string.supportLetterAddressee)

            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(addressee))
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            intent.putExtra(Intent.EXTRA_TEXT, message)

            startActivity(intent)
        }

        val userAgreementButton = findViewById<FrameLayout>(R.id.userAgreementButton)
        userAgreementButton.setOnClickListener {
            val linkToTermsOfUse = getString(R.string.linkToTermsOfUse)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkToTermsOfUse))
            startActivity(intent)
        }
    }
}