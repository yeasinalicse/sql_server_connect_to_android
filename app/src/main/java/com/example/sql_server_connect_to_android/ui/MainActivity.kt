package com.example.sql_server_connect_to_android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.sql_server_connect_to_android.App
import com.example.sql_server_connect_to_android.databinding.ActivityMainBinding
import com.example.sql_server_connect_to_android.model.Resource
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MyViewModel
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        binding.btnConnectSql.setOnClickListener {
            App.showProgressDialog(this@MainActivity)

            viewModel.performBackgroundTask().observe(this) { resultData ->
                when (resultData.status) {
                    Resource.Status.SUCCESS -> {
                        App.hideProgressDialog()
                        if (resultData.data.length() > 0) {
                            var dataRs: JSONArray = resultData.data
                            Log.e("sdfsdfsd", "sdfsdf")

                        } else {

                        }
                        Toast.makeText(this, resultData.message, Toast.LENGTH_SHORT).show()
                    }

                    Resource.Status.ERROR -> {
                        App.hideProgressDialog()
                        Toast.makeText(this, resultData.message, Toast.LENGTH_SHORT).show()
                    }
                    // Handle other states if needed
                    else -> {}
                }
            }

        }


    }
}