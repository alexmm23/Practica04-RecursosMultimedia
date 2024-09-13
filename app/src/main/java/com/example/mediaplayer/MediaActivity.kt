package com.example.mediaplayer

import android.annotation.SuppressLint
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.io.IOException
import java.util.logging.Handler

class MediaActivity : AppCompatActivity() {
    private var play: Button? = null
    private var stop: Button? = null
    private var forward: Button? = null
    private var back: Button? = null
    private var mediaPlayer: MediaPlayer? = null
    private var txtSongName: TextView? = null
    private var txtTimeLeft: TextView? = null
    private lateinit var handler: android.os.Handler

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)
        play = findViewById(R.id.btnPlay)
        stop = findViewById(R.id.btnStop)
        forward = findViewById(R.id.btnForward)
        back = findViewById(R.id.btnBack)
        txtSongName = findViewById(R.id.txtSongName)
        txtTimeLeft = findViewById(R.id.txtTimeLeft)
        txtSongName!!.text = "Reproduciendo: "
        txtTimeLeft!!.text = "Tiempo transcurrido 00:00"
        mediaPlayer = MediaPlayer()
        play!!.setOnClickListener {
            if(mediaPlayer == null){
                mediaPlayer = MediaPlayer()
            }
            if(mediaPlayer!!.isPlaying)return@setOnClickListener
            handler = android.os.Handler()
            handler.post(updateTimeTask)
            mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
            val filePath = "android.resource://" + packageName + "/" + R.raw.memories
            val uri = Uri.parse(filePath)
            txtSongName!!.text = "Reproduciendo: Memories - Conan Gray"
            try {
                mediaPlayer!!.setDataSource(this@MediaActivity, uri)
                mediaPlayer!!.trackInfo.forEach {
                    Log.d("trackingInfo", it.toString())
                }
                mediaPlayer!!.prepare()
                mediaPlayer!!.start()
                Toast.makeText(this@MediaActivity, "Comienza reproduccion", Toast.LENGTH_LONG)
                    .show()
            } catch (exception: IOException) {
                Toast.makeText(this@MediaActivity, "Error al reproducir", Toast.LENGTH_LONG).show()
                println(exception)
            }
        }
        back!!.setOnClickListener {
            if (mediaPlayer == null) {
                Toast.makeText(this, "Inicia la canción primero!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (mediaPlayer!!.currentPosition >= 0) {
                mediaPlayer!!.pause()
                mediaPlayer!!.seekTo(mediaPlayer!!.currentPosition - 5000)
                mediaPlayer!!.start()
            } else {
                mediaPlayer!!.pause()
                mediaPlayer!!.seekTo(1)
                mediaPlayer!!.start()
            }

        }
        forward!!.setOnClickListener {
            if (mediaPlayer == null) {
                Toast.makeText(this, "Inicia la canción primero!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (mediaPlayer!!.currentPosition + 1000 >= mediaPlayer!!.duration) {
                mediaPlayer!!.pause()
                mediaPlayer!!.seekTo(0)
                mediaPlayer!!.start()
            } else {
                mediaPlayer!!.pause()
                mediaPlayer!!.seekTo(mediaPlayer!!.currentPosition + 1000)
                mediaPlayer!!.start()
            }
        }

        stop!!.setOnClickListener {
            if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
                mediaPlayer!!.stop()
                mediaPlayer = null
                handler.removeCallbacks(updateTimeTask) // Detener el Runnable
                Toast.makeText(this@MediaActivity, "Se detiene reproduccion", Toast.LENGTH_LONG)
                    .show()
            }
        }

        mediaPlayer!!.setOnPreparedListener {
            mediaPlayer!!.start()
            handler.post(updateTimeTask)
        }

    }

    private val updateTimeTask = object : Runnable {
        override fun run() {
            if (mediaPlayer!!.duration >= mediaPlayer!!.currentPosition) {
                val currentPosition = mediaPlayer!!.currentPosition
                txtTimeLeft!!.text = "Tiempo transcurrido: " + formatTime(currentPosition)
                handler.postDelayed(this, 1000)
            }
        }

    }

    fun formatTime(millseconds: Int): String {
        val minutes = (millseconds / 1000) / 60
        val seconds = (millseconds / 1000) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer!!.isPlaying) {
            mediaPlayer!!.stop()
        }
        handler.removeCallbacks(updateTimeTask) // Detener el Runnable
        mediaPlayer!!.release() // Libera el MediaPlayer
    }

}