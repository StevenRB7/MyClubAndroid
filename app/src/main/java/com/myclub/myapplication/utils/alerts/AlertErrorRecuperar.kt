package com.myclub.myapplication.utils.alerts

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.myclub.myapplication.R
import com.myclub.myapplication.databinding.AlertErrorRecuperarBinding
import com.myclub.myapplication.databinding.AlertErrorResponseBinding


class AlertErrorRecuperar {

    companion object {
        lateinit var alertDialogErrorResponse: AlertDialog
    }

    fun alertErrorResponseDialog(context: Context, message: String) {
        val viewAlert = AlertErrorRecuperarBinding.inflate(LayoutInflater.from(context))
        alertDialogErrorResponse = AlertDialog.Builder(context).apply {
            setView(viewAlert.root)
            setCancelable(false)
        }.create()
        viewAlert.idBtnTryAgain.setOnClickListener {
            alertDialogErrorResponse.setCancelable(true)
            alertDialogErrorResponse.dismiss()
        }
        viewAlert.idTxtxMessage.text = message
        alertDialogErrorResponse.window?.setBackgroundDrawableResource(R.color.transparente)
    }
}