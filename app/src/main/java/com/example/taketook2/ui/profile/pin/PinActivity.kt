package com.example.taketook2.ui.profile.pin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taketook2.R

class PinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin)
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, PinActivity::class.java)
        }
    }
}