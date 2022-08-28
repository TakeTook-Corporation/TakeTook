package com.example.taketook2.ui.profile.pin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taketook2.R
import com.example.taketook2.databinding.ActivityMainBinding
import com.example.taketook2.databinding.ActivityPinBinding

/**
 * @author y.gladkikh
 */
class PinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPinBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, PinActivity::class.java)
        }
    }
}
