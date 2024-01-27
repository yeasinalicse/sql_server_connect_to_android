package com.example.sql_server_connect_to_android.utils

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.sql.ResultSet
import java.sql.Types

object Utils {
    @JvmStatic
    fun resultSetToJsonArray(rs: ResultSet): JSONArray {
        val jSONArray = JSONArray()
        try {
            var rsmd = rs.metaData
            rsmd = rs.metaData
            val numColumns = rs.metaData.columnCount
            while (rs.next()) {
                val obj = JSONObject()
                for (i in 1..numColumns) {
                    val column_name = rsmd.getColumnLabel(i)
                    val type = rsmd.getColumnType(i)
                    when (type) {
                        Types.BIGINT, Types.DECIMAL, Types.INTEGER -> obj.put(
                            column_name,
                            rs.getLong(column_name)
                        )

                        Types.BOOLEAN -> obj.put(column_name, rs.getBoolean(column_name))
                        Types.BLOB -> obj.put(column_name, rs.getBlob(column_name))
                        Types.DOUBLE -> obj.put(column_name, rs.getDouble(column_name))
                        Types.FLOAT -> obj.put(column_name, rs.getFloat(column_name).toDouble())
                        Types.NVARCHAR -> if (rs.getNString(column_name) == null) {
                            obj.put(column_name, " ")
                        } else {
                            obj.put(column_name, rs.getNString(column_name))
                        }

                        Types.VARCHAR -> if (rs.getString(column_name) == null) {
                            obj.put(column_name, " ")
                        } else {
                            obj.put(column_name, rs.getString(column_name))
                        }

                        Types.TINYINT -> obj.put(column_name, rs.getInt(column_name))
                        Types.SMALLINT -> obj.put(column_name, rs.getInt(column_name))
                        Types.DATE -> obj.put(column_name, rs.getDate(column_name))
                        Types.TIMESTAMP -> obj.put(column_name, rs.getTimestamp(column_name))
                        Types.ARRAY -> obj.put(column_name, rs.getArray(column_name))
                        else -> obj.put(column_name, rs.getObject(column_name))
                    }
                }
                jSONArray.put(obj)
            }
        } catch (ex: Exception) {
            Log.e("dsfsdf", ex.message!!)
        }
        return jSONArray
    }
}
