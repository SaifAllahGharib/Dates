package com.example.dates.util

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog

class CustomDialog(
    private val context: Context,
    val view: View,
    private val them: Int?,
    private val cancelable: Boolean = true
) {
    private var alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context, them!!)
    private var alertDialog: AlertDialog

    init {
        alertDialogBuilder.setView(view)
        alertDialogBuilder.setCancelable(cancelable)
        alertDialog = alertDialogBuilder.create()
    }

    fun showAlertDialog() {
        alertDialog.show()
    }

    fun alertDialogDismiss() {
        alertDialog.dismiss()
    }
}