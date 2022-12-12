package com.myclub.myapplication.utils.alerts

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.myclub.myapplication.Actvity.RecuperarContraActivity
import com.myclub.myapplication.Actvity.VerifyCodeRecuperarContraActivity
import com.myclub.myapplication.R
import com.myclub.myapplication.dataDto.utilsData.DataUtils
import com.myclub.myapplication.databinding.AlertErrorResponseBinding


class AlertErrorResponse {

    companion object {
        lateinit var alertDialogErrorResponse: AlertDialog
    }

    fun alertErrorResponseDialog(context: Context, message: String, dataExtra: DataUtils) {
        val viewAlert = AlertErrorResponseBinding.inflate(LayoutInflater.from(context))
        alertDialogErrorResponse = AlertDialog.Builder(context).apply {
            setView(viewAlert.root)
            setCancelable(true)
        }.create()
        viewAlert.idBtnTryAgain.setOnClickListener {
            alertDialogErrorResponse.setCancelable(true)
            alertDialogErrorResponse.dismiss()
        }
        if (dataExtra.isVerify == "veriricar") {
            viewAlert.idBtnTryAgain.text = "Usuario verificado"
            viewAlert.idBtnTryAgain.setOnClickListener {
                val i = Intent(context, VerifyCodeRecuperarContraActivity::class.java)
                i.putExtra("IdPerson", dataExtra.IdPerson.toString())
                i.putExtra("Login", dataExtra.Phone.toString())
                i.putExtra("Email", dataExtra.Phone.toString())
                context.startActivity(i)
            }
        }


        viewAlert.idTxtxMessage.text = message
        alertDialogErrorResponse.window?.setBackgroundDrawableResource(R.color.transparente)
    }

    fun alertErrorResponseDialog(context: RecuperarContraActivity, message: String, dataExtra: String) {

    }


}