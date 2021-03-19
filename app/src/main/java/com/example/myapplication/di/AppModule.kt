package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.network.MyApi
import com.example.myapplication.data.network.NetworkConnectionInterceptor
import com.example.myapplication.data.network.remote.UserRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
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

    @Singleton
    @Provides
    fun provideUserRemoteDataSource(myApi: MyApi) = UserRemoteDataSource(myApi)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideCharacterDao(db: AppDatabase) = db.characterDao()


}