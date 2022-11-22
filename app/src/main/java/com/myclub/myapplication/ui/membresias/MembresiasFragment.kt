package com.myclub.myapplication.ui.membresias

import android.content.ClipData.Item
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myclub.myapplication.R
import com.myclub.myapplication.adapter.CuponAdapter
import com.myclub.myapplication.dataDto.request.ConsultaCuponRequestDto
import com.myclub.myapplication.dataDto.response.ConsultarCuponResponseDto

import com.myclub.myapplication.databinding.FragmentMembresiasBinding
import com.myclub.myapplication.network.ApiClient.Companion.RetrofitHelper
import com.myclub.myapplication.network.ApiService
import com.myclub.myapplication.utils.Constantes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MembresiasFragment : Fragment(R.layout.fragment_membresias) {

    private var binding: FragmentMembresiasBinding? = null
    private lateinit var listacupones: MutableList<ConsultarCuponResponseDto>
    private lateinit var myAdapter: CuponAdapter
    private lateinit var consultaCuponDto: ConsultaCuponRequestDto



    private lateinit var recyclerView: RecyclerView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMembresiasBinding.bind(view)
        callListService()

    }

    private fun callListService() {

        try {
            consultaCuponDto = ConsultaCuponRequestDto()
            consultaCuponDto.IdQuery = 1.0
            val apiService: ApiService =
                RetrofitHelper(Constantes.BASE_MY_CLUB).create(ApiService::class.java)

            apiService.ConsultarCupon(consultaCuponDto)
                .enqueue(object : Callback<List<ConsultarCuponResponseDto?>?> {
                    override fun onResponse(
                        call: Call<List<ConsultarCuponResponseDto?>?>,
                        response: Response<List<ConsultarCuponResponseDto?>?>
                    ) {
                        if (response.body() != null && response.body()?.size!! > 0) {
                            listacupones =
                                response.body() as MutableList<ConsultarCuponResponseDto>
                            initRecyclerView(listacupones)

                        } else {
                            Toast.makeText(requireContext(), "EmptyList", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(
                        call: Call<List<ConsultarCuponResponseDto?>?>,
                        t: Throwable
                    ) {
                    }


                })
        } catch (e: Exception) {
            //

        }
    }

    private fun initRecyclerView(listFunction: MutableList<ConsultarCuponResponseDto>) {
        try {
            recyclerView = binding!!.idRecyclerViewCupon
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.setHasFixedSize(true)

            myAdapter = CuponAdapter(listFunction, requireContext())
            
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

