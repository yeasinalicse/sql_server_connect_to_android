package com.example.sql_server_connect_to_android.db

import android.annotation.SuppressLint
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class ConnectionClass {
    var ip = "192.168.0.115"
    var classs = "net.sourceforge.jtds.jdbc.Driver"
    var db = "Intutive"
    var un = "yeasin"
    var password = "yeasin"
    @SuppressLint("NewApi")
    fun CONN(): Connection? {
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        var conn: Connection? = null
        val ConnURL: String
        try {
            Class.forName(classs)
            ConnURL = "jdbc:jtds:sqlserver://$ip;databaseName=$db;user=$un;password=$password;"
            conn = DriverManager.getConnection(ConnURL)
        } catch (se: SQLException) {
            Log.e("Error 1: ", se.message!!)
        } catch (e: ClassNotFoundException) {
        } catch (e: Exception) {
            Log.e("Error 2:", e.message!!)
        }
        return conn
    }
}