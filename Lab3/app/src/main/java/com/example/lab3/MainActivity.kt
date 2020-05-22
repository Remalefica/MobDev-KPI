package com.example.lab3

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var col : String = ""
    var price : String = ""
    lateinit var fileName: String
    var orders: MutableList<Order> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fileName = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath + "orders.json"

        radio_group1.setOnCheckedChangeListener { group, checkedId ->
            val radio1: RadioButton = findViewById(checkedId)
            col = radio1.text as String
        }

        radio_group2.setOnCheckedChangeListener { group, checkedId ->
            val radio2: RadioButton = findViewById(checkedId)
            price = radio2.text as String
        }

        buttonOkSetup()
        buttonShowAllSetup()
        buttonClearSetup()
    }

    private fun buttonOkSetup(){
        button.setOnClickListener {
            var result = ""

            if (col != "" && price != "") {
                result = "Selected color of flowers: $col \nSelected range of price: $price (added)"
                orders.add(Order(col, price))
            }
            else {
                result = "You have to choose all options\nSorry!"
                Toast.makeText(this, getString(R.string.warning_message), Toast.LENGTH_SHORT).show()
            }

            answer.text = result
        }
    }

    private fun buttonShowAllSetup(){
        button_show.setOnClickListener {
            FileService.saveOrdersToFile(fileName, orders)
            val intent = Intent(this, OutputActivity::class.java)
            startActivity(intent)
        }
    }

    private fun  buttonClearSetup(){
        button_clear.setOnClickListener {
            orders.clear()
            answer.text = getString(R.string.file_empty)
            FileService.saveOrdersToFile(fileName, orders)
        }
    }

    override fun onStart() {
        super.onStart()
        orders = FileService.getOrdersFromFile(fileName)
    }


    override fun onStop() {
        super.onStop()
        FileService.saveOrdersToFile(fileName, orders)
    }
}
