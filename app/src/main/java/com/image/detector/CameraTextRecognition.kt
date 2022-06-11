package com.image.detector

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.image.detector.R
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText

class CameraTextRecognition : AppCompatActivity() {
    private var imageView: ImageView? = null
    private var txtView: EditText? = null
    private var imageBitmap: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        val snapBtn = findViewById<Button>(R.id.snapBtn)
        val detectBtn = findViewById<Button>(R.id.detectBtn)
        imageView = findViewById(R.id.imageView)
        txtView = findViewById(R.id.txtView)
        snapBtn.setOnClickListener { dispatchTakePictureIntent() }
        detectBtn.setOnClickListener { detectTxt() }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val extras = data!!.extras
            imageBitmap = extras!!["data"] as Bitmap?
            imageView!!.setImageBitmap(imageBitmap)
        }
    }

    private fun detectTxt() {
        val image = FirebaseVisionImage.fromBitmap(imageBitmap!!)
        val detector = FirebaseVision.getInstance().onDeviceTextRecognizer
        detector.processImage(image)
            .addOnSuccessListener { firebaseVisionText -> processTxt(firebaseVisionText) }
            .addOnFailureListener { }
    }

    private fun processTxt(text: FirebaseVisionText) {
        val blocks = text.textBlocks
        if (blocks.size == 0) {
            Toast.makeText(this@CameraTextRecognition, "No Text Found", Toast.LENGTH_LONG).show()
            return
        }
        for (block in text.textBlocks) {
            val txt = block.text
            txtView?.textSize = 24f
            txtView?.setText(txt)
        }
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
    }
}