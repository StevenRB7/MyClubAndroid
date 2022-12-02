package com.myclub.myapplication.Actvity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myclub.myapplication.R
import com.myclub.myapplication.adapter.MisPlanesAdapter
import com.myclub.myapplication.adapter.VaucherAdapter
import com.myclub.myapplication.dataDto.request.ConsultaMisPlanesRequestDto
import com.myclub.myapplication.dataDto.request.ConsultarVaucherRequestDto
import com.myclub.myapplication.dataDto.response.ConsultarVaucherResponseDto
import com.myclub.myapplication.dataDto.response.MisPlanesResponseDto
import com.myclub.myapplication.databinding.ActivityListadoComerciosPlanBinding
import com.myclub.myapplication.network.ApiClient
import com.myclub.myapplication.network.ApiService
import com.myclub.myapplication.utils.Constantes
import com.myclub.myapplication.utils.dataStore.MyClub
import com.myclub.myapplication.utils.dataStore.MySharedPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListadoComerciosPlanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListadoComerciosPlanBinding
    private lateinit var listCoupons: MutableList<ConsultarVaucherResponseDto>
    private lateinit var myAdapter: VaucherAdapter
    private lateinit var consultaMisPlanes: ConsultarVaucherRequestDto
    private lateinit var recyclerView: RecyclerView
    private lateinit var IdPersonRecoverted: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListadoComerciosPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        IdPersonRecoverted = intent.extras?.getString("IdPerson").toString()

        callListService()
        botones()
    }

    private fun botones() {
        binding.btnregresarlistado.setOnClickListener {
            onBackPressed()
        }
    }

    private fun callListService() {
        try {
            consultaMisPlanes = ConsultarVaucherRequestDto()
            consultaMisPlanes.IdPerson = recoverIdPersonShared().toDouble()
            consultaMisPlanes.IdProject = Constantes.ID_PROYECTO
            consultaMisPlanes.IdCoupon =  intent.extras?.getString("IdCoupon").toString().toDouble()

            val apiService: ApiService =
                ApiClient.RetrofitHelper(Constantes.BASE_MY_CLUB).create(ApiService::class.java)

            apiService.ConsultarVaucher(consultaMisPlanes)
                .enqueue(object : Callback<List<ConsultarVaucherResponseDto?>> {
                    override fun onResponse(
                        call: Call<List<ConsultarVaucherResponseDto?>>,
                        response: Response<List<ConsultarVaucherResponseDto?>>
                    ) {
                        if (response.body() != null) {
                            listCoupons =
                                response.body() as MutableList<ConsultarVaucherResponseDto>
                            initRecyclerView(listCoupons)
                        }
                    }

                    override fun onFailure(
                        call: Call<List<ConsultarVaucherResponseDto?>>,
                        t: Throwable
                    ) {
                        //
                    }
                })
        } catch (e: Exception) {
            //
        }
    }

    private fun initRecyclerView(lista: MutableList<ConsultarVaucherResponseDto>) {
        try {
            recyclerView = binding.idRecyclerViewPlanesComercio
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.setHasFixedSize(true)
            myAdapter = VaucherAdapter(lista, this)
            recyclerView.adapter = myAdapter


        } catch (e: Exception) {
            //
        }
    }
    private fun recoverIdPersonShared(): String {
        var idPerson = ""
        try {
            MyClub.sharedPreferences = MySharedPreferences(this)
            idPerson = MyClub.sharedPreferences.recoverIdPersonPref()
        } catch (e: Exception) {
            //
        }
        return idPerson
    }


}