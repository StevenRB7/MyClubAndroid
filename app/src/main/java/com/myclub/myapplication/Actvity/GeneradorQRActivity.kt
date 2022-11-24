package com.myclub.myapplication.Actvity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.myclub.myapplication.dataDto.request.generadorQRDto
import com.myclub.myapplication.databinding.ActivityGeneradorQractivityBinding
import net.glxn.qrgen.android.QRCode
import kotlin.math.log

class GeneradorQRActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGeneradorQractivityBinding
    private lateinit var QR: generadorQRDto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeneradorQractivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent != null) {

            val IdDes = intent.extras?.getString("IdDes").toString()
            val IdShop = intent.extras?.getString("IdShop").toString()
            val IdCoupon = intent.extras?.getString("IdCoupon").toString()

            val todo = IdDes + IdCoupon + IdCoupon
            println(todo)

            QR = generadorQRDto(IdDes, IdShop, IdCoupon)

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
        if (TextUtils.isDigitsOnly(QR.toString())) {
        } else {
            val bitmap = QRCode.from(QR.toString()).withSize(1000, 1000).bitmap()
            binding.ivQRCode.setImageBitmap(bitmap)
        }
    }


}
