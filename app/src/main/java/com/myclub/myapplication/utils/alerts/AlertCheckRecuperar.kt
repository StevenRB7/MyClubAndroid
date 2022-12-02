package com.myclub.myapplication.Actvity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.myclub.myapplication.R
import com.myclub.myapplication.databinding.AlertCheckRecuperarBinding

class AlertCheckRecuperar {
    companion object {
        lateinit var alertDialogCheckRecuperar: AlertDialog
    }

    fun alertCheckRecuperar(context: Context, dataExtra: String) {
        val viewAlert = AlertCheckRecuperarBinding.inflate(LayoutInflater.from(context))
        alertDialogCheckRecuperar = AlertDialog.Builder(context).apply {
            setView(viewAlert.root)
            setCancelable(false)
        }.create()

        viewAlert.idBtnOkAlertEmail.setOnClickListener {
            alertDialogCheckRecuperar.dismiss()
            if (dataExtra == "enviar") {
                val i = Intent(context, IniciarSesion::class.java)
                context.startActivity(i)
            }
        }
        alertDialogCheckRecuperar.window?.setBackgroundDrawableResource(R.color.transparente)
        alertDialogCheckRecuperar.show()
    }
}