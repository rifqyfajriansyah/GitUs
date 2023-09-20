package com.example.submission5setengah.core.di

import android.content.Context
import com.example.submission5setengah.core.domain.repository.IntUserRepo
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RepositoryModule::class]
)
interface CoreComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): CoreComponent
    }

    fun provideRepository() : IntUserRepo
}