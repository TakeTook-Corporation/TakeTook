package com.example.taketook2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.core.widgets.temp.MainActivityExtension
import com.example.pin_module.PinFragment
import com.example.profile.module.signin.SignInFragment
import com.example.taketook2.databinding.ActivityMainBinding
import com.example.taketook2.ui.profile.pin.PinActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * @author y.gladkikh
 */
class MainActivity : AppCompatActivity(), MainActivityExtension {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)

        when (intent.getStringExtra(NAVIGATION_ARG)) {
            Navigation.PROFILE.name -> {
                binding.navView.selectedItemId = R.id.navigation_profile
            }
            else -> {

            }
        }
    }

    companion object {
        private const val NAVIGATION_ARG = "NAVIGATION"

        fun createIntent(context: Context, navigation: Navigation = Navigation.NONE): Intent {
            return Intent(context, MainActivity::class.java)
                .putExtra(NAVIGATION_ARG, navigation.name)
        }
    }

    override fun mainActivityAction(fragment: Fragment) {
        when(fragment.tag) {
            SignInFragment.TAG ->{
                startActivity(PinActivity.createIntent(this))
            }
            PinFragment.TAG-> {
                IS_SIGNED_IN = true
                startActivity(MainActivity.createIntent(this, Navigation.PROFILE))
            }
        }
    }
}

var IS_SIGNED_IN = false //TODO this property must be got from server/local DB

enum class Navigation {
    NONE, PROFILE
}