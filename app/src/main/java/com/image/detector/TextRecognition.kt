package com.image.detector

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.showmewhat.R
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import java.io.IOException

class TextRecognition : AppCompatActivity() {
    private var btnPhoto: ImageButton? = null
    private var photo: ImageView? = null
    private var imageText: EditText? = null
    private var imageBitmap: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_recognition)
        btnPhoto = findViewById(R.id.btnPhoto)
        photo = findViewById(R.id.photo)
        imageText = findViewById(R.id.imageText)
        btnPhoto?.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_REQUEST_CODE)
        }
    }

    private fun scanText(uri: Uri?) {
        try {
            val image = FirebaseVisionImage.fromFilePath(this@TextRecognition, uri!!)
            val textRecognizer = FirebaseVision.getInstance().onDeviceTextRecognizer
            textRecognizer.processImage(image)
                .addOnSuccessListener { firebaseVisionText ->
                    imageText!!.setText(firebaseVisionText.text)
                    Toast.makeText(this@TextRecognition, "Text converted", Toast.LENGTH_SHORT)
                        .show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        this@TextRecognition,
                        e.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == PICK_REQUEST_CODE) {
            val selectedImage = data!!.data
            photo!!.setImageURI(selectedImage)
            scanText(selectedImage)
        } else if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {
            val extras = data!!.extras
            imageBitmap = extras!!["data"] as Bitmap?
            photo!!.setImageBitmap(imageBitmap)
        }
    }

    companion object {
        private const val PICK_REQUEST_CODE = 0
        private const val CAMERA_REQUEST_CODE = 1
    }
}