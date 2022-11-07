package com.example.taketook2.di

import com.example.taketook2.App
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: App)
}