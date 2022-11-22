package com.myclub.myapplication.Actvity


import android.R.attr.button
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.myclub.myapplication.MainActivity
import com.myclub.myapplication.adapter.ShopsAdapter
import com.myclub.myapplication.dataDto.request.ConsultarBuyRequestDto
import com.myclub.myapplication.dataDto.request.ConsultarShopsRequestDto
import com.myclub.myapplication.dataDto.response.ConsultarBuyResponseDto
import com.myclub.myapplication.dataDto.response.ConsultarShopsResponseDto
import com.myclub.myapplication.databinding.ActivityListadoShopsBinding
import com.myclub.myapplication.network.ApiClient
import com.myclub.myapplication.network.ApiService
import com.myclub.myapplication.utils.Constantes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListadoShopsActivity : AppCompatActivity() {

    private var binding: ActivityListadoShopsBinding? = null
    private lateinit var listashops: MutableList<ConsultarShopsResponseDto>
    private lateinit var myAdapterShop: ShopsAdapter
    private lateinit var consultaShopDto: ConsultarShopsRequestDto
    private lateinit var recyclerViewShop: RecyclerView
    private lateinit var idCouponRecover: String

    //BUY PLAN
    private var buyRequestDto: ConsultarBuyRequestDto? = null
    private var buynResponseDto: ConsultarBuyResponseDto? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListadoShopsBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        if (intent.extras != null) {
            idCouponRecover = intent.getStringExtra("IdCoupon").toString()
        }
        callService()
        botones()
    }

    private fun botones() {
        binding?.idbtnregresarshop?.setOnClickListener {
            onBackPressed()
        }
        binding?.BtnFloatingActivarPlan?.setOnClickListener {
            callServiceBuy(buyRequestDto?.IdPlan.toString())

        }
    }

    private fun callServiceBuy(toString: String) {
        try {

            buyRequestDto = ConsultarBuyRequestDto()
            buyRequestDto!!.IdPerson = null
            buyRequestDto!!.IdPlan = 1.0
            buyRequestDto!!.IdProject = Constantes.ID_PROYECTO
            buyRequestDto!!.IdStatus = 1.0

            val apiService: ApiService = ApiClient.RetrofitHelper(Constantes.BASE_MY_CLUB)
                .create(ApiService::class.java)
            apiService.BuyPlan(buyRequestDto)
                .enqueue(object : Callback<ConsultarBuyResponseDto?> {
                    override fun onResponse(
                        call: Call<ConsultarBuyResponseDto?>,
                        response: Response<ConsultarBuyResponseDto?>
                    ) {
                        buynResponseDto = response.body()
                        if (response.body() != null ) {
                            if (buynResponseDto?.Codigo == 500) {
                                Toast.makeText(this@ListadoShopsActivity, "usted ya adquirio esta membresia", Toast.LENGTH_SHORT).show()
                                Log.e("holaaaa", buynResponseDto?.Codigo.toString())
                                Log.e("holaaaa", buynResponseDto?.Mensaje.toString())
                                FloatingActionButton.INVISIBLE
                                //AlertErrorResponse.alertDialogErrorResponse.show()
                                //AlertLoading.alertDialogLoading.dismiss()

                            } else {
                                Log.e("holaaaa", buynResponseDto?.Codigo.toString())
                                Log.e("holaaaa", buynResponseDto?.Mensaje.toString())
                                //AlertLoading.alertDialogLoading.dismiss()
                                val i = Intent(this@ListadoShopsActivity, MainActivity::class.java)
                                Toast.makeText(this@ListadoShopsActivity, "Membresia Activada", Toast.LENGTH_SHORT).show()
                                startActivity(i)
                            }
                        }

                    }

                    override fun onFailure(call: Call<ConsultarBuyResponseDto?>, t: Throwable) {

                        //Toast.makeText(this@ListadoShopsActivity,  t.message, Toast.LENGTH_SHORT).show()

                    }

                })

        } catch (e: Exception) {

        }


    }

    private fun callService() {
        try {
            consultaShopDto = ConsultarShopsRequestDto()
            consultaShopDto.IdQuery = idCouponRecover.toDouble()

            val apiService: ApiService = ApiClient.RetrofitHelper(Constantes.BASE_MY_CLUB)
                .create(ApiService::class.java)
            apiService.ConsultarShops(consultaShopDto)
                .enqueue(object : Callback<List<ConsultarShopsResponseDto?>> {
                    override fun onResponse(
                        call: Call<List<ConsultarShopsResponseDto?>>,
                        response: Response<List<ConsultarShopsResponseDto?>>

                    ) {
                        listashops = response.body() as MutableList<ConsultarShopsResponseDto>
                        if (listashops.size > 0) {

                            initRecyclerView(listashops)

                        }
                    }

                    override fun onFailure(
                        call: Call<List<ConsultarShopsResponseDto?>>,
                        t: Throwable
                    ) {
                    }

                })
        } catch (e: Exception) {
            //

        }

    }

    private fun initRecyclerView(listFunction: MutableList<ConsultarShopsResponseDto>) {
        try {
            recyclerViewShop = binding!!.recyclerViewShopsa
            recyclerViewShop.layoutManager = LinearLayoutManager(this)
            recyclerViewShop.setHasFixedSize(true)

            myAdapterShop = ShopsAdapter(listFunction, this)
            recyclerViewShop.adapter = myAdapterShop
        } catch (e: Exception) {
            //
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}


