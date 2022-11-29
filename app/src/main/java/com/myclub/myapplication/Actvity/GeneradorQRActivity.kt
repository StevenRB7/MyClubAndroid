package com.myclub.myapplication.Actvity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.myclub.myapplication.databinding.ActivityGeneradorQractivityBinding
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

            val IdPersonShop = intent.extras?.getString("IdPersonShop").toString()
            val IdShop = intent.extras?.getString("IdShop").toString()
            val IdCoupon = intent.extras?.getString("IdCoupon").toString()
            val IdProject = intent.extras?.getString("IdProject").toString()
            holaaaaaa = """ 
                
                {"IdPersonShop"
                : "${IdPersonShop}","IdShop"
                : "${IdShop}","IdCoupon"
                : "${IdCoupon}","IdProject"
                : "${IdProject}"}
                
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
