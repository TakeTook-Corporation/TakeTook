package com.example.core.widgets.temp

import android.content.Context
import androidx.fragment.app.Fragment

interface MainActivityExtension {

    val context: Context

    fun mainActivityAction(fragment: Fragment)
}