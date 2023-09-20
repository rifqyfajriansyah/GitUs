package com.example.submission5setengah.core.di

import com.example.submission5setengah.core.data.UserRepo
import com.example.submission5setengah.core.domain.repository.IntUserRepo
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class, DatabaseModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(userRepository: UserRepo): IntUserRepo

}