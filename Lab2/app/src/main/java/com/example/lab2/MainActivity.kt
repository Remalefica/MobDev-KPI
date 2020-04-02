package com.example.lab2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onFragmentInteraction(result: String) {
        var fragment = supportFragmentManager.findFragmentById(R.id.resultFragment) as ResultFragment
        if(fragment.isInLayout){
            fragment.setText(result)
        }
    }
}
