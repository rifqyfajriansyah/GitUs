package com.example.submission5setengah

import android.app.Application
import com.example.submission5setengah.core.di.CoreComponent
import com.example.submission5setengah.core.di.DaggerCoreComponent
import com.example.submission5setengah.di.AppComponent
import com.example.submission5setengah.di.DaggerAppComponent

open class MyApplication: Application() {

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().create(applicationContext)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(coreComponent)
    }
}