package com.example.sql_server_connect_to_android.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sql_server_connect_to_android.db.dao.UserDao
import com.example.sql_server_connect_to_android.model.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray


class MyViewModel : ViewModel() {

    private val viewModelScope = CoroutineScope(Dispatchers.Main)

    fun performBackgroundTask(): LiveData<Resource<JSONArray>> {
        val resultLiveData = MutableLiveData<Resource<JSONArray>>()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val users = UserDao().getAllUsers()
                resultLiveData.postValue(users)
            } catch (e: Exception) {
                resultLiveData.postValue(Resource(Resource.Status.ERROR, JSONArray(), "Error retrieving user data"))
            }
        }

        return resultLiveData
    }

}