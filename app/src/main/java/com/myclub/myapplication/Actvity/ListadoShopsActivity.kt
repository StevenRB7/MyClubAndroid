package com.myclub.myapplication.Actvity


import android.R.attr.button
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.myclub.myapplication.MainActivity
import com.myclub.myapplication.R
import com.myclub.myapplication.adapter.ShopsAdapter
import com.myclub.myapplication.dataDto.request.ConsultarBuyRequestDto
import com.myclub.myapplication.dataDto.request.ConsultarShopsRequestDto
import com.myclub.myapplication.dataDto.response.ConsultarBuyResponseDto
import com.myclub.myapplication.dataDto.response.ConsultarShopsResponseDto
import com.myclub.myapplication.databinding.ActivityListadoShopsBinding
import com.myclub.myapplication.databinding.AlertLoadingBinding
import com.myclub.myapplication.network.ApiClient
import com.myclub.myapplication.network.ApiService
import com.myclub.myapplication.utils.Constantes
import com.myclub.myapplication.utils.dataStore.MyClub.Companion.sharedPreferences
import com.myclub.myapplication.utils.dataStore.MySharedPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListadoShopsActivity : AppCompatActivity() {

    private var binding: ActivityListadoShopsBinding? = null
    private lateinit var listashops: MutableList<ConsultarShopsResponseDto>
    private lateinit var myAdapterShop: ShopsAdapter
    private lateinit var consultaShopDto: ConsultarShopsRequestDto
    private lateinit var recyclerViewShop: RecyclerView
    private var idCouponRecover: String = "1.0"
    private lateinit var alertLoadingNew: AlertDialog


    //BUY PLAN
    private var buyRequestDto: ConsultarBuyRequestDto? = null
    private var buynResponseDto: ConsultarBuyResponseDto? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListadoShopsBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        idCouponRecover = "1"
        Toast.makeText(this, "Uno "+idCouponRecover, Toast.LENGTH_SHORT).show()

        if (intent.extras != null) {
            idCouponRecover = intent.getStringExtra("IdCoupon").toString()
        }else{
            idCouponRecover = "1"
        }

        callService()
        botones()
        alertLoadingShow()


        AlertLoading().alertLoadingDialog(this, "Cargando")

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

            alertLoadingNew.show()

            buyRequestDto = ConsultarBuyRequestDto()
            buyRequestDto!!.IdPerson = recoverIdPersonShared().toDouble()
            buyRequestDto!!.IdPlan = intent.extras?.get("IdCoupon").toString().toDouble()
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
                        alertLoadingNew.dismiss()

                        buynResponseDto = response.body()
                        if (response.body() != null) {
                            if (buynResponseDto?.Codigo == 500) {
                                Toast.makeText(
                                    this@ListadoShopsActivity,
                                    "usted ya adquirio esta membresia",
                                    Toast.LENGTH_SHORT
                                ).show()
                                //Log.e("holaaaa", buynResponseDto?.Codigo.toString())
                                FloatingActionButton.INVISIBLE

                            } else {
                                val i = Intent(this@ListadoShopsActivity, MainActivity::class.java)
                                Toast.makeText(
                                    this@ListadoShopsActivity,
                                    "¡Membresia Activada exitosamente!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(i)
                            }
                        }

                    }

                    override fun onFailure(call: Call<ConsultarBuyResponseDto?>, t: Throwable) {
                        alertLoadingNew.dismiss()

                        //Toast.makeText(this@ListadoShopsActivity,  t.message, Toast.LENGTH_SHORT).show()

                    }

                })

        } catch (e: Exception) {
            alertLoadingNew.dismiss()

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

    private fun recoverIdPersonShared(): String {
        var idPerson = ""
        try {
            sharedPreferences = MySharedPreferences(this)
            idPerson = sharedPreferences.recoverIdPersonPref()
        } catch (e: Exception) {
            //
        }
        return idPerson
    }

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


