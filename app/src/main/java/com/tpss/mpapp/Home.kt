package com.tpss.mpapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.tpss.mpapp.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {

    private lateinit var homeBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        homeBinding.btnLogout.setOnClickListener {
            finish()
        }
    }
}