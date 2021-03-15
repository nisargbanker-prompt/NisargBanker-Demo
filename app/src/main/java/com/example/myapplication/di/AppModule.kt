package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.data.network.MyApi
import com.example.myapplication.data.network.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNetworkConnectionInterceptor(@ApplicationContext context: Context): NetworkConnectionInterceptor =
        NetworkConnectionInterceptor(context)

    @Singleton
    @Provides
    fun provideMyApi(networkConnectionInterceptor: NetworkConnectionInterceptor): MyApi =
        Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(networkConnectionInterceptor)
                    .build()
            )
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)

}