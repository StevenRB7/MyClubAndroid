package com.myclub.myapplication.Actvity


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.myclub.myapplication.databinding.ActivityGeneradorQractivityBinding
import net.glxn.qrgen.android.QRCode
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.collections.ArrayList


class GeneradorQRActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGeneradorQractivityBinding
    private var texto: String? = null
    private lateinit var idVacher: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeneradorQractivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (intent.extras != null) {
            idVacher = intent.getSerializableExtra("IdVaucher").toString()
            generarQR()

        }

        botones()



    }

    private fun botones() {
        binding.btnregresarqr.setOnClickListener {
            onBackPressed()

        }
        binding.idBtnsalirqr.setOnClickListener {
            onBackPressed()

        }
    }

    fun generarQR() {
        if (TextUtils.isEmpty(idVacher)) {
        } else {
            val bitmap = QRCode.from(idVacher).withSize(1000,1000).bitmap()
            binding.ivQRCode.setImageBitmap(bitmap)
        }
    }



}