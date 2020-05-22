package com.example.lab3

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_output.*

class OutputActivity : AppCompatActivity() {

    lateinit var fileName: String
    var orders: MutableList<Order> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_output)
    }

    override fun onStart() {
        super.onStart()
        fileName = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath + "orders.json"
        orders = FileService.getOrdersFromFile(fileName)

        if (orders.size == 0) {
            noOrdersLabel.visibility = View.VISIBLE
        } else {
            noOrdersLabel.visibility = View.INVISIBLE
        }

        list_orders.adapter = CustomListAdapter(this, orders)
    }

    private class CustomListAdapter(val context: Context, val orders: MutableList<Order>): BaseAdapter() {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(context)
            val row = layoutInflater.inflate(R.layout.list_item, parent, false)

            val colorTextView = row.findViewById<TextView>(R.id.color)
            colorTextView.text = "${position + 1}. Color: ${orders[position].color}"

            val priceTextView = row.findViewById<TextView>(R.id.price)
            priceTextView.text = "price: ${orders[position].price}"
            return row
        }

        override fun getItem(position: Int): Any {
            return orders[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return orders.count()
        }
    }
}
