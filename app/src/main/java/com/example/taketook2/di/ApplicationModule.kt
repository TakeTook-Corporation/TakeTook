package com.example.taketook2.di

import android.content.Context
import androidx.annotation.NonNull
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author y.gladkikh
 */
@Module
class ApplicationModule(@param:NonNull private val context: Context) {

    @Singleton
    @Provides
    @NonNull
    fun provideContext(): Context {
        return context
    }
}
