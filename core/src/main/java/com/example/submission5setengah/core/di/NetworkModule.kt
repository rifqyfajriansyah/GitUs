package com.example.submission5setengah.core.di

import com.example.submission5setengah.core.BuildConfig
import com.example.submission5setengah.core.data.source.remote.network.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {

        val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val requestHeaders = req.newBuilder()
                .addHeader("Authorization", BuildConfig.KEY)
                .build()
            chain.proceed(requestHeaders)
        }

        val loggingInterceptor = if(BuildConfig.DEBUG) { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) }else { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE) }

        val hostname = "api.github.com"
        val certificatePinner = CertificatePinner.Builder()
            //.add(hostname, "sha256/jFaeVpA8UQuidlJkkpIdq3MPwD0m8XbuCRbJlezysBE=")
            //.add(hostname, "sha256/Wec45nQiFwKvHtuHxSAMGkt19k+uPSw9JlEkxhvYPHk=")
            .add(hostname, "sha256/Jg78dOE+fydIGk19swWwiypUSR6HWZybfnJG/8G7pyM=")
            .add(hostname, "sha256/e0IRz5Tio3GA1Xs4fUVWmH1xHDiH2dMbVtCBSkOIdqM=")
            .add(hostname, "sha256/r/mIkG3eEpVdm+u/ko/cwxzOMo1bk4TyHIlByibiA5E=")
            .build()

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }

    @Provides
    fun provideApiService(client: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

}
