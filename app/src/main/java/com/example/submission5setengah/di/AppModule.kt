package com.example.submission5setengah.di

import com.example.submission5setengah.core.domain.usecase.UserInteractor
import com.example.submission5setengah.core.domain.usecase.UserUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun provideUseCase(userInteractor: UserInteractor): UserUseCase

}