package com.myclub.myapplication.utils.alerts

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.myclub.myapplication.Actvity.Registro
import com.myclub.myapplication.MainActivityBusiness
import com.myclub.myapplication.R
import com.myclub.myapplication.databinding.AlertErrorResponseBinding


class AlertErrorResponse {

    companion object {
        lateinit var alertDialogErrorResponse: AlertDialog
    }

    fun alertErrorResponseDialog(context: Context, message: String) {
        val viewAlert = AlertErrorResponseBinding.inflate(LayoutInflater.from(context))
        alertDialogErrorResponse = AlertDialog.Builder(context).apply {
            setView(viewAlert.root)
            setCancelable(true)
        }.create()
        viewAlert.idBtnTryAgain.setOnClickListener {
            alertDialogErrorResponse.setCancelable(true)
            alertDialogErrorResponse.dismiss()
        }

        viewAlert.idTxtxMessage.text = message
        alertDialogErrorResponse.window?.setBackgroundDrawableResource(R.color.transparente)
    }


}