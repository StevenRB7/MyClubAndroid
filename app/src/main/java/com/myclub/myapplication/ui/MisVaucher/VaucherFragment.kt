package com.myclub.myapplication.ui.MisVaucher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myclub.myapplication.R
import com.myclub.myapplication.adapter.MisPlanesAdapter
import com.myclub.myapplication.dataDto.request.ConsultaMisPlanesRequestDto
import com.myclub.myapplication.dataDto.response.MisPlanesResponseDto
import com.myclub.myapplication.databinding.FragmentVaucherBinding
import com.myclub.myapplication.network.ApiClient
import com.myclub.myapplication.network.ApiService
import com.myclub.myapplication.utils.Constantes
import com.myclub.myapplication.utils.dataStore.MyClub.Companion.sharedPreferences
import com.myclub.myapplication.utils.dataStore.MySharedPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VaucherFragment : Fragment(R.layout.fragment_vaucher) {

    private var binding: FragmentVaucherBinding? = null
    private lateinit var listMisPlanes: MutableList<MisPlanesResponseDto>
    private lateinit var myAdapter: MisPlanesAdapter
    private lateinit var consultaMisPlanes: ConsultaMisPlanesRequestDto


    private lateinit var recyclerView: RecyclerView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVaucherBinding.bind(view)

        callListService()

    }

    private fun callListService() {

        try {
            consultaMisPlanes = ConsultaMisPlanesRequestDto()
            consultaMisPlanes.IdPerson = recoverIdPersonShared().toString().toDouble()
            consultaMisPlanes.IdProject = Constantes.ID_PROYECTO
            consultaMisPlanes.IdCoupon = 0.0

            val apiService: ApiService =
                ApiClient.RetrofitHelper(Constantes.BASE_MY_CLUB).create(ApiService::class.java)

            apiService.GetMyPlans(consultaMisPlanes)
                .enqueue(object : Callback<List<MisPlanesResponseDto>?> {
                    override fun onResponse(
                        call: Call<List<MisPlanesResponseDto>?>,
                        response: Response<List<MisPlanesResponseDto>?>
                    ) {
                        if (response.body() != null) {
                            listMisPlanes = response.body() as MutableList<MisPlanesResponseDto>
                            initRecyclerView(listMisPlanes)
                        }
                    }

                    override fun onFailure(call: Call<List<MisPlanesResponseDto>?>, t: Throwable) {
                        //
                    }

                })
        } catch (e: Exception) {
            //
        }
    }

    private fun initRecyclerView(lista: MutableList<MisPlanesResponseDto>) {
        try {
            recyclerView = binding!!.idRecyclerViewVaucher
            recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerView.setHasFixedSize(true)
            myAdapter = MisPlanesAdapter(lista, requireContext())
            recyclerView.adapter = myAdapter
        } catch (e: Exception) {
            //
        }
    }

    override fun onDestroy() {
        super.onDestroy()
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