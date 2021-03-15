package com.example.myapplication.data.network

import com.example.myapplication.data.network.responses.AuthResponse
import com.example.myapplication.ui.dashboard.navigation.home.data.DataItem
import com.example.myapplication.ui.dashboard.navigation.home.data.UserResponse
import com.example.myapplication.ui.dashboard.navigation.home.homedetails.data.UserDetailsResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import javax.inject.Inject

interface MyApi {

    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Response<AuthResponse>

    @FormUrlEncoded
    @POST("register")
    suspend fun userSignup(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Response<AuthResponse>

    @GET("users")
    suspend fun users(@Query("page") pageNumber: String) : Response<UserResponse>

    @GET("users/{userId}")
    suspend fun userDetails(@Path("userId") userId: String) : Response<UserDetailsResponse>

  /*  companion object{
        @Inject lateinit var networkConnectionInterceptor: NetworkConnectionInterceptor
        operator fun invoke() : MyApi {

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("https://reqres.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }*/

}

