package com.myclub.myapplication.Actvity


import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.myclub.myapplication.databinding.ActivityGeneradorQractivityBinding
import com.myclub.myapplication.utils.dataStore.MySharedPreferences
import net.glxn.qrgen.android.QRCode
import kotlin.math.log

class GeneradorQRActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGeneradorQractivityBinding
    private var holaaaaaa: String = "vacio"
   // private var otro: String? = "15878084"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeneradorQractivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent != null) {

            val idCoupon = intent.extras?.getString("IdCoupon").toString()
            val idTrade = intent.extras?.getString("IdTrade").toString()
            val idProject = intent.extras?.getString("IdProject").toString()
            val idPersonTrade = intent.extras?.getString("IdPersonTrade").toString()

            holaaaaaa = """ 
                
                {
                "IdPersonTrade":"${idPersonTrade}",
                "IdCoupon": "${idCoupon}",
                "IdTrade": "${idTrade}",
                "IdProject": "${idProject}",
                "IdUserAssociated": "${MySharedPreferences(this).recoverIdPersonPref()}"}              
                """

                .trimIndent()



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
        if (TextUtils.isDigitsOnly(holaaaaaa)) {

        } else {
            val bitmap =
                QRCode.from(holaaaaaa).withSize(1000, 1000).bitmap()
            binding.ivQRCode.setImageBitmap(bitmap)
        }
    }


}
