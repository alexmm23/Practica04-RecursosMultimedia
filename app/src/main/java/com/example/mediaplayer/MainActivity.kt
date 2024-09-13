package com.example.mediaplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonMusic = findViewById<Button>(R.id.btnMenu2)
        val botonMedia = findViewById<Button>(R.id.btnMenu1)
        val botonVideo = findViewById<Button>(R.id.btnMenu3)


        botonMedia.setOnClickListener { v: View ->
            val intent = Intent(applicationContext, MediaActivity::class.java)
            startActivity(intent)
        }
        botonVideo.setOnClickListener { v:View?->
            val intent = Intent(applicationContext, VideoActivity::class.java)
            startActivity(intent)
        }
    }
}