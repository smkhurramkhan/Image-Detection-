package com.image.detector

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.image.detector.R

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val btnPicture = findViewById<Button>(R.id.btnPicture)
        val btnText = findViewById<Button>(R.id.btnText)
        btnPicture.setOnClickListener {
            startActivity(
                Intent(
                    this@HomeActivity,
                    ChooseImageSource::class.java
                )
            )
        }
        btnText.setOnClickListener {
            startActivity(
                Intent(
                    this@HomeActivity,
                    ChooseImageSourceTextRecognition::class.java
                )
            )
        }
    }
}