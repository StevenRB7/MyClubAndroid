package com.myclub.myapplication.utils.alerts

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.myclub.myapplication.MainActivityBusiness
import com.myclub.myapplication.databinding.AlertConfirmarCompraBinding

class AlertConfirmarCompra {
    companion object {
        lateinit var alertDialogConfirmarCompra: AlertDialog
        var d: AlertDialog? = null

    }

    fun alertConfirmarCompra(context: Context, dataExtra: String) {
        val viewAlert = AlertConfirmarCompraBinding.inflate(LayoutInflater.from(context))
        alertDialogConfirmarCompra = AlertDialog.Builder(context).apply {
            setView(viewAlert.root)
            setCancelable(true)
        }.create()

    try {

        viewAlert.idBtnComfirmarCompra.setOnClickListener {
            if (dataExtra == "comprar") {
                val i = Intent(context, MainActivityBusiness::class.java)
                context.startActivity(i)
            }
        }
        viewAlert.idBtnCancelarCompra.setOnClickListener {
            if (dataExtra == "cancelar") {
                val i = Intent(context, MainActivityBusiness::class.java)
                context.startActivity(i)
            }

        }

        }catch (_:Exception){

        }

    }
}