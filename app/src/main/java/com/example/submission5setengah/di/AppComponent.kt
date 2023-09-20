package com.example.submission5setengah.di

import com.example.submission5setengah.core.di.CoreComponent
import com.example.submission5setengah.core.domain.usecase.UserUseCase
import com.example.submission5setengah.ui.detail.DetailActivity
import com.example.submission5setengah.ui.detail.FollowFragment
import com.example.submission5setengah.ui.main.MainActivity
import dagger.Component

@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class, ViewModelModule::class]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(activity: DetailActivity)
    fun inject(fragment: FollowFragment)

    fun usecase():UserUseCase

}