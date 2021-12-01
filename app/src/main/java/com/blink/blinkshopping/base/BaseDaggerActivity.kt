package com.blink.blinkshopping.base

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.blink.blinkshopping.R
import dagger.android.AndroidInjection

open class BaseDaggerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
    }

    fun showSnackbar(
        view: View,
        message: String,
        type: Int,
        duration: Int = Snackbar.LENGTH_SHORT,
        callback: () -> Unit
    ) {
        val snackbar = Snackbar
            .make(view, message, duration)
        when (type) {
            1 -> snackbar.view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.greencolor))
            2 -> snackbar.view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.errColor))
            else -> snackbar.view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.yellowWarning))
        }
        snackbar.setActionTextColor(Color.WHITE)
        snackbar.addCallback(object : Snackbar.Callback() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                callback()
            }
        })
        snackbar.show()
    }

     fun showAlert(message: String) {
        AlertDialog.Builder(applicationContext).apply {
            setMessage(message)
            setPositiveButton(getText(android.R.string.ok)) { dialog, _ -> dialog.cancel() }
            show()
        }
    }

}