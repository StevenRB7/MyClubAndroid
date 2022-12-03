package com.myclub.myapplication.Actvity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myclub.myapplication.R
import com.myclub.myapplication.adapter.ComerciosAsociadosAdapter
import com.myclub.myapplication.adapter.MisPlanesAdapter
import com.myclub.myapplication.adapter.VaucherAdapter
import com.myclub.myapplication.dataDto.request.ConsultaMisPlanesRequestDto
import com.myclub.myapplication.dataDto.request.ConsultarVaucherRequestDto
import com.myclub.myapplication.dataDto.response.ComercioCategoriasResponseDto
import com.myclub.myapplication.dataDto.response.ConsultarVaucherResponseDto
import com.myclub.myapplication.dataDto.response.CuponComercioResponseDto
import com.myclub.myapplication.dataDto.response.MisPlanesResponseDto
import com.myclub.myapplication.databinding.ActivityListadoComerciosPlanBinding
import com.myclub.myapplication.databinding.AlertLoadingBinding
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
    private lateinit var myAdapterDos: ComerciosAsociadosAdapter
    private lateinit var consultaMisPlanes: ConsultarVaucherRequestDto
    private lateinit var recyclerView: RecyclerView
    private lateinit var IdPersonRecoverted: String
    private lateinit var respuestMisComercioAsociados: CuponComercioResponseDto
    private lateinit var alertLoadingNew: AlertDialog
    private var mainHandler: Handler = Handler()
    private var statusPeticion: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListadoComerciosPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        IdPersonRecoverted = intent.extras?.getString("IdPerson").toString()

        alertLoadingShow()
        //callListService()
        callConsultarMisComerciosAsociadosService()
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
            consultaMisPlanes.IdCoupon = intent.extras?.getString("IdCoupon").toString().toDouble()

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


    private fun callConsultarMisComerciosAsociadosService() {
        try {
            alertLoadingNew.show()

            consultaMisPlanes = ConsultarVaucherRequestDto()
            consultaMisPlanes.IdPerson = intent.extras?.getString("IdPerson")?.toDouble()
            consultaMisPlanes.IdProject = Constantes.ID_PROYECTO
            consultaMisPlanes.IdCoupon = intent.extras?.getString("IdCoupon")?.toDouble()

            val apiService =
                ApiClient.RetrofitHelper(Constantes.BASE_MY_CLUB).create(ApiService::class.java)
            apiService.consultarMisComerciosAsociados(consultaMisPlanes)
                .enqueue(object : Callback<CuponComercioResponseDto?> {
                    override fun onResponse(
                        call: Call<CuponComercioResponseDto?>,
                        response: Response<CuponComercioResponseDto?>
                    ) {
                        alertLoadingNew.dismiss()

                        if (response.body() != null) {
                            respuestMisComercioAsociados = response.body()!!
                            iinitDos(respuestMisComercioAsociados.Categories?.get(0)?.Trade as MutableList<ComercioCategoriasResponseDto>)

                        }
                    }

                    override fun onFailure(call: Call<CuponComercioResponseDto?>, t: Throwable) {
                        alertLoadingNew.dismiss()
                    }

                })
        } catch (e: Exception) {
            alertLoadingNew.dismiss()
        }
    }

    private fun iinitDos(lista: MutableList<ComercioCategoriasResponseDto>) {
        try {
            recyclerView = binding.idRecyclerViewPlanesComercio
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.setHasFixedSize(true)
            myAdapterDos = ComerciosAsociadosAdapter(lista, this)
            recyclerView.adapter = myAdapterDos

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

    /**
     * UTILEDADES
     */

    private fun alertLoadingShow() {
        try {
            val viewAlert = AlertLoadingBinding.inflate(layoutInflater)
            alertLoadingNew = AlertDialog.Builder(this).apply {
                setView(viewAlert.root)
                setCancelable(false)
            }.create()
            viewAlert.idTxtxMessage.text = "Cargando membresias"
            alertLoadingNew.window?.setBackgroundDrawableResource(R.color.transparent)
        } catch (e: Exception) {
            //
        }
    }

}