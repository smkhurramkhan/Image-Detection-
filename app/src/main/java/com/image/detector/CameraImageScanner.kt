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
import com.example.showmewhat.R
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage

class CameraImageScanner : AppCompatActivity() {
    private var snapBtn: Button? = null
    private var detectBtn: Button? = null
    private var imageView: ImageView? = null
    private var txtView: EditText? = null
    private var imageBitmap: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        snapBtn = findViewById(R.id.snapBtn)
        detectBtn = findViewById(R.id.detectBtn)
        imageView = findViewById(R.id.imageView)
        txtView = findViewById(R.id.txtView)
        snapBtn?.setOnClickListener { dispatchTakePictureIntent() }
        detectBtn?.setOnClickListener { labelImage() }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_CODE_PICK)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK && resultCode == RESULT_OK) {
            val extras = data!!.extras
            imageBitmap = extras!!["data"] as Bitmap?
            imageView!!.setImageBitmap(imageBitmap)
        }
    }

    private fun labelImage() {
        val image = FirebaseVisionImage.fromBitmap(imageBitmap!!)
        val labeler = FirebaseVision.getInstance().onDeviceImageLabeler
        labeler.processImage(image).addOnSuccessListener { firebaseVisionImageLabels ->
            if (firebaseVisionImageLabels.isEmpty()) {
                txtView!!.setText("No tags detected")
            } else {
                val sb = StringBuilder(
                    """
    Recognized tags: 
    
    """.trimIndent()
                )
                for (i in 1..firebaseVisionImageLabels.size) {
                    sb.append(
                        """
    $i. ${firebaseVisionImageLabels[i - 1].text}
    
    """.trimIndent()
                    )
                }
                txtView!!.setText(sb.toString())
                imageView!!.setImageBitmap(imageBitmap)
            }
        }
            .addOnFailureListener {
                Toast.makeText(
                    this@CameraImageScanner,
                    "Failed to recognize image", Toast.LENGTH_SHORT
                ).show()
            }
    }

    companion object {
        private const val REQUEST_CODE_PICK = 0
    }
}