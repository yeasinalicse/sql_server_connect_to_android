package com.example.sql_server_connect_to_android

import android.app.Activity
import android.app.Application
import android.app.ProgressDialog

object App : Application() {
    private var instence: App? = null
    private var pgDialog: ProgressDialog? = null

    @get:Synchronized
    val context: App?
        /**
         * Gets the context.
         *
         * @return single instance of this class
         */
        get() {
            if (instence == null) {
                instence = App
            }
            return instence
        }

    fun showProgressDialog(_context: Activity?) {
        hideProgressDialog()
        pgDialog = ProgressDialog(_context)
        pgDialog!!.setMessage("Please, wait...")
        pgDialog!!.setCancelable(false)
        pgDialog!!.show()
    }

    fun hideProgressDialog() {
        if (pgDialog != null && pgDialog!!.isShowing) {
            pgDialog!!.dismiss()
        }
    }
}
