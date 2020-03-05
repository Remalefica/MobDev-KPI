package com.example.lab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setAdapter(true)

        setAdapter(false)

        buttonOkSetup()

        buttonResetSetup()
    }

    fun setAdapter(option: Boolean)
    {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            if (option)
                listOf(getString(R.string.nothing_selected_item)) + resources.getStringArray(R.array.colors).toList()
            else
                listOf(getString(R.string.nothing_selected_item)) + resources.getStringArray(R.array.price).toList()
        )
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        if (option) spinnerColor!!.adapter = adapter
        else spinnerPrice!!.adapter = adapter
    }

    fun buttonOkSetup()
    {
        val nothing_selected_item: String = getString(R.string.nothing_selected_item)
        buttonOk.setOnClickListener{
            if(spinnerColor.selectedItem.toString()== nothing_selected_item
                &&spinnerPrice.selectedItem.toString()== nothing_selected_item)
            {
                textResultColor.text = "You have not chosen any options yet"
                textResultPrice.text = "Sorry!"
            }
            else if (spinnerColor.selectedItem.toString()== nothing_selected_item
                ||spinnerPrice.selectedItem.toString()== nothing_selected_item)
            {
                textResultColor.text = "You have to select both options"
                textResultPrice.text = "Try again!"
            }
            else
            {
                textResultColor.text = "Your color choice for this time: ${spinnerColor?.selectedItem.toString()}"
                textResultPrice.text = "We will find offers in range: ${spinnerPrice?.selectedItem.toString()} UAH"
            }

        }
    }

    fun buttonResetSetup()
    {
        buttonReset.setOnClickListener {
            spinnerColor?.setSelection(0)
            spinnerPrice?.setSelection(0)
            textResultColor.text = getString(R.string.nothing_selected_color)
            textResultPrice.text = getString(R.string.nothing_selected_price)
        }
    }
}
