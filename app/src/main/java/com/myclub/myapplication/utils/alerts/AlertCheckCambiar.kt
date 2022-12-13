package com.myclub.myapplication.utils.alerts

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.myclub.myapplication.Actvity.IniciarSesion
import com.myclub.myapplication.R
import com.myclub.myapplication.databinding.AlertCheckCambiarBinding
import com.myclub.myapplication.databinding.AlertCheckRecuperarBinding

class AlertCheckCambiar {
    companion object {
        lateinit var alertDialogCheckCambiar: AlertDialog
    }

    fun alertCheckCambiar(context: Context, dataExtra: String) {
        val viewAlert = AlertCheckCambiarBinding.inflate(LayoutInflater.from(context))
        alertDialogCheckCambiar = AlertDialog.Builder(context).apply {
            setView(viewAlert.root)
            setCancelable(false)
        }.create()

        viewAlert.idBtncambiarcontra.setOnClickListener {
            alertDialogCheckCambiar.dismiss()
            if (dataExtra == "iniciarSesion") {
                val i = Intent(context, IniciarSesion::class.java)
                context.startActivity(i)
            }
        }
        alertDialogCheckCambiar.window?.setBackgroundDrawableResource(R.color.transparente)
        alertDialogCheckCambiar.show()
    }
}