package com.example.lab2

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_choice.*

interface OnFragmentInteractionListener {
    fun onFragmentInteraction(result: String)
}

class ChoiceFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_choice, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonOkSetup()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun buttonOkSetup() {
        button.setOnClickListener{
            val id1: Int = radio_group1.checkedRadioButtonId
            val id2: Int = radio_group2.checkedRadioButtonId
            var result = ""

            if(id1!=-1) {
                when {
                    rb_red.isChecked -> result += "Selected color of flowers: red\n"
                    rb_yellow.isChecked -> result += "Selected color of flowers: yellow\n"
                    rb_white.isChecked -> result += "Selected color of flowers: white\n"
                }
            }
            else result += "You have to choose all options\n"

            if(id2!=-1) {
                when {
                    rb_price1.isChecked -> result += "Selected range of price: 100-150\n"
                    rb_price2.isChecked -> result += "Selected range of price: 150-250\n"
                    rb_price3.isChecked -> result += "Selected range of price: 250-350\n"
                }
            }
            else result += "Sorry!"

            listener?.onFragmentInteraction(result)
        }
    }
}