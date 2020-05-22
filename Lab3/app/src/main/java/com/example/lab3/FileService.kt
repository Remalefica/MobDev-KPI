package com.example.lab3

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.File
import java.io.IOException

object FileService {
    fun getOrdersFromFile(fileName: String): MutableList<Order> {
        val gson = Gson()
        val inputString: String

        try {
            val bufferedReader: BufferedReader = File(fileName).bufferedReader()
            inputString = bufferedReader.use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return mutableListOf()
        }

        val listOrderType = object : TypeToken<MutableList<Order>>() {}.type
        return gson.fromJson(inputString, listOrderType)
    }

    fun saveOrdersToFile(fileName: String, orders: MutableList<Order>) {
        val gson = Gson()
        val jsonString: String = gson.toJson(orders)
        val file = File(fileName)

        file.writeText(jsonString)
    }
}
