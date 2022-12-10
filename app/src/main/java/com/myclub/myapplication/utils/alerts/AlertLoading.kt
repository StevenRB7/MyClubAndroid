package com.myclub.myapplication.utils.alerts

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.myclub.myapplication.R
import com.myclub.myapplication.databinding.AlertLoadingBinding

class AlertLoading {
    companion object {
        lateinit var alertDialogLoading: AlertDialog
    }

    fun alertLoadingDialog(context: Context, message: String) {
        val viewAlert: AlertLoadingBinding =
            AlertLoadingBinding.inflate(LayoutInflater.from(context))
        alertDialogLoading = AlertDialog.Builder(context).apply {
            setView(viewAlert.root)
            setCancelable(false)
        }.create()

        viewAlert.idTxtxMessage.text = message
        alertDialogLoading.window?.setBackgroundDrawableResource(R.color.transparente)
    }
}