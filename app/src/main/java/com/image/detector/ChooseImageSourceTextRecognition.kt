package com.image.detector

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.showmewhat.R

class ChooseImageSourceTextRecognition : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_convert_choise)
        val btnGallery = findViewById<Button>(R.id.btnGallery)
        val btnCamera = findViewById<Button>(R.id.btnCamera)
        btnGallery.setOnClickListener {
            startActivity(
                Intent(
                    this@ChooseImageSourceTextRecognition,
                    TextRecognition::class.java
                )
            )
        }
        btnCamera.setOnClickListener {
            startActivity(
                Intent(
                    this@ChooseImageSourceTextRecognition,
                    CameraTextRecognition::class.java
                )
            )
        }
    }
}