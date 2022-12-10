package com.myclub.myapplication.utils.alerts
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.myclub.myapplication.Actvity.IniciarSesion
import com.myclub.myapplication.R
import com.myclub.myapplication.databinding.AlertCheckMailBinding

class AlertCheckEmail {
    companion object {
        lateinit var alertDialogCheckEmail: AlertDialog
    }

    fun alertCheckEmail(context: Context, dataExtra: String) {
        val viewAlert = AlertCheckMailBinding.inflate(LayoutInflater.from(context))
        alertDialogCheckEmail = AlertDialog.Builder(context).apply {
            setView(viewAlert.root)
            setCancelable(false)
        }.create()

        viewAlert.idBtnOkAlertEmail.setOnClickListener {
            alertDialogCheckEmail.dismiss()
            if (dataExtra == "iniciar") {
                val i = Intent(context, IniciarSesion::class.java)
                context.startActivity(i)
            }
        }
        alertDialogCheckEmail.window?.setBackgroundDrawableResource(R.color.transparente)
        alertDialogCheckEmail.show()
    }
}