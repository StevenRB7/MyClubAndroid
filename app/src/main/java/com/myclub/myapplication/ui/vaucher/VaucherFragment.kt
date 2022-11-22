package com.myclub.myapplication.ui.vaucher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myclub.myapplication.R
import com.myclub.myapplication.adapter.CuponAdapter
import com.myclub.myapplication.adapter.VaucherAdapter
import com.myclub.myapplication.dataDto.request.ConsultaCuponRequestDto
import com.myclub.myapplication.dataDto.request.ConsultarVaucherRequestDto
import com.myclub.myapplication.dataDto.response.ConsultarCuponResponseDto
import com.myclub.myapplication.dataDto.response.ConsultarVaucherResponseDto
import com.myclub.myapplication.databinding.FragmentMembresiasBinding
import com.myclub.myapplication.databinding.FragmentVaucherBinding
import com.myclub.myapplication.network.ApiClient
import com.myclub.myapplication.network.ApiService
import com.myclub.myapplication.utils.Constantes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VaucherFragment : Fragment(R.layout.fragment_vaucher) {

    private var binding: FragmentVaucherBinding? = null
    private lateinit var listavauchers: MutableList<ConsultarVaucherResponseDto>
    private lateinit var myAdapter: VaucherAdapter
    private lateinit var consultaVaucherDto: ConsultarVaucherRequestDto



    private lateinit var recyclerView: RecyclerView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVaucherBinding.bind(view)

        callListService()

    }

    private fun callListService() {

        try {
            consultaVaucherDto = ConsultarVaucherRequestDto()
            consultaVaucherDto.IdPerson = 191.0
            consultaVaucherDto.IdProject = Constantes.ID_PROYECTO


            val apiService: ApiService =
                ApiClient.RetrofitHelper(Constantes.BASE_MY_CLUB).create(ApiService::class.java)

            apiService.ConsultarVaucher(consultaVaucherDto)
                .enqueue(object : Callback<List<ConsultarVaucherResponseDto?>?>{
                    override fun onResponse(
                        call: Call<List<ConsultarVaucherResponseDto?>?>,
                        response: Response<List<ConsultarVaucherResponseDto?>?>
                    ) {

                        listavauchers = response.body() as MutableList<ConsultarVaucherResponseDto>
                        if (listavauchers.size>0){
                            initRecyclerView(listavauchers)

                        }
                    }

                    override fun onFailure(
                        call: Call<List<ConsultarVaucherResponseDto?>?>,
                        t: Throwable
                    ) {
                    }

                })


        } catch (e: Exception){

        }
    }

    private fun initRecyclerView(listFunction: MutableList<ConsultarVaucherResponseDto>) {
        try {
            recyclerView = binding!!.idRecyclerViewVaucher
            recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            recyclerView.setHasFixedSize(true)

            myAdapter = VaucherAdapter(listFunction, requireContext())
            recyclerView.adapter = myAdapter

        } catch (e: Exception) {
            //
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


}