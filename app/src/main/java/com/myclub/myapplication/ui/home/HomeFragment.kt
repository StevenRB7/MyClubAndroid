package com.myclub.myapplication.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View

import androidx.fragment.app.Fragment
import com.myclub.myapplication.Actvity.ListadoComerciosPlanActivity

import com.myclub.myapplication.R
import com.myclub.myapplication.adapter.Carrusel
import com.myclub.myapplication.adapter.CarruselAdapter
import com.myclub.myapplication.dataDto.request.QueryPersonByIdRequestDto
import com.myclub.myapplication.dataDto.response.QueryPersonByIdResponseDto
import com.myclub.myapplication.databinding.FragmentHomeBinding
import com.myclub.myapplication.network.ApiClient
import com.myclub.myapplication.network.ApiService
import com.myclub.myapplication.utils.Constantes
import com.myclub.myapplication.utils.dataStore.MyClub.Companion.sharedPreferences
import com.myclub.myapplication.utils.dataStore.MySharedPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var binding: FragmentHomeBinding? = null
    private var idPersonRecovered = ""
    private lateinit var queryPersonByIdRequestDto: QueryPersonByIdRequestDto
    private lateinit var queryPersonByIdResponseDto: QueryPersonByIdResponseDto

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        idPersonRecovered = recoverIdPersonShared()

        val idCoupon = 1.0
        //binding?.idcardbares?.setOnClickListener {
            ///val i = Intent(requireContext(), ListadoComerciosPlanActivity::class.java)
            //i.putExtra("IdCoupon", idCoupon.toString())
            //startActivity(i)
        //}

        val carrusel = ArrayList<Carrusel>()
        carrusel.add(Carrusel(R.drawable.cardbannercinco))
        carrusel.add(Carrusel(R.drawable.cardbannercuatro))
        carrusel.add(Carrusel(R.drawable.cardbannertres))
        carrusel.add(Carrusel(R.drawable.cardbannerdos))
        carrusel.add(Carrusel(R.drawable.cardbanner))

        val adapter = CarruselAdapter(carrusel)

        binding?.apply {
            carouselRecyclerview.adapter = adapter
            carouselRecyclerview.setAlpha(false)
            carouselRecyclerview.setInfinite(true)
            carouselRecyclerview.setIsScrollingEnabled(true)
            carouselRecyclerview.set3DItem(true)

        }
        callQueryPersonByIdService()

    }


    private fun callQueryPersonByIdService() {
        try {

            queryPersonByIdRequestDto = QueryPersonByIdRequestDto()
            queryPersonByIdRequestDto.idPersona =
                MySharedPreferences(requireContext()).recoverIdPersonPref().toDouble()
            val apiService: ApiService = ApiClient.RetrofitHelper(Constantes.BASE_URL_PERSONAS)
                .create(ApiService::class.java)
            apiService.queryPersonByIdRequestDto(queryPersonByIdRequestDto)
                ?.enqueue(object : Callback<QueryPersonByIdResponseDto?> {
                    override fun onResponse(
                        call: Call<QueryPersonByIdResponseDto?>,
                        response: Response<QueryPersonByIdResponseDto?>
                    ) {
                        if (response.body() != null) {
                            queryPersonByIdResponseDto = response.body()!!
                            binding?.txtnombre?.text = queryPersonByIdResponseDto.PRIMER_NOMBRE

                        }
                    }

                    override fun onFailure(call: Call<QueryPersonByIdResponseDto?>, t: Throwable) {

                    }
                })
        } catch (e: Exception) {
            //
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun recoverIdPersonShared(): String {
        var idPerson = ""
        try {
            sharedPreferences = MySharedPreferences(requireContext())
            idPerson = sharedPreferences.recoverIdPersonPref()
        } catch (e: Exception) {
            //
        }
        return idPerson
    }


}