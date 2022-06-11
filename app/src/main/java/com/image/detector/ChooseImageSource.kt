package com.image.detector

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.image.detector.R

class ChooseImageSource : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_convert_choise_image)
        val btnGallery = findViewById<Button>(R.id.btnGallery)
        val btnCamera = findViewById<Button>(R.id.btnCamera)
        btnGallery.setOnClickListener {
            startActivity(
                Intent(
                    this@ChooseImageSource,
                    MainActivity::class.java
                )
            )
        }
        btnCamera.setOnClickListener {
            startActivity(
                Intent(
                    this@ChooseImageSource,
                    CameraImageScanner::class.java
                )
            )
        }
    }
}