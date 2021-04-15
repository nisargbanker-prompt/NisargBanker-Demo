package com.example.myapplication.data.repositories

import com.example.myapplication.data.local.UserDao
import com.example.myapplication.data.network.MyApi
import com.example.myapplication.data.network.SafeApiRequest
import com.example.myapplication.data.network.remote.UserRemoteDataSource
import com.example.myapplication.data.network.responses.AuthResponse
import com.example.myapplication.ui.dashboard.navigation.home.data.DataItem
import com.example.myapplication.ui.dashboard.navigation.home.data.UserResponse
import com.example.myapplication.ui.dashboard.navigation.home.homedetails.data.UserDetailsResponse
import com.example.myapplication.util.performGetOperation
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: MyApi,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val localDataSource: UserDao
) : SafeApiRequest() {

    suspend fun userLogin(email: String, password: String): AuthResponse {
        return apiRequest { api.userLogin(email, password) }
    }

    suspend fun userSignup(
        email: String,
        password: String
    ): AuthResponse {
        return apiRequest { api.userSignup(email, password) }
    }

    suspend fun users(pageNumber: String): UserResponse {
        return apiRequest { api.users(pageNumber) }
    }

    fun getUsers() = performGetOperation(
        databaseQuery = { localDataSource.getAllUsers() },
        networkCall = { userRemoteDataSource.getUsers("1") },
        saveCallResult = { localDataSource.insertAll(it.data as List<DataItem>) }
    )

    suspend fun userDetails(userId: String): UserDetailsResponse {
        return apiRequest { api.userDetails(userId) }
    }

}