package com.example.sql_server_connect_to_android.model

class Resource<T>(var status: Status, var data: T, var message: String) {

    enum class Status {
        LOADING,
        SUCCESS,
        ERROR,
        UNAUTHORIZED
    }
}
