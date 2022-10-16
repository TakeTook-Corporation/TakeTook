package com.example.taketook2.ui.profile.pin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.core.widgets.temp.MainActivityExtension
import com.example.pin_module.PinFragment
import com.example.profile.module.signin.SignInFragment
import com.example.taketook2.IS_SIGNED_IN
import com.example.taketook2.MainActivity
import com.example.taketook2.Navigation
import com.example.taketook2.R
import com.example.taketook2.databinding.ActivityPinBinding

/**
 * @author y.gladkikh
 */
class PinActivity : AppCompatActivity(), MainActivityExtension {

    private lateinit var binding: ActivityPinBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPinBinding.inflate(layoutInflater)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        setContentView(binding.root)
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, PinActivity::class.java)
        }
    }

    override fun mainActivityAction(fragment: Fragment) {
        when(fragment.tag) {
            SignInFragment.TAG->{
                startActivity(createIntent(this))
            }
            PinFragment.TAG-> {
                IS_SIGNED_IN = true
                startActivity(MainActivity.createIntent(this, Navigation.PROFILE))
            }
        }
    }
}
