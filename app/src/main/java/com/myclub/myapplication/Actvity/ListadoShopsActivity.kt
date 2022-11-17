package com.myclub.myapplication.Actvity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myclub.myapplication.MainActivity
import com.myclub.myapplication.adapter.ShopsAdapter
import com.myclub.myapplication.dataDto.request.ConsultarShopsRequestDto
import com.myclub.myapplication.dataDto.response.ConsultarShopsResponseDto
import com.myclub.myapplication.databinding.ActivityListadoShopsBinding
import com.myclub.myapplication.network.ApiClient
import com.myclub.myapplication.network.ApiService
import com.myclub.myapplication.ui.membresias.MembresiasFragment
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListadoShopsBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        callService()
        botones()
    }

    private fun botones() {
        binding?.idbtnregresarshop?.setOnClickListener {
            var i = Intent(this, MembresiasFragment::class.java)
            startActivity(i)
        }
    }

    private fun callService() {
        try {
            consultaShopDto = ConsultarShopsRequestDto()
            consultaShopDto.IdQuery = 1.0

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