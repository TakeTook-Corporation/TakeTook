package com.example.taketook2.di

import com.example.taketook2.MainActivity
import com.example.taketook2.ui.profile.pin.PinActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [
    AndroidSupportInjectionModule::class
])
abstract class AppModule {
    @ActivityScope @ContributesAndroidInjector()
    abstract fun contributesMainActivityInjector(): MainActivity

    @ActivityScope @ContributesAndroidInjector()
    abstract fun contributesModuleActivityInjector(): PinActivity
}