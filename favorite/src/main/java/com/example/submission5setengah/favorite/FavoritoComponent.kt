package com.example.submission5setengah.favorite

import com.example.submission5setengah.di.AppComponent
import dagger.Component


@FavScope
@Component(dependencies = [AppComponent::class])
interface FavoritoComponent {

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): FavoritoComponent
    }

    fun inject(activity: FavoritoActivity)
}
