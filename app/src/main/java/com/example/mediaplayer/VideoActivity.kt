package com.example.mediaplayer

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView

class VideoActivity : AppCompatActivity() {
    private var videoView: VideoView? = null
    private var mediaController: MediaController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        videoView = findViewById(R.id.videoView)
        mediaController = MediaController(this)
        mediaController!!.setAnchorView(videoView)

        var uri = Uri.parse("android.resource://"+packageName+"/"+R.raw.ditf)
        videoView!!.setVideoURI(uri)
        videoView!!.requestFocus()
        videoView!!.start()
        Toast.makeText(this, "Comienza video", Toast.LENGTH_LONG).show()
        videoView!!.setMediaController(mediaController)
    }
}