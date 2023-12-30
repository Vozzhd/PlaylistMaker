package com.practicum.playlistmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val findButton = findViewById<MaterialButton>(R.id.findButton)
        val imageClickListener: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(this@MainActivity, "Нажали на кнопку ПОИСК!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        findButton.setOnClickListener(imageClickListener)


        val libraryButton = findViewById<MaterialButton>(R.id.libraryButton)
        libraryButton.setOnClickListener {
            Toast.makeText(this@MainActivity, "Нажали на кнопку МЕДИАТЕКА!", Toast.LENGTH_SHORT)
                .show()
        }

        val settingsButton = findViewById<MaterialButton>(R.id.settingsButton)
        settingsButton.setOnClickListener {
            Toast.makeText(this@MainActivity, "Нажали на кнопку НАСТРОЙКИ!", Toast.LENGTH_SHORT)
                .show()
        }

    }
}