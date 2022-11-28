package com.myclub.myapplication.Actvity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.myclub.myapplication.MainActivity
import com.myclub.myapplication.R
import com.myclub.myapplication.databinding.AlertCheckMailBinding
import com.myclub.myapplication.databinding.AlertConfirmarCompraBinding

class AlertConfirmarCompra {
    companion object {
        lateinit var alertDialogConfirmarCompra: AlertDialog
    }

    fun alertConfirmarCompra(context: Context, dataExtra: String) {
        val viewAlert = AlertConfirmarCompraBinding.inflate(LayoutInflater.from(context))
        alertDialogConfirmarCompra = AlertDialog.Builder(context).apply {
            setView(viewAlert.root)
            setCancelable(false)
        }.create()

        viewAlert.idBtnComfirmarCompra.setOnClickListener {
            alertDialogConfirmarCompra.dismiss()
            if (dataExtra == "comprar") {
                val i = Intent(context, MainActivity::class.java)
                context.startActivity(i)
            }
        }
        alertDialogConfirmarCompra.window?.setBackgroundDrawableResource(R.color.transparente)
        alertDialogConfirmarCompra.show()
    }
}