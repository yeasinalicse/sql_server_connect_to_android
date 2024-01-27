package com.example.sql_server_connect_to_android.db.dao

import com.example.sql_server_connect_to_android.db.ConnectionClass
import com.example.sql_server_connect_to_android.model.Resource
import com.example.sql_server_connect_to_android.utils.Utils
import org.json.JSONArray
import java.sql.CallableStatement
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.SQLTimeoutException


class UserDao {
    private var connection: Connection? = null
    private var ConnectionResult: String? = ""
    private var isSuccess = false
//    val userLists: ResultData<JSONArray>
//        get() {
//            var userListData = JSONArray()
//            try {
//                val connectionHelper = ConnectionClass()
//                connection = connectionHelper.CONN()
//                if (connection == null) {
//                    ConnectionResult = "Check Your Internet Access!"
//                } else {
//                    val query = "select * from [User] u "
//                    val statement = connection!!.createStatement()
//                    val resultSet = statement.executeQuery(query)
//                    userListData = Utils.resultSetToJsonArray(resultSet)
//                    ConnectionResult = "Successful!"
//                    isSuccess = true
//                    connection!!.close()
//                }
//            } catch (e: Exception) {
//                isSuccess = true
//                ConnectionResult = e.message
//            }
//        }

    fun getAllUsers(): Resource<JSONArray> {
        var connection: Connection? = null
        try {
            connection = ConnectionClass().CONN()
            val query = "select * from [User] u "

            connection.use { conn ->
                val resultSet = conn?.let { executeQuery(it, query) }

                if (resultSet != null) {
                    return handleSuccess(Utils.resultSetToJsonArray(resultSet))
                } else {
                    return handleFailure(SQLException("Result set is null"))
                }
            }
        } catch (e: SQLTimeoutException) {
            return handleFailure(e)
        } catch (e: SQLException) {
            return handleFailure(e)
        }
    }

    fun getAllUsersInsert(): Resource<JSONArray> {
        var connection: Connection? = null
        try {
            connection = ConnectionClass().CONN()
            val query = "{call InsertUser(?, ?, ?, ?)} "

            connection.use { conn ->

                conn?.prepareStatement(query).use { statement ->
                    if (statement != null) {
                        statement.setInt(1, 10)
                        statement.setString(2, "Yewasinss")
                        statement.setString(3, "01888")
                        statement.setString(4, "sdfsd")

                    }

                    // Execute the stored procedure
                   var status:Int = statement?.executeUpdate() ?:0
                    if (status>0){
                        return handleSuccess(JSONArray())
                    }else{
                        return handleFailure(SQLException("Result set is null"))
                    }
                }
//                val resultSet = executeQuery(conn, query)
//
//                if (resultSet != null) {
//                    return handleSuccess(Utils.resultSetToJsonArray(resultSet))
//                } else {
//                    return handleFailure(SQLException("Result set is null"))
//                }
            }
        } catch (e: SQLTimeoutException) {
            return handleFailure(e)
        } catch (e: SQLException) {
            return handleFailure(e)
        }
    }

    fun getUsersProCall(): Resource<JSONArray> {

        var resultSetArray = JSONArray();
        var connection: Connection? = null
        var callableStatement: CallableStatement? = null
        try {
            connection = ConnectionClass().CONN()
            connection.use { conn ->
                val storedProcedureCall = "{call dbo.SPGetUserTwoTimes}"
                if (conn != null) {
                    callableStatement = conn.prepareCall(storedProcedureCall)
                }
                var hasResults = callableStatement!!.execute()
                var resultSetNumber = 1
                do {
                    if (hasResults) {

                        resultSetArray.put(Utils.resultSetToJsonArray(callableStatement!!.resultSet))
                    }
                    resultSetNumber++
                    hasResults = callableStatement!!.moreResults
                } while (hasResults)
            }
        } catch (e: SQLTimeoutException) {
            return handleFailure(e)
        } catch (e: SQLException) {
            return handleFailure(e)
        } finally {
            callableStatement?.close()
        }
        return handleSuccess(resultSetArray)
    }


    private fun executeQuery(connection: Connection, query: String): ResultSet? {
        val statement = connection.createStatement()
        return statement?.executeQuery(query)
    }

    private fun handleSuccess(jsonArray: JSONArray): Resource<JSONArray> {
        return Resource(Resource.Status.SUCCESS, jsonArray, "success")
    }

    private fun handleFailure(e: SQLException): Resource<JSONArray> {
        return Resource(Resource.Status.ERROR, JSONArray(), "Error retrieving user data: ${e.message}")
    }


}
