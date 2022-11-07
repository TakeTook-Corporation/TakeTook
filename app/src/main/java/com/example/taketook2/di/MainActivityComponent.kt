package com.example.taketook2.di

import com.example.taketook2.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
    ]
)
interface MainActivityComponent {

    fun inject(activity: MainActivity)
}