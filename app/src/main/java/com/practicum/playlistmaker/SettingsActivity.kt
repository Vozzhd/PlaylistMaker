package com.practicum.playlistmaker
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backToMainButton = findViewById<ImageButton>(R.id.backButton)
        backToMainButton.setOnClickListener{
            finish()
        }

        val shareButton = findViewById<ImageView>(R.id.shareButton)
        shareButton.setOnClickListener{
            val intent = Intent()
            intent.action=Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, "https://practicum.yandex.ru/");
            intent.type="application/octet-stream"
            startActivity(Intent.createChooser(intent,"share to:"))
        }
    }
}