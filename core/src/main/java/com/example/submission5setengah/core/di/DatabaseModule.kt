package com.example.submission5setengah.core.di

import android.content.Context
import androidx.room.Room
import com.example.submission5setengah.core.data.source.local.room.UserDao
import com.example.submission5setengah.core.data.source.local.room.UserDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): UserDatabase = Room.databaseBuilder(
        context,
        UserDatabase::class.java, "Tourism.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideTourismDao(database: UserDatabase): UserDao = database.usersDao()

}